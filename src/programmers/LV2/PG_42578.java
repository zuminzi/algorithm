package programmers.LV2;

import java.util.*;

//  96.4/100.0, time out
public class PG_42578 {
    static int answer;
    public int solution(String[][] clothes) {
        answer = 0;
        HashMap<String, List<String>> map = new HashMap<>();
        for (int k = 0; k < clothes.length; k++) {
            if (!map.containsKey(clothes[k][1])) {
                List<String> items = new ArrayList<>();
                items.add(clothes[k][0]);
                map.put(clothes[k][1], items);
            } else {
                List<String> oldList = map.get(clothes[k][1]);
                oldList.add(clothes[k][0]);
                map.put(clothes[k][1], oldList);
            }
        }

        List <String> keyList = new ArrayList<>(map.keySet());


        for (int i = 1; i <= map.size(); i++) {
            if (i == 1) { // nCr에서 r이 1인 경우
                for (String key : map.keySet()) {
                    answer += map.get(key).size();
                }
            } else if (i == map.size()){ // nCr에서 r이 n인 경우
                int chooseAll = 1;
                for (String key : map.keySet()) {
                    chooseAll *= map.get(key).size();
                }
                if (chooseAll != 1) {
                    answer += chooseAll;
                }
            } else {
                boolean[] check = new boolean[map.size()];
                combination_DFS(map, keyList, check, 0, 0, i);
            }
        }
        return answer;
    }

    private void combination_DFS(HashMap<String, List<String>> map, List<String> keyList, boolean[] check, int idx, int cnt, int r) {
        if (cnt == r) {
            int sum = 1;
            for(int i=0; i<check.length; i++) {
                if (check[i]) {
                        if(r == 1) {
                        answer += map.get(keyList.get(i)).size();
                        } else {
                            sum *= map.get(keyList.get(i)).size();
                        }
                }
            }
            if(r != 1) {
                answer += sum;
            }
        }

        for(int i=idx; i< check.length;i++) {
            if(check[i]) {continue;}

            check[i] = true;
            combination_DFS(map, keyList, check, i, cnt+1, r);
            check[i] = false;
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