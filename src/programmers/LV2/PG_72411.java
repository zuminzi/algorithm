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

    public List<String> fail_sol(String[] orders, int[] course) {
        List<String> comb = new ArrayList<>();
        List<String> answer = new ArrayList<>();
        int[] duplicate = new int[11];
        Arrays.sort(orders, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() > o2.length()) return 1;
                else if (o1.length() < o2.length()) return -1;
                else return 0;
            }
        });

        for (int i = 0; i < orders.length - 1; i++) {
            String[] order = orders[i].split("");
            for (int k = i + 1; k < orders.length; k++) {
                Set<String> intersection = Arrays.stream(order).collect(Collectors.toSet());
                String[] nextOrder = orders[k].split("");
                Set<String> nextSet = Arrays.stream(nextOrder).collect(Collectors.toSet());

                // Fail Point
                // 교집합이 아닌 부분집합 구해야 함 -> 조합 백트래킹으로 재시도
                intersection.retainAll(nextSet);
                if (intersection.size() == 1 || intersection.isEmpty()) continue;

                if (Arrays.stream(course).anyMatch(amount -> amount == intersection.size())) {
                    duplicate[intersection.size()] += 1;
                    comb.add(intersection.stream().collect(Collectors.joining()));
                }
            }
        }

        Set<String> completed = new HashSet<>();
        for (int i = 0; i < duplicate.length; i++) {
            if (duplicate[i] > 1) {
                String maxMenu = "";
                long max = 0;
                for (int k = 0; k < comb.size(); k++) {
                    if (comb.get(k).length() < i) continue;
                    else if (comb.get(k).length() > i) break;
                    else if (completed.add(comb.get(k))) {
                        String[] menu = comb.get(k).split("");
                        long sum = 0;
                        for (int j = 0; j < orders.length; j++) {
                            int x = 0;
                            for (; x < menu.length; x++) {
                                if (!orders[j].contains(menu[x]))
                                    break;
                            }
                            if (x == menu.length) sum++;
                        }
                        if (sum > max) {
                            max = sum;
                            maxMenu = comb.get(k);
                        } else if (sum == max) {
                            maxMenu = maxMenu + "," + comb.get(k);
                        }
                    }
                }
                if (maxMenu.contains(",")) {
                    String[] menu = maxMenu.split(",");
                    for (int y = 0; y < menu.length; y++) {
                        answer.add(menu[y]);
                    }
                } else answer.add(maxMenu);
            }
        }
        Collections.sort(answer);
        return answer;
    }

    public static void main(String[] args){
        PG_72411 pg_72411 = new PG_72411();
        System.out.println(pg_72411.solution(new String[] {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"}, new int[]{2,3,4}));
        System.out.println(pg_72411.solution(new String[]{"XYZ", "XWY", "WXA"}, new int[]{2,3,4}));
        System.out.println(pg_72411.solution(new String[]{"ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"}, new int[]{2,3,5}));
        // expected : ['AB', 'ABC', 'ABCDE', 'ABD', 'ABE', 'AC', 'ACD', 'ACE', 'AD', 'ADE', 'AE', 'BC', 'BCD', 'BCE', 'BD', 'BDE', 'BE', 'CD', 'CDE', 'CE', 'DE']
        System.out.println(pg_72411.solution(new String[]{"ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE", "ABCDE"}, new int[]{2,3,5}));
    }
}