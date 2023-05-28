package programmers.lv3;

import java.util.*;

/*
 * input : 항공권 정보가 담긴 2차원 배열 tickets
 * output : 방문하는 공항 경로를 배열에 담아 return
 - 만일 가능한 경로가 2개 이상일 경우 알파벳 순서가 앞서는 경로를 return 합니다.
 - 문제에 항공권 정보가 중복되지 않는다는 언급이 없습니다. 동일한 항공권이 여러 장 주어질 수 있음을 감안하고 풀이를 작성해야 합니다.
 - ~66.89ms, 104MB, BFS로 풀이
 */
public class PG_43164 {
    class Airport {
        int curr;
        int[] order;
        int count;

        Airport(int curr, int[] order, int count){
            this.curr = curr;
            this.order = order;
            this.count = count;
        }
    }
    private static int n;
    private static PriorityQueue<String[]> pq;
    private final String DEPARTURE_AIRPORT= "ICN";
    public String[] solution(String[][] tickets) {
        n = tickets.length;
        pq = new PriorityQueue<>(new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                for(int i=0; i<o1.length; i++){
                        return o1[i].compareTo(o2[i]);
                }
                // Problem Point
                // pq에 중복 배열이 들어가는 경우까지 고려
                // 이 때, 예외 발생시키지 말고 동일한 경우로 처리 (return 0)
                return  0;
            }
        });

        // Problem Point
        // tickets 배열 오름차순 정렬
        Arrays.sort(tickets, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                if(!o1[0].equals(o2[0])) {
                    return o1[0].compareTo(o2[0]);
                } else {
                    return o1[1].compareTo(o2[1]);
                }
            }
        });

        for(int i=0;i <tickets.length; i++){
            if(tickets[i][0].equals(DEPARTURE_AIRPORT)){
                int[] order = new int[n];
                Arrays.fill(order, -1); // 방문 전 배열 모두 -1로 초기화
                exploreTheRoute(tickets, i, order);
            }
        }
        return pq.peek();
    }

    private static void addPQ(String[][] tickets, int[] completedOrder) {
        // Problem Point
        // output으로 return할 배열은 tickets.length + 1 크기에 해당
        String[] nameArr = new String[n + 1];
        for (int k = 0; k < completedOrder.length; k++) {
            nameArr[completedOrder[k]] = tickets[k][0];
            if (completedOrder[k] == completedOrder.length - 1) nameArr[nameArr.length - 1] = tickets[k][1];
        }
        pq.add(nameArr);
    }

    private void exploreTheRoute(String[][] tickets, int start, int[] order) {
        Queue<Airport> q = new LinkedList<>();
        order[start] = 0;
        // Problem Point
        // count 변수는 n과 비교하기 위해 1부터 시작하도록 설정
        q.add(new Airport(start, order, 1));

        while (!q.isEmpty()){
            Airport airport = q.poll();
            String desti = tickets[airport.curr][1];
            int count = airport.count;
            order = airport.order;

            if(count == n){
                addPQ(tickets, order);
                return;
            }

            for(int i=0; i<n; i++){
                if(order[i] == -1){
                    if(tickets[i][0].equals(desti)) {
                        // Problem Point
                        // 배열 깊은 복사 (원본과 주소값 공유 X)
                        int[] visitedOrder = order.clone();
                        visitedOrder[i] = count;
                        q.add(new Airport(i, visitedOrder, count + 1));
                    }
                }
            }
        }
    }

    public static void main (String[] args){
        PG_43164 pg_43164 = new PG_43164();
        // expected : ["ICN", "JFK", "HND", "IAD"]
        System.out.println(pg_43164.solution(new String[][]{{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}}));
        // expected : ["ICN", "ATL", "ICN", "SFO", "ATL", "SFO"]
        System.out.println(pg_43164.solution(new String[][]{{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}}));
        // expected : ["ICN", "BOO", "DOO", "BOO", "ICN", "COO", "DOO", "COO", "BOO"]
        System.out.println(pg_43164.solution(new String[][]{{"ICN", "BOO"}, {"ICN", "COO"}, {"COO", "DOO"}, {"DOO", "COO"}, {"BOO", "DOO"}, {"DOO", "BOO"}, {"BOO", "ICN"}, {"COO", "BOO"}}));
        // 중복티켓을 처리 안한 경우 반례
        // expected : ["ICN", "AAA", "ICN", "AAA", "ICN", "AAA"]
        System.out.println(pg_43164.solution(new String[][]{{"ICN", "AAA"}, {"ICN", "AAA"}, {"ICN", "AAA"}, {"AAA", "ICN"}, {"AAA", "ICN"}}));
    }
}
