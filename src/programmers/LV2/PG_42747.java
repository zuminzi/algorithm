package programmers.LV2;

import java.util.*;

public class PG_42747 {
    // h번 이상 인용된 논문이 h편 이상-> h의 최댓값 = 과학자의 h-index
    // citations = 논문 인용 횟수
    // 규칙 : citations을 내림차순으로 정렬했을 때, 인덱스가 citations[i]-1 이상인 것 // 인덱스는 0부터 시작하므로

    // Accuracy Test : ~3.22ms, ~85.7MB
    public int codeOfMine(int[] citations) {
        int max = 0;
        Integer[] cp = new Integer[citations.length];

        for(int i=0; i<cp.length; i++){
            cp[i] = citations[i];
        }

        // Collections.reverseOrder() 사용 시 int가 아닌 Integer배열이어야 함
        Arrays.sort(cp, Collections.reverseOrder());

        for(int i=0; i<cp.length; i++){
            if(i >= cp[i] - 1){
                max = Math.max(cp[i], max);
            // (i >= cp[i] - 1) == false일 경우에도 최대값 찾는 로직 필요
            } else {
                max = Math.max(i + 1,max);
            }
        }

        return max;
    }

    // ~1.13ms, ~87.3MB
    // 관계연산자에서 Math.max로 변경 후 ~1.02ms, ~78.4MB)
    public int exam1(int[] citations) {
        // 오름차순으로 정렬 후
        Arrays.sort(citations);

        int max = 0;
        // 뒤에서부터 탐색하기
        for(int i = citations.length-1; i > -1; i--){
            int min = (int)Math.min(citations[i], citations.length - i);
            // min 값이 (이전 min값들 중)max를 초과하면 새로운 max로 설정
            max = Math.max(max,min);
            //if(max < min) max = min;
        }

        return max;
    }

    // ~1.02ms, ~70.6MB
    public int exam2(int[] citations) {
        int answer = 0;
        Arrays.sort(citations);
        // 어차피 citations 배열의 모든 요소를 다 탐색해야 하므로 내림차순으로 해도 상관 X
        for(int i=0; i<citations.length; i++){
            int smaller = Math.min(citations[i], citations.length-i);
            // 관계연산자(>)을 통한 비교보다 Math.max()을 통한 비교가 속도면에서 더 효율적
            answer = Math.max(answer, smaller);
        }
        return answer;
    }

    // ~ 9.29ms, ~77MB
    // TreeMap
    public int exam3(int[] citations) {
        // 해시
        // TreeMap 이용시 저장하면서 자동 정렬
        // 정렬 조건은 오름차순으로 넣어줌
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        for(int citation:citations) {
            map.put(citation, map.getOrDefault(citation, 0) + 1);
        }

        int key = 0;
        int sum = 0;
        int max = map.firstKey();

        for (key=max; key>1; key--) {
            int value = map.getOrDefault(key, 0);
            sum += value;
            if(sum >= key)
                break;
        }
        return key;
    }

    public static void main(String[] args){
        PG_42747 pg_42747 = new PG_42747();
        System.out.println(pg_42747.exam3(new int[]{3, 0, 6, 1, 5})); // expected : 3
        System.out.println(pg_42747.exam3(new int[]{7, 7, 7, 7, 3})); // expected : 4
    }
}
