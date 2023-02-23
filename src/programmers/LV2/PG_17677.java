package programmers.LV2;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

    // Accuracy Test ~38.58ms, 80.7MB
    public int codeOfMine(String str1, String str2) {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        double similarity = 0.0;
        int answer = 0;

        str1 = str1.toUpperCase();//.replaceAll("[^a-zA-Z]*","");
        str2 = str2.toUpperCase();//.replaceAll("[^a-zA-Z]*","");

        int cnt = 0;
        for(int i=0; i<str1.length()-1; i++){
            String target = str1.substring(i,i+2);
            if(target.matches("[a-zA-Z]{2}")){
                while (set1.contains(target)){
                    target += "-";
                }
                set1.add(target);
            }
        }
        cnt = 0;
        for(int i=0; i<str2.length()-1; i++){
            String target = str2.substring(i,i+2);
            if(target.matches("[a-zA-Z]{2}")){
                while (set2.contains(target)){
                    target += "-";
                }
                set2.add(target);
            }
        }

        // Collections.retainsAll()과 addAll은 원본을 훼손하므로 복사본 생성
            Set<String> cloneSet1 = new TreeSet<>(set1);
            Set<String> cloneSet2 = new TreeSet<>(set2);
            cloneSet1.retainAll(set2);
            cloneSet2.addAll(set1);

            if(!cloneSet1.isEmpty() || !cloneSet2.isEmpty()) {
                similarity = cloneSet1.size() / (double) cloneSet2.size();
            } else {
                similarity = 1.0;
            }
            similarity *= 65536;
            answer = (int) similarity;


        return answer;
    }
    public static void main(String[] args){
        PG_17677 pg_17677 = new PG_17677();
        System.out.println(pg_17677.codeOfMine("FRANCE","french")); // expected : 16384
        System.out.println(pg_17677.codeOfMine("handshake","shake hands")); // expected : 65536
        System.out.println(pg_17677.codeOfMine("aa1+aa2","AAAA12")); // expected : 43690
        System.out.println(pg_17677.codeOfMine("E=M*C^2","e=m*c^2")); // expected : 65536
        System.out.println(pg_17677.codeOfMine("abc","abbb")); // expected : 16384
    }
}
