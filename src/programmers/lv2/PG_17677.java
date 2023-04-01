package programmers.lv2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PG_17677 {
    // 자카드 유사도
    // 두 집합의 교집합 크기를 두집합의 합집합 크기로 나눈 값
    // 교집합과 합집합이 모두 공집합일 경우 유사도 = 1
    // B가 A의 부분집합이면, 교집합은 B(min), 합집합은 A(max)

    //문자열 사이의 유사도 계산 가능
    // 두 글자씩 끊어서 다중집합
    // 영문자로 된 글자쌍만 유효, 기타공백이나 숫자, 특수문자가 포함되어있는 경우 글자쌍 버린다
    // 대소문자 구분 x
    // 출력형식: 유사도 * 65536 소수점 아래 제거 // 정수부만 출력

    // Accuracy Test ~17.45ms, 85.9MB)
    public int codeOfMine_refactor(String str1, String str2) {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        double similarity = 0.0;
        int answer = 0;

        compareAndAdd(str1, set1);
        compareAndAdd(str2, set2);

        // Collections.retainsAll()과 addAll은 원본을 훼손하므로 복사본 생성
        Set<String> cloneSet1 = new HashSet<>(set1);
        Set<String> cloneSet2 = new HashSet<>(set2);
        cloneSet1.retainAll(set2);
        cloneSet2.addAll(set1);

        if (!cloneSet1.isEmpty() || !cloneSet2.isEmpty()) {
            similarity = cloneSet1.size() / (double) cloneSet2.size();
        } else {
            similarity = 1.0;
        }
        similarity *= 65536;
        answer = (int) similarity;


        return answer;
    }

    private void compareAndAdd(String str, Set<String> set) {
        str = str.toUpperCase();

        for (int i = 0; i < str.length() - 1; i++) {
            String target = str.substring(i, i + 2);
            if (target.matches("[a-zA-Z]{2}")) {
                // Problem Point 1
                // 중복 요소에 인덱스를 부여할 때 aa-1,aa-2로만 부여하는 것이 아니라 aa-1,bb-2,aa-3 모든 중복요소가 인덱스를 공유해서 제대로된 답 도출 X
                // 단순 문자열 붙이기로 수정 후 제대로 된 답 도출 O
                while (set.contains(target)) {
                    target += "-";
                }
                set.add(target);
            }
        }
    }

    // Accuracy Test ~14.68ms, 93.8MB
    public int exam1(String str1, String str2) {
        List<String> list1 = extractValidElements(str1);
        List<String> list2 = extractValidElements(str2);

        if (list1.isEmpty() && list2.isEmpty()) {
            return 65536;
        }

        int intersectionSize = 0;
        int unionSize = 0;

        while (!list1.isEmpty()) {
            String target = list1.get(0);
            int targetSizeOfList1 = 0;
            int targetSizeOfList2 = 0;

            // 중복요소도 고려하여 이미 카운트된 요소는 삭제하면서 교집합 요소 더하기
            while (list1.contains(target)) {
                list1.remove(target);
                targetSizeOfList1++;
            }
            while (list2.contains(target)) {
                list2.remove(target);
                targetSizeOfList2++;
            }
            // 교집합이 공집합이 아니면 교집합 더하기
            // Math.max, Math.min 사용 이유 -> 교집합 중복 요소가 더 많은 경우 더 많이 카운트되므로
            if (targetSizeOfList2 > 0) {
                intersectionSize += Math.min(targetSizeOfList1, targetSizeOfList2);
                unionSize += Math.max(targetSizeOfList1, targetSizeOfList2);
            } else {
                unionSize += targetSizeOfList1;
            }
        }

        // 위 while문 안에서 unionSize에 list1 size만 더해줬으므로
        unionSize += list2.size();

        double j = (double) intersectionSize / unionSize;
        int answer = (int) (j * 65536);
        return answer;
    }

    private static List<String> extractValidElements(String str) {
        List<String> set = new ArrayList<>();
        for (int i = 0; i < str.length() - 1; i++) {
            String temp = str.substring(i, i + 2);
            // !(or) -> ! and !
            if (!((temp.charAt(0) >= 'A' && temp.charAt(0) <= 'Z')
                    || (temp.charAt(0) >= 'a' && temp.charAt(0) <= 'z'))) {
                continue;
            }
            if (!((temp.charAt(1) >= 'A' && temp.charAt(1) <= 'Z')
                    || (temp.charAt(1) >= 'a' && temp.charAt(1) <= 'z'))) {
                continue;
            }
            set.add(temp.toUpperCase());
        }
        return set;
    }

    // Character.isAlphabetic(), Character.isLetter() -> 영문자(한글 자음, 모음 포함) 확인 (boolean 리턴 타입) 메서드
    private static final Integer MULTIPLIER = Character.MAX_VALUE + 1; // Character.MAX_VALUE == 65535

    public int exam2(String str1, String str2) {
        String word1 = str1.toLowerCase();
        String word2 = str2.toLowerCase();

        Map<String, Long> words1 = group(word1);
        Map<String, Long> words2 = group(word2);

        // 같은 key의 value(빈도수) 중 min 값 sum 해서 가져오기
        Integer intersection = getIntersection(words1, words2);
        // word1과 word2 Map 하나로 합친 후 각 key의 max 값 sum 해서 가져오기
        Integer union = getUnion(words1, words2);

        if (intersection.equals(union) && union.equals(0)) {
            return MULTIPLIER;
        }

        return (int) (intersection.doubleValue() / union.doubleValue() * MULTIPLIER);
    }

    private Map<String, Long> group(String word) {
        return IntStream.range(0, word.length() - 1)
                .mapToObj(index -> word.substring(index, index + 2))
                .filter(text -> text.chars().allMatch(character -> Character.isAlphabetic((char) character)))
                // 요소의 발생 횟수 계산하여 그룹화(key, value)한 뒤 Map으로 저장
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
    }

    private Integer getIntersection(Map<String, Long> words1, Map<String, Long> words2) {
        return words1.entrySet().stream()
                .filter(entry -> words2.containsKey(entry.getKey()))
                .map(entry -> Math.min(entry.getValue(), words2.get(entry.getKey())))
                .mapToInt(Long::intValue)
                .sum();
    }

    private Integer getUnion(Map<String, Long> words1, Map<String, Long> words2) {
        Map<String, Long> copiedWords = new HashMap<>(words2);
        words1.forEach((key, value) -> copiedWords.put(key, Math.max(value, words2.getOrDefault(key, 0L))));

        return copiedWords.values().stream()
                .mapToInt(Long::intValue)
                .sum();

    }

    public static void main(String[] args) {
        PG_17677 pg_17677 = new PG_17677();
        System.out.println(pg_17677.exam2("FRANCE", "french")); // expected : 16384
        System.out.println(pg_17677.exam2("handshake", "shake hands")); // expected : 65536
        System.out.println(pg_17677.exam2("aa1+aa2", "AAAA12")); // expected : 43690
        System.out.println(pg_17677.exam2("E=M*C^2", "e=m*c^2")); // expected : 65536
        System.out.println(pg_17677.exam2("abc", "abbb")); // expected : 16384
        System.out.println(pg_17677.exam2("aaa","bbb"));
    }
}
