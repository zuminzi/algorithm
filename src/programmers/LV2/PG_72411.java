package programmers.LV2;

import java.util.*;
import java.util.stream.Collectors;

// 가장 많이 주문한 단품메뉴를 코스요리 메뉴로 구성 // 최소 2번 주문
// input : course 추가하고 싶어하는 단품메뉴들의 갯수
// output : 추가하게 될 코스요리 메뉴 구성
public class PG_72411 {
    private static String[] orders;
    private static List<String> combList;
    private static Set<String> set;

    // ~35.16ms, 112MB
    public List<String> solution(String[] orders, int[] course) {
        this.orders = orders;
        combList = new ArrayList<>();
        set = new HashSet<>();
        List<String> answer = new ArrayList<>();

        for (int i = 0; i < orders.length; i++) {
            String[] orderArr = orders[i].split("");
            Arrays.sort(orderArr);
            for (int k = 0; k < course.length; k++) {
                if (k > orderArr.length) break;
                combination(orderArr, new boolean[orderArr.length], course[k], 0);
            }
        }

        checkMaxFrequency(course, answer);

        Collections.sort(answer);
        return answer;
    }

    private void combination(String[] arr, boolean[] visited, int r, int start) {
        if (r == 0) {
            checkForInclusion(arr, visited);
            return;
        }
        for (int i = start; i < arr.length; i++) {
            visited[i] = true;
            combination(arr, visited, r - 1, i + 1);
            visited[i] = false;
        }
    }

    private void checkForInclusion(String[] arr, boolean[] visited) {
        int cnt = 0;
        String menu = "";
        for (int i = 0; i < arr.length; i++) {
            if (visited[i]) menu += arr[i];
        }
        if (set.add(menu)) {
            String[] menuArr = menu.split("");
            for (String order : orders) {
                int i = 0;
                for (; i < menuArr.length; i++) {
                    if (!order.contains(menuArr[i])) break;
                }
                if (i == menuArr.length) cnt++;
            }
            if (cnt > 1) combList.add(menu);
        }
    }

    private static void checkMaxFrequency(int[] course, List<String> answer) {
        Set<String> check = new HashSet<>();
        for (int i = 0; i < course.length; i++) {
            String maxMenu = "";
            int max = 0;
            for (String comb : combList) {
                if (comb.length() == course[i]) {
                    if (check.add(comb)) {
                        String[] menu = comb.split("");
                        int sum = 0;
                        for (String order : orders) {
                            int j = 0;
                            for (; j < menu.length; j++) {
                                if (!order.contains(menu[j])) break;
                            }
                            if (j == menu.length) sum++;
                        }
                        if (sum > max) {
                            max = sum;
                            maxMenu = comb;
                        } else if (sum == max) {
                            maxMenu = maxMenu + "," + comb;
                        }
                    }
                }
            }
            if (maxMenu.contains(",")) {
                String[] menu = maxMenu.split(",");
                for (int y = 0; y < menu.length; y++) {
                    answer.add(menu[y]);
                }
            } else if (maxMenu != "") answer.add(maxMenu);
        }
    }

    static HashMap<String,Integer> map;
    static int m;
    // 우선순위 큐
    // 기본 오름차순 정렬
    // 우선순위가 높은 순으로 out
    public String[] exam(String[] orders, int[] course) {
        PriorityQueue<String> pq = new PriorityQueue<>();
        // 각 course(메뉴 갯수)에 해당하는 조합이 있는지
        for (int i=0;i<course.length;i++){
            map = new HashMap<>();
            m=0;
            // orders 순회하며 체크
            for (int j=0;j<orders.length;j++) {
                find(0, "", course[i], 0, orders[j]);
            }
            // course 별로 max 체크 후 max 해당 값은 우선순위 큐에 추가
            for (String s : map.keySet()){
                if (map.get(s)==m&&m>1){
                    pq.offer(s);
                }
            }
        }

        String  ans[] = new String[pq.size()];
        int k=0;
        while (!pq.isEmpty()){
            ans[k++] = pq.poll();
        }
        return ans;
    }
    static void find(int cnt,String str,int targetNum,int idx,String word){
        if (cnt==targetNum){
            // str -> temps 변환 이유
            // 알파벳 순 정렬 위해
            char[] c = str.toCharArray();
            Arrays.sort(c);
            String temps="";
            for (int i=0;i<c.length;i++)temps+=c[i];

            // 메뉴 별로 Key 관리
            map.put(temps,map.getOrDefault(temps,0)+1);
            // max 체크
            m = Math.max(m,map.get(temps));
            return;
        }
        for (int i=idx;i<word.length();i++){
            char now =word.charAt(i);
            find(cnt+1,str+now,targetNum,i+1,word);
        }
    }

    public static void main(String[] args){
        PG_72411 pg_72411 = new PG_72411();
        System.out.println(pg_72411.exam(new String[] {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"}, new int[]{2,3,4}));
        System.out.println(pg_72411.exam(new String[]{"XYZ", "XWY", "WXA"}, new int[]{2,3,4}));
        System.out.println(pg_72411.exam(new String[]{"ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"}, new int[]{2,3,5}));
        // expected : ['AB', 'ABC', 'ABCDE', 'ABD', 'ABE', 'AC', 'ACD', 'ACE', 'AD', 'ADE', 'AE', 'BC', 'BCD', 'BCE', 'BD', 'BDE', 'BE', 'CD', 'CDE', 'CE', 'DE']
        System.out.println(pg_72411.exam(new String[]{"ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE"}, new int[]{2,3,5}));
    }
}