package programmers.LV2;

import java.util.*;

//  96.4/100.0, out of memory
public class PG_42578 {
    static boolean[] visited;
    static List<Integer> output;
    public int solution(String[][] clothes) {
        int answer = 0;
        Map<String,Integer> map = new HashMap<>();
        for(int i=0; i< clothes.length; i++) { // containsKey로 검사할 필요 없이 merge로 Key가 없을 시 default value도 두 번째 인수에 넣어주면 ok
            map.merge(clothes[i][1], 1, (oldValue, newValue) -> oldValue + 1);
        }

        visited = new boolean[map.size()];
        List<String> keyList = new ArrayList<>(map.keySet());
        for(int i=1; i<= map.size(); i++){
            if(i==1) {
                for (String key : map.keySet()) {
                    answer += map.get(key);
                }
            } else {
                output = new ArrayList<>();
                combination(0, map.size(), i);
                int sum = 1;
                for (int k = 0; k < output.size(); k++) {
                    sum *= map.get(keyList.get(output.get(k)));
                    if((k+1) % i == 0) {
                        answer += sum;
                        sum = 1;
                    }
                }
            }
        }
    return answer;
    }

    static void combination(int start, int n, int r) {
        if(r == 0) {
            for(int i=0; i< visited.length; i++){
                if(visited[i]){
                    output.add(i);
                }
            }
            return;
        }

        for(int i=start; i<n; i++) {
            visited[i] = true;
            combination(i + 1, n, r - 1);
            visited[i] = false;
        }
    }

    public static void main(String[] args){
        PG_42578 pg_42578 = new PG_42578();
        // expected : 11
        System.out.println(pg_42578.solution(new String[][]{{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"},{"green_turban", "headgear"},{"smoky_makeup", "face"}}));
        // expected : 3
        System.out.println(pg_42578.solution(new String[][]{{"crow_mask", "face"}, {"blue_sunglasses", "face"}, {"smoky_makeup", "face"}}));
    }
}