package programmers.LV2;

import java.util.*;

import static java.util.stream.Collectors.*;

// 선택하지 않는 경우도 포함된 경우의 수
public class PG_42578 {
    // 상의의 수를 A 하의의 수를 B라고 한다면
    // 상의와 하의의 조합하는 경우의 수 = A * B
    // 상의와 하의 각각 선택하지 않는 경우의 수를 포함하면 (A+1)*(B+1)의 경우의 수 도출
    // 의상 카테고리에서 최소 하나는 선택해야 하므로 최종적으로 -1을 해주면 (A+1)*(B+1) - 1
    public int exam1(String[][] clothes) {
        int answer = 1;
        HashMap<String, Integer> hm = new HashMap<>();
        for(int i =0; i<clothes.length; i++){
            hm.put(clothes[i][1], hm.getOrDefault(clothes[i][1],0)+1);
            // hm.merge(clothes[i][1], 1, (oldValue, newValue) -> oldValue + 1); 도 가능
        }
        for(String key : hm.keySet()) {
            answer *=(hm.get(key)+1);
        }
        answer -=1;
        return answer;
    }

    // iterator 이용
    public int exam2(String[][] clothes) {
        int answer = 1;
        HashMap<String, Integer> map = new HashMap<>();
        for(int i=0; i<clothes.length; i++){
            String key = clothes[i][1];
            if(!map.containsKey(key)) {
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }
        Iterator<Integer> it = map.values().iterator();
        while(it.hasNext()) {
            answer *= it.next().intValue()+1;
        }
        return answer-1;
    }

    // stream 이용
    public int solution(String[][] clothes) {
        return Arrays.stream(clothes)
                .collect(groupingBy(p -> p[1], mapping(p -> p[0], counting())))
                .values()
                .stream()
                .collect(reducing(1L, (x, y) -> x * (y + 1)))// 새로운값에 매번 +1
                .intValue() - 1; // 모두 선택하지 않는 경우 -1
    }

    public static void main(String[] args){
        PG_42578 pg_42578 = new PG_42578();
        // expected : 11
        System.out.println(pg_42578.exam1(new String[][]{{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"},{"green_turban", "headgear"},{"smoky_makeup", "face"}}));
        // expected : 3
        System.out.println(pg_42578.exam1(new String[][]{{"crow_mask", "face"}, {"blue_sunglasses", "face"}, {"smoky_makeup", "face"}}));
    }
}