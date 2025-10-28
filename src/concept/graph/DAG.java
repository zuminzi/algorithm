package concept.graph;

import java.util.*;

// 리프 노드 중복 없는 최대 경로 탐색.(조건: 루트 1개, 사이클 없음 = DAG)

// directed 그래프가 DAG(Directed Acyclic Graph)가 되기 위해서는 이 그래프가 topological ordering(위상 정렬)을 가져야하며, 반대로 topological ordering을 가지는 그래프는 DAG가 된다.
//
// DFS + backtracking으로도 n이 500일 때는 괜찮지만, 그 이상일 때는 위상정렬 + dp로 푸는게 좋음.
// 포인트 1. 루트 노드 찾기 (간선 간의 관계로 부모가 없는 노드 탐색.
// 포인트 2. 리프 노드 도달 시 방문 체크 (중복 경로 방지)).
// 포인트 3. 간선 방문 체크 (백트래킹을 위해 방문 해제).
// 포인트 4. 노드가 1부터 시작한다면 배열 크기 N+1로 설정.
public class DAG {
    // [방법 1] DFS + 백트래킹
    static boolean[] visitedLeaf;
    static boolean[][] visitedEdge;
    static int maxPaths;
    private static int sol1(int N, int M, int[] U, int[] V) {
        maxPaths = 0;
        visitedLeaf = new boolean[N+1];
        visitedEdge = new boolean[N+1][N+1];
        Set<Integer> hasParents = new HashSet<>();
        Set<Integer> hasChildren = new HashSet<>();
        ArrayList<Integer>[] children = new ArrayList[N+1];

        for (int i = 0; i < M; i++) {
            if(children[U[i]] == null) children[U[i]] = new ArrayList<>();
            children[U[i]].add(V[i]);
            hasChildren.add(U[i]);
            hasParents.add(V[i]);
        }

        hasChildren.removeAll(hasParents);
        int root = hasChildren.iterator().next();

        dfs(root, children);

        return maxPaths;
    }

    private static void dfs(int node, ArrayList<Integer>[] children) {
        if (children[node] == null) { // isEmpty() 체크 대신에 null 체크 필요 -> 리프 노드는 List 생성 안했기 때문.
            if (!visitedLeaf[node]) {
                visitedLeaf[node] = true;
                maxPaths++;
            }
            return;
        }

        for (int child : children[node]) {
            if (!visitedEdge[node][child]) {
                visitedEdge[node][child] = true;
                dfs(child, children);
                visitedEdge[node][child] = false;
            }
        }
    }

    // [방법 2] 위상정렬 + DP
    public static int sol2(int N, int M, int[] U, int[] V) {
        // 1. 초기화
        ArrayList<Integer>[] children = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) children[i] = new ArrayList<>();

        int[] inDegree = new int[N + 1];

        for (int i = 0; i < M; i++) {
            children[U[i]].add(V[i]);
            inDegree[V[i]]++;
        }

        // 2. 위상정렬
        // 2-1. 루트 노드 찾기
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        List<Integer> topoOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            // 2-2. 큐에서 노드 꺼내기
            int node = queue.poll();
            topoOrder.add(node);
            // 2-3. 자식 노드들 inDegree 감소시키기
            for (int child : children[node]) {
                inDegree[child]--;
                // 2-4. inDegree가 0이 된 자식 노드
                if (inDegree[child] == 0) queue.add(child);
            }
        }
            // 3. dp(각 노드에서 도달 가능한 유니크 리프 수)
            int[] dp = new int[N + 1];
            boolean[] visitedLeaf = new boolean[N + 1];
            // 3-1. 리프부터 루트까지 위상정렬 역순으로 dp 계산
            for (int i = topoOrder.size() - 1; i >= 0; i--) {
                int node = topoOrder.get(i);
                if (children[node].isEmpty()) {
                    dp[node] = 1;
                    visitedLeaf[node] = true;
                } else {
                    for (int child : children[node]) {
                        dp[node] += dp[child];
                    }
                }
            }

            // 4. 루트에서 도달 가능한 유니크 리프 수 계산
            int maxPaths = 0;
            for (int i = 1; i <= N; i++) if (visitedLeaf[i]) maxPaths++;

            return maxPaths;
    }

    public static void main(String[] args) {
        // expected : 1
        System.out.println(sol2(6, 6, new int[]{1,1,2,3,4,5}, new int[]{2,3,4,5,6,6}));
        // expected: 2
        System.out.println(sol2(7, 6, new int[]{1,1,2,3,4,5}, new int[]{2,3,4,5,6,7}));
    }
}
