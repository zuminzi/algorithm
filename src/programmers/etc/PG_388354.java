package programmers.etc;

import java.util.*;

class PG_388354 {
    /* 시간복잡도:
       * 최악: O(정점*(정점+간선)) - 모든 루트가 동일 컴포넌트 탐색 시 (visited 없음)
       * 평균/최선: O(정점+간선) - 루트 분리되거나 DFS 조기 종료 시
    */
    static int[] answer;
    private static final int NO_PARENT = -1;

    public int[] solution(int[] nodes, int[][] edges) {
        answer = new int[2];
        Map<Integer, Set<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            // check-1: 양방향 간선 처리
            adjList.computeIfAbsent(from, k -> new HashSet<>()).add(to);
            adjList.computeIfAbsent(to, k -> new HashSet<>()).add(from);
        }

        for (int root : nodes) {
            // check-2: 간선이 없는 노드 처리(→ Collections.emptyList())
            int rootChildCount = adjList.getOrDefault(root, Collections.emptySet()).size();
            boolean isNodeCountMatchRootNum = (root % 2 == 0) == (rootChildCount % 2 == 0);
            // check-3: 루트이므로 부모 노드는 NO_PARENT로 인자 전달
            dfsCheckParity(adjList, root, NO_PARENT, isNodeCountMatchRootNum);
        }
        return answer;
    }

    private static boolean dfsCheckParity(Map<Integer, Set<Integer>> adjList, int current, Integer parent, boolean parentMatch) {
        // check-2 동일
        Set<Integer> neighbors = adjList.getOrDefault(current, Collections.emptySet());

        // check-4: current 노드 자식 노드 계산 (부모 제외. 무조건 -1을 하면 잘못된 계산 값이 나온다.)
        int currentChildCount = neighbors.contains(parent) ? neighbors.size() - 1 : neighbors.size();
        boolean currentMatch = (current % 2 == 0) == (currentChildCount % 2 == 0);

        for (int neighbor : neighbors) {
            if (parentMatch != currentMatch) return false;
            if (neighbor != parent) {
                // check-5: 자식에서 false를 받으면 즉시 상위도 중단
                if (!dfsCheckParity(adjList, neighbor, current, currentMatch)) return false;
            }
        }
        // check-6: 종료 조건 (단순히 leaf 노드일 때가 아닌, 최상위 루트까지 예외없이 for 루프 탐색 완료된 경우)
        if (parent == NO_PARENT) {
            if (currentMatch) answer[0]++;
            else answer[1]++;
        }
        return true;
    }
    public static void main(String[] args) {
        PG_388354 pg_388354 = new PG_388354();
        // expected: [1,0]
        System.out.println(Arrays.toString(pg_388354.solution(
                new int[]{11, 9, 3, 2, 4, 6},
                new int[][]{{9, 11}, {2, 3}, {6, 3}, {3, 4}}
        )));
        // expected: [2,1]
        System.out.println(Arrays.toString(pg_388354.solution(
                new int[]{9, 15, 14, 7, 6, 1, 2, 4, 5, 11, 8, 10},
                new int[][]{{5, 14}, {1, 4}, {9, 11}, {2, 15}, {2, 5}, {9, 7}, {8, 1}, {6, 4}}
        )));
        // expected: [0,0]
        System.out.println(Arrays.toString(pg_388354.solution(
                new int[]{1, 2, 3, 4},
                new int[][]{{1, 3}, {1, 2}, {2, 4}})));
    }
}