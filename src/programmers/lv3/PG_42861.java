package programmers.lv3;

import java.util.*;

/*
 * MST(최소신장트리) : 간선에 가중치를 고려하여 최소 비용의 스패닝 트리를 선택하는 것
 * Spanning Tree : n개의 정점을 가지는 그래프의 최소 간선의 수는 (n-1)개이고, (n-1)개의 간선으로 연결되어 있는 트리 형태
 * Tree : 사이클이 없는 그래프
  1.Prim (정점 중심의 접근 방식)
   - 정점을 하나씩 추가해가면서 최소 비용의 신장 트리를 만들어가는 방법
   - 각 정점을 방문할 때마다 해당 정점과 연결된 간선 중에서 최소 비용의 간선을 선택
  2.Kruskal (간선 중심의 접근 방식)
   - 간선을 하나씩 추가해가면서 최소 비용의 신장 트리를 만들어가는 방법
   - 간선을 비용 순으로 정렬한 후, 가장 작은 비용의 간선부터 추가
   - 추가하려는 간선의 양 끝 정점이 서로 연결되어 있지 않은 경우에만 간선을 추가하며, 연결 여부를 확인하기 위해 Union-Find 자료구조를 사용
  3. Prim 알고리즘과 Kruskal 알고리즘에서 사이클 확인 여부
   - 프림은 시작점을 정하고, 시작점에서 가까운 정점을 선택하면서 트리를 구성하므로 그 과정에서 사이클을 이루지 않는다.
   - 하지만 크루스칼은 시작점을 따로 정하지 않고 최소비용의 간선을 차례로 대입하면서 트리를 구성하기 때문에 사이클이 이루어지는 항상 확인해야한다.
 */
class PG_42861 {
    // Kruskal Example
    private int[] parent;

    public int exam1(int n, int[][] costs) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        Arrays.sort(costs, Comparator.comparingInt(a -> a[2]));

        int answer = 0;
        for (int[] cost : costs) {
            int islandA = cost[0];
            int islandB = cost[1];
            int bridgeCost = cost[2];

            if (find(islandA) != find(islandB)) {
                union(islandA, islandB);
                answer += bridgeCost;
            }
        }

        return answer;
    }

    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        parent[rootX] = rootY;
    }

    // Prim Example
    public int exam2(int n, int[][] costs) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] cost : costs) {
            int islandA = cost[0];
            int islandB = cost[1];
            int bridgeCost = cost[2];

            graph.get(islandA).add(new Edge(islandB, bridgeCost));
            graph.get(islandB).add(new Edge(islandA, bridgeCost));
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[n];

        visited[0] = true;
        for (Edge edge : graph.get(0)) {
            pq.offer(edge);
        }

        int answer = 0;
        int connectedCount = 1;

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int currentIsland = current.island;
            int currentCost = current.cost;

            if (visited[currentIsland]) {
                continue;
            }

            visited[currentIsland] = true;
            answer += currentCost;
            connectedCount++;

            if (connectedCount == n) {
                break;
            }

            for (Edge next : graph.get(currentIsland)) {
                if (!visited[next.island]) {
                    pq.offer(next);
                }
            }
        }

        return answer;
    }

    private static class Edge implements Comparable<Edge> {
        int island;
        int cost;

        public Edge(int island, int cost) {
            this.island = island;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) {
        PG_42861 pg_42861 = new PG_42861();
        System.out.println(pg_42861.exam1(4, new int[][]{{0, 1, 1}, {0, 2, 2}, {1, 2, 5}, {1, 3, 1}, {2, 3, 8}})); // expected : 4
        System.out.println(pg_42861.exam1(5, new int[][]{{0, 1, 5}, {1, 2, 3}, {2, 3, 3}, {3, 1, 2}, {3, 0, 4}, {2, 4, 6}, {4, 0, 7}})); // expected : 15
    }
}
