package programmers.lv3;

import java.util.*;

public class PG_43162 {
    // primitive type 중에 boolean 배열은 stream 지원 X
    // bool 배열에 stream을 사용하고 싶다면 Boolean[]으로 생성
    // ex. if((Arrays.stream(visited).filter(el -> el == true).count()==visited.length)) return; // 그러나 이는 효율상 적용안하는 게 나음
    // https://stackoverflow.com/questions/42225001/java-8-boolean-primitive-array-to-stream

    // DFS
    // ~0.23ms, 70.6MB
    public int codeOfMine(int n, int[][] computers) {
        int networkCnt = 0;
        boolean[] visited = new boolean[n];
        if(n != 1) {
            for (int i = 0; i < computers.length; i++) {
                if (!visited[i]) {
                    makeGraph(i, visited, computers);
                    networkCnt++;
                }
            }
        } else {
            networkCnt = 1;
        }
        return networkCnt;
    }

    private void makeGraph(int root, boolean[] visited, int[][] graph) {
        // Problem Point
        // 재귀 호출 후 처음 실행하는 실행문
        visited[root] = true;
        for (int i=0; i < visited.length; i++) {
            if (graph[root][i] == 1 && !visited[i]) {
                // graph에 방문체크 안해도 visited[i]가 true이기 때문에 for문 횟수나 시간 차이 X
                // graph[root][i] = -1;
                // graph[i][root] = -1;
                makeGraph(i, visited, graph);
            }
        }
    }

    // DFS
    // 0.21ms, 79.2MB
    // 정점 방문 체크는 boolean[] chk로만
    // computers[root][i] = -1로 방문 체크 작업 X
    public int exam1(int n, int[][] computers) {
        int answer = 0;
        boolean[] chk = new boolean[n];
        for(int i = 0; i < n; i++) {
            if(!chk[i]) {
                dfs(computers, chk, i);
                answer++;
            }
        }
        return answer;
    }
    void dfs(int[][] computers, boolean[] chk, int start) {
        chk[start] = true;
        for(int i = 0; i < computers.length; i++) {
            if(computers[start][i] == 1 && !chk[i]) {
                dfs(computers, chk, i);
            }
        }
    }

    // BFS
    // ~0.87ms, 75.6MB
    // 좌표를 저장할 클래스를 선언한다.
    class Pair {
        int x;
        int y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public int exam2(int n, int[][] computers) {
        int answer = 0;

        boolean[] visited = new boolean[n];

        // 1. 2차원 배열에서 대각선을 포함한 반을 탐색하면서 방문하지 않고 연결되어 있을 경우 bfs 실행
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (computers[i][j] == 1 && !visited[i]) {
                    answer++;
                    bfs(i, j, computers, n, visited); // i,j는 정점 좌표
                }
            }
        }
        return answer;
    }

    public void bfs(int i, int j, int[][] computers, int n, boolean[] visited) {
        // 2. 방문 체크
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(i, j));
        visited[i] = true;

        // 6. 인접 정점들(x좌표) 탐색하면 LinkedList에 담긴 다음 깊이 탐색
        while (!q.isEmpty()) {
            Pair cur = q.poll();

            // 3. 해당 컴퓨터와 새롭게 이어진 컴퓨터를 찾아보자
            // 3. x 좌표의 컴퓨터와 이어진 컴퓨터 찾기
            int ny = cur.x; // ny = 현재의 x 좌표와 이어진 행
            for (int nx = 0; nx <= n; nx++) { // nx = 현재의 x 좌표와 이어진 열
                if (0 <= nx && nx < n && 0 <= ny && ny < n) {
                    if (!visited[nx] && computers[nx][ny] == 1) {
                        // 4. 이어진 컴퓨터 방문 체크
                        visited[nx] = true;
                        // 5. 해당 코드는 인접 정점들을 탐색하는 방법이므로
                        // 5. 정점의 다음 깊이를 바로 탐색하지 않고 Queue에 추가만
                        q.offer(new Pair(nx, ny));
                    }
                }
            }
        }
    }

    public static void main (String[] args){
    PG_43162 pg_43162 = new PG_43162();
    // expected : 2
    System.out.println(pg_43162.exam2(3,new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));
    // expected : 1
    System.out.println(pg_43162.exam2(3,new int[][]{{1, 1, 0}, {1, 1, 1}, {0, 1, 1}}));
    // expected : 1
    System.out.println(pg_43162.exam2(3, new int[][]{{1,1,1},{1,1,1},{1,1,1}}));
    // expected : 1
    System.out.println(pg_43162.exam2(7, new int[][]{{1,0,0,0,0,0,1}, {0,1,1,0,1,0,0}, {0,1,1,1,0,0,0}, {0,0,1,1,0,0,0},
            {0,1,0,0,1,1,0}, {0,0,0,0,1,1,1}, {1,0,0,0,0,1,1}}));
    // expected: 1
    System.out.println(pg_43162.exam2(6, new int[][]{{1, 0, 1, 1, 0, 0}, {0, 1, 0, 0, 1, 1},{1, 0, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}}));
    // expected : 2
    System.out.println(pg_43162.exam2(5,new int[][]{{1,1,1,0,0}, {1,1,0,0,0}, {1,0,1,0,0},{0,0,0,1,1},{0,0,0,1,1}}));
    // expected : 1
    System.out.println(pg_43162.exam2(4, new int[][]{{1, 1, 0, 1}, {1, 1, 0, 0}, {0, 0, 1, 1}, {1, 0, 1, 1}}));
    // expected : 2 // 단방향(유향) 그래프 ** // 양방향(무방향)인 예시와 달리 단방향도 주어짐
    System.out.println(pg_43162.exam2(3,new int[][]{{1,1,0},{0,1,0},{0,0,1}}));
    }
}