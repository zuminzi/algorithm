package programmers.lv3;

import java.util.*;

public class PG_72413 {
    private class Node implements Comparable<Node>{
        int index;
        int cost;
        Node (int index, int cost) {
            this.index = index;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node n) {
            return this.cost - n.cost;
        }
    }
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int maxCostSum = n * 100_000;
        int answer = maxCostSum;

        int[] costA = new int[n + 1];
        int[] costB = new int[n + 1];
        int[] costS = new int[n + 1];

        Arrays.fill(costA, maxCostSum);
        Arrays.fill(costB, maxCostSum);
        Arrays.fill(costS, maxCostSum);

        List<Node>[] edge = new ArrayList[n + 1];
        for(int i=0; i<fares.length; i++) {
            int node1 = fares[i][0];
            int node2 = fares[i][1];
            int cost = fares[i][2];

            if(edge[node1] == null) edge[node1] = new ArrayList<>();
            edge[node1].add(new Node(node2, cost));

            if(edge[node2] == null) edge[node2] = new ArrayList<>();
            edge[node2].add(new Node(node1, cost));
        }
        getMinCost(costA, a, edge);
        getMinCost(costB, b, edge);
        getMinCost(costS, s, edge);

        for (int i = 0; i < n + 1; i++) {
            answer = Math.min(answer, costA[i] + costB[i] + costS[i]);
        }
        return answer;
    }

    /**
     * 다익스트라 (양수 가중치 최단 거리 탐색)
     * - 현재까지의 최단 거리 정보를 이용하여 인접 노드 최소 비용 갱신(relaxation)
     * - PriorityQueue를 이용해 가장 가까운 노드부터 탐색 (탐색 순서 보장)
     */
    private void getMinCost(int[] graph, int start, List<Node>[] edge) {
        graph[start] = 0; // 자기 자신까지의 거리 초기화 필요 **
        // 기본 오름차순 [최소 힙]
        // 내림차순 희망 시 PriorityQueue<Node> pq = new PriorityQueue<>(Collections.reverseOrder()); [최대 힙]
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            for(Node next: edge[curr.index]) {
                if(graph[next.index] > curr.cost + next.cost) {
                    graph[next.index] = curr.cost + next.cost;
                    queue.add(new Node(next.index, graph[next.index]));
                }
            }
        }
    }

    public static void main(String[] args) {
        PG_72413 pg72413 = new PG_72413();
        // expected: 14
        System.out.println(pg72413.solution(
                7,
                3,
                4,
                1,
                new int[][]{{5, 7, 9}, {4, 6, 4}, {3, 6, 1}, {3, 2, 3}, {2, 1, 6}}
        ));
    }
}
