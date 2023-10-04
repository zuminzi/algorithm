package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1916 {
    private static int ans, N, M;
    private static List<Edge>[] graph;

    /**
     * 출발 도시에서 도착 도시까지 최소 비용으로 움직이는 다익스트라 알고리즘(각 단계에서 탐욕적인 선택을 한다.)
     * Key Point 1 : 우선순위 큐는 미리 정렬 후 삽입 삭제 시 O(log N)이 걸리기 때문에, 반복문으로 O(N) 걸려서 최단거리 찾는 방법보다 빠르다.
     * Key Point 2 : 최단 경로를 저장하는 테이블을 사용하여 출발 노드로부터 각 노드까지의 현재까지의 최단 거리를 유지한다.
     */
    public static void main(String[] args) throws IOException {
        ans = Integer.MAX_VALUE;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        graph = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph[from].add(new Edge(to, cost));
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        getShortestRoute(start, end);

        System.out.println(ans);
        br.close();
    }

    private static void getShortestRoute(int start, int end) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int curr = edge.to;
            int cost = edge.cost;

            if (cost > dist[curr]) {
                continue;
            }

            for (Edge nextEdge : graph[curr]) {
                int next = nextEdge.to;
                int newCost = cost + nextEdge.cost;

                if (newCost < dist[next]) {
                    dist[next] = newCost;
                    pq.add(new Edge(next, newCost));
                }
            }
        }

        ans = dist[end];
    }

    static class Edge {
        int to;
        int cost;

        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }


    // 히든 TC 메모리 초과
    // 가중치가 있는 최단 거리에서 BFS 사용 시 최악의 경우 모든 노드를 다 탐색하게 된다.
    // 따라서, 각 단계에서 방문한적 없는 노드들 중에서 거리(비용)가 가장 짧은 노드를 선택하는 다익스트라를 사용해야 한다.
    private static int min, n, m;
    private static List<Integer>[] departingBuses;
    private static int[][] route;
    public static void BFS() throws IOException {
        min = Integer.MAX_VALUE;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        route = new int[m + 1][3]; // 도시는 1번부터 시작
        departingBuses = new ArrayList[n + 1]; // 각 도시에서 출발하는 버스 노선 // 버스는 1번부터 시작

        for(int i=1; i<departingBuses.length; i++){
            departingBuses[i] = new ArrayList<>();
        }

        for(int i=1; i<route.length; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            route[i][0] = Integer.parseInt(st.nextToken());
            route[i][1] = Integer.parseInt(st.nextToken());
            route[i][2] = Integer.parseInt(st.nextToken());

            departingBuses[route[i][0]].add(i);
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        if(start == end) {
            min = 0;
        } else {
            getShortestRoute_BFS(start, end);
        }

        System.out.println(min);
        br.close();
    }

    private static void getShortestRoute_BFS(int start, int end) {
        Queue<Node> q = new LinkedList<>();
        for(int busNo : departingBuses[start]){
            boolean[] visited = new boolean[m + 1];
            visited[start] = true;
            q.add(new Node(route[busNo][1], route[busNo][2], visited));
        }

        while (!q.isEmpty()){
            Node node = q.poll();
            int curr = node.curr;
            int cost = node.cost;
            boolean[] visited = node.visited;

            if(curr == end){
                min = Math.min(min, cost); // 여기서 return하면 기존 q 초기화됨 // return 안함 주의
            }

            for(int busNo : departingBuses[curr]){
                int destination = route[busNo][1];
                if(!visited[destination]){
                    visited[destination] = true;
                    cost += route[busNo][2];
                    q.add(new Node(destination, cost, visited.clone()));
                    visited[destination] = false;
                    cost -= route[busNo][2];
                }
            }
        }
    }

    static class Node{
        int curr;
        int cost;
        boolean[] visited;
        Node(int curr, int cost, boolean[] visited){
            this.curr = curr;
            this.cost = cost;
            this.visited = visited;
        }
    }
}
