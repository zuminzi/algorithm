package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2644 {
    static int n, m, p1, p2;
    static List<Integer>[] graph;
    static boolean[] visited;
    /**
     * 가계도(부모-자식 관계)를 기반으로 두 사람 간의 촌수 또는 친척 거리를 계산하기 위한 BFS 알고리즘
     */
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        p1 = Integer.parseInt(st.nextToken());
        p2 = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(br.readLine());
        graph = new ArrayList[n + 1]; // 문제에서는 넘버링을 1부터 시작하므로 1~n+1을 관리하기 위해
        visited = new boolean[n + 1];

        // 각 정점의 간선 리스트 생성
        for (int i = 0; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        // input으로부터 받아서 각 정점의 간선 추가
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            graph[parent].add(child);
            graph[child].add(parent);
        }

        int result = bfs(p1, 0);

        System.out.println(result);
    }

    static int bfs(int start, int distance) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        queue.add(distance);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            int d = queue.poll();

            if (curr == p2) {
                return d;
            }

            for (int neighbor : graph[curr]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                    queue.add(d + 1);
                }
            }
        }
        return -1;
    }

    static int N, A, B, M;
    static int[][] adjMatrix;
    static boolean[] checked;
    static int chon = -1;
    /**
     * 가계도(부모-자식 관계)를 기반으로 두 사람 간의 촌수 또는 친척 거리를 계산하기 위한 DFS 알고리즘
     */
    public static void dfs() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        adjMatrix = new int[N + 1][N + 1];
        checked = new boolean[N + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        M = Integer.parseInt(br.readLine());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adjMatrix[a][b] = 1;
            adjMatrix[b][a] = 1;
        }

        dfs(A, 0);

        System.out.println(chon);
    }

    private static void dfs(int a, int depth) {
        if (a == B) {
            chon = depth;
        }

        checked[a] = true;


        for (int i = 1; i <= N; i++) {
            if (adjMatrix[a][i] == 1 && !checked[i]) {
                dfs(i, depth + 1);
            }
        }

    }
}
