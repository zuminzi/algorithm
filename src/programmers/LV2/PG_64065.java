package programmers.LV2;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PG_64065 {
    // Accuracy Test ~73.33ms, 107MB
    public List<Integer> codeOfMine(String s) {
        Map<Integer,Integer> map = new HashMap<>();
        String[] strings= s.split(",");
        for(int i=0; i<strings.length; i++){
            String target = strings[i];
            while(target.contains("{")||target.contains("}")) {
                if(!target.contains("{") && !target.contains("}")) break;
                // target.replace().replace()보다 수행시간 덜 소요됨
                target = target.replace("{", "");
                target = target.replace("}", "");
            }
            int num = Integer.parseInt(target);
            if(map.size() != 0 && map.containsKey(num)) {
                map.put(num,map.get(num)+1);
            } else{
                map.put(num,1);
            }
        }
        // map의 value 기준으로 내림차순 정렬
        List<Integer> keySet = new ArrayList<>(map.keySet());
        keySet.sort((o1, o2) -> map.get(o2) - (map.get(o1)));

        return keySet;
    }

    // trim() -> 왼쪽, 오른쪽 공백 제거 (가운데에 있는 공백은 제거 불가능)
    // {}로 구분된 부분집합에서 {,}을 공백(" ")으로 replace하면서 각 부분집합을 공백(" ")으로 구분
    // -> 부분집합 내에 있는 ","와 구분하여 " , "로 split
    public int[] exam(String s) {
        Set<String> set = new HashSet<>();
        String[] arr = s.replaceAll("[{]", " ").replaceAll("[}]", " ").trim().split(" , ");

        // 요소의 길이(->요소의 개수)로 내림차순 정렬
        Arrays.sort(arr, (a, b)->{return a.length() - b.length();});

        int[] answer = new int[arr.length];
        int idx = 0;
        for(String s1 : arr) {
            for(String s2 : s1.split(",")) {
                if(set.add(s2)) answer[idx++] = Integer.parseInt(s2);
            }
        }
        return answer;
    }

    // util.regex의 Matcher와 Pattern 라이브러리 이용하여 정수 값만 뽑아내기
    // matcher.find() -> matcher에 정규식 패턴에 해당하는 값 있는지 확인
    // matcher.group() -> find한 값 가져오기
    public int[] solution(String s) {

        Map<String, Integer> map = new HashMap<>();
        // 정규식 패턴 생성
        Pattern pattern = Pattern.compile("[0-9]+");
        // 패턴의 matcher 생성
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            String n = matcher.group();
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        int size = map.size();
        int[] answer = new int[size];
        for (String key: map.keySet()) {
            answer[size - map.get(key)] = Integer.parseInt(key);
        }
        return answer;
    }

    public static void main(String[] args){
        PG_64065 pg_64065 = new PG_64065();
        System.out.println(pg_64065.exam("{{2},{2,1},{2,1,3},{2,1,3,4}}")); // expected : [2,1,3,4]
        System.out.println(pg_64065.exam("{{4,2,3},{3},{2,3,4,1},{2,3}}")); // expected : [3,2,4,1]
    }
}
