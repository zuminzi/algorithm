package programmers.lv3;

import java.util.*;

/*
 * input : 노드 개수 n, 간선에 대한 정보 vertex
 * output : 1번 노드로부터 가장 멀리 떨어진 노드(최단경로로 이동했을 때 간선의 개수가 가장 많은 노드)
 * 간선은 양방향이며 총 1개 이상 50,000개 이하의 간선이 있습니다.
 * vertex 배열 각 행 [a, b]는 a번 노드와 b번 노드 사이에 간선이 있다는 의미입니다.
 */
public class PG_49189 {
    // ~109.74ms, 104MB
    private Map<Integer, List<Integer>> map; // <정점, 간선 목록>
    private Map<Integer,Integer> distanceMap; // <정점, 거리>
    private boolean[] visited;

    private class Graph {
        int vertex;
        int distance;
        Graph(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public int solution(int n, int[][] edge) {
        map = new HashMap<>();
        distanceMap = new HashMap<>();
        visited = new boolean[n + 1]; // (1~n)

        for(int i=0; i< n; i++){
            map.put(i + 1, new ArrayList<>());
        }

        for(int i = 0; i < edge.length; i++){
            int v1 = edge[i][0];
            int v2 = edge[i][1];

            List<Integer> list = map.get(v1);
            list.add(v2);
            map.put(v1, list);

            list = map.get(v2);
            list.add(v1);
            map.put(v2, list);
        }

        bfs();

        int max = distanceMap.values().stream().mapToInt(d -> d).max().getAsInt();

        return (int) distanceMap.values().stream().filter(d -> d == max).count();
    }

    private void bfs() {
        Queue<Graph> q = new LinkedList<>();
        q.add(new Graph(1,0));
        visited[1] = true;

        while(!q.isEmpty()) {
            Graph graph = q.poll();
            int v = graph.vertex;
            int d = graph.distance;
            List<Integer> list = map.get(v);
            distanceMap.put(v, d);

            for(int el : list) {
                if(!visited[el]){
                    visited[el] = true;
                    q.add(new Graph(el, d + 1));
                }
            }
        }
    }

    // ~47.43ms, 108MB
    private int exam(int n, int[][] edge) {
        int answer = 0;
        boolean[] visited = new boolean[n + 1]; // 방문 체크 (1~n)
        int[] dist = new int[n + 1]; // 거리 체크 (1~n)
        List<Integer>[] nums = new List[n + 1]; // (1~n)

        // 각 정점마다 리스트 미리 생성
        for(int i = 1; i <= n; i++) {
            nums[i] = new ArrayList<>();
        }
        // 간선 추가
        for(int[] line : edge) {
            nums[line[0]].add(line[1]);
            nums[line[1]].add(line[0]);
        }

        calculateDist(visited, nums, dist);

        return countLongestNode(n, answer, dist);
    }

    private void calculateDist(boolean[] visited, List<Integer>[] nums, int[] dist) {
        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        visited[1] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();
            for(int next: nums[cur]) {
                if (visited[next]) {
                    continue;
                }
                q.add(next);
                visited[next] = true;
                // 거리 갱신
                dist[next] = dist[cur] + 1;
            }
        }
    }

    private int countLongestNode(int n, int answer, int[] dist) {
        int max = 0;
        for(int i = 1; i <= n; i++) {
            max = Math.max(max, dist[i]);
        }

        for(int i = 1; i <= n; i++) {
            if (dist[i] == max) answer++;
        }
        return answer;
    }

    public static void main (String[] args){
        PG_49189 pg_49189 = new PG_49189();
        // expected : 3
        System.out.println(pg_49189.solution(
                6,
                new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}})
        );
    }
}
