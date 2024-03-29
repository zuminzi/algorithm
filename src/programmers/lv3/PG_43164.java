package programmers.lv3;

import java.util.*;

/*
 * input : 항공권 정보가 담긴 2차원 배열 tickets
 * output : 방문하는 공항 경로를 배열에 담아 return
 - 만일 가능한 경로가 2개 이상일 경우 알파벳 순서가 앞서는 경로를 return 합니다.
 - 문제에 항공권 정보가 중복되지 않는다는 언급이 없습니다. 동일한 항공권이 여러 장 주어질 수 있음을 감안하고 풀이를 작성해야 합니다.
 - (63.99ms, 118MB), (1.18ms, 78.1MB), (1.32ms, 69.9MB), (1.59ms, 76MB)
   - [방법1] tickets 배열을 알파벳 순서가 앞서는 경로로 정렬한 후, 가장 처음 조건을 만족시키는 요소 리턴하기
   - 우선순위 큐에는 ICN으로 시작하는 요소 개수만큼만 저장됨
 - (85.99ms, 97.8MB), (1.18ms, 75.4MB), (0.82ms, 76.5MB), (1.08ms, 78.4MB)
   - [방법 2] tickets 배열을 정렬하지 않고, 가능한 모든 경로를 탐색한 후에 우선순위 큐안에서 알파벳 순으로 정렬하여 반환
   - 우선순위 큐에는 ICN으로 시작하는 요소 개수 이상으로, 가능한 모든 경로가 저장됨
 */
public class PG_43164 {
    // BFS
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
                    if(!o1[i].equals(o2[i]))
                        return o1[i].compareTo(o2[i]);
                }
                // Problem Point
                // pq에 중복 배열이 들어가는 경우까지 고려
                // 이 때, 예외 발생시키지 말고 동일한 경우로 처리 (return 0)
                return  0;
            }
        });

        // [방법 1]
//        Arrays.sort(tickets, new Comparator<String[]>() {
//            @Override
//            public int compare(String[] o1, String[] o2) {
//                if(!o1[0].equals(o2[0])) {
//                    return o1[0].compareTo(o2[0]);
//                } else {
//                    return o1[1].compareTo(o2[1]);
//                }
//            }
//        });

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
        String[] nameArr = new String[n + 1]; // output으로 return할 배열은 tickets.length + 1 크기에 해당
        for (int k = 0; k < completedOrder.length; k++) {
            nameArr[completedOrder[k]] = tickets[k][0];
            if (completedOrder[k] == completedOrder.length - 1) nameArr[nameArr.length - 1] = tickets[k][1];
        }
        pq.add(nameArr);
    }

    private void exploreTheRoute(String[][] tickets, int start, int[] order) {
        Queue<Airport> q = new LinkedList<>();
        order[start] = 0;
        q.add(new Airport(start, order, 1)); // count 변수는 n과 비교하기 위해 1부터 시작하도록 설정

        while (!q.isEmpty()){
            Airport airport = q.poll();
            String desti = tickets[airport.curr][1];
            int count = airport.count;
            order = airport.order;

            if(count == n){
                addPQ(tickets, order);
                // [방법 1]
//                return;
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


    // ~93.28ms, 107MB
    // DFS
    public PriorityQueue <String> queue;
    public boolean[] visited;
    public String[] exam(String[][] tickets) {
        queue = new PriorityQueue<>();
        visited = new boolean[tickets.length];
        DFS(0, "ICN", "ICN", tickets);
        // 우선순위 큐 대신 리스트 사용하여 정렬 후 첫번째 요소 반환하는 방법도 가능
        // Collections.sort(list);
        // return list.get(0).split(" ");
        String[] answer = queue.poll().split(" "); // 배열이 아닌 문자열로 관리했기 때문에 우선순위 큐의 기본 정렬 기준(오름차순 정렬) 사용 가능
        return answer;
    }

    // 지금 있는 위치, 현재 몇개의 티켓을 사용했는지, 현재까지의 경로(문자열로 표기 가능)
    public void DFS(int used, String now, String path, String[][] tickets) {
        if (used == tickets.length) {
            queue.add(path);
            return;
        }
        for (int i = 0; i < tickets.length; i++) {
            if (!visited[i] && tickets[i][0].equals(now)) {
                visited[i] = true;
                DFS(used + 1, tickets[i][1], path + " " + tickets[i][1], tickets);
                // 일반적으로 그래프에서 재귀 호출로 정점들을 방문한다면
                // 함수가 반환될 때에는 함수를 호출하면서 방문 표시한 곳을 초기 상태로 돌려놓아야만 모든 경로를 탐색할 수 있습니다.
                visited[i] = false;
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
