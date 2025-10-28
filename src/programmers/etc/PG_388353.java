package programmers.etc;

import java.util.LinkedList;
import java.util.Queue;

public class PG_388353 {
    private class Node {
        int y, x;
        Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    private int[] dx = {0, 0, -1, 1};
    private int[] dy = {1, -1, 0, 0};
    private int remaining;
    private int n, m;
    private char[][] map;

    // [방법 1] BFS
    public int sol1(String[] storage, String[] requests) {
        n = storage.length;
        m = storage[0].length();
        remaining = n * m;
        map = new char[n][m];

        for (int i = 0; i < n; i++) {
            map[i] = storage[i].toCharArray();
        }

        for (String request : requests) {
            // 1. 케이스 분류
            if (request.length() == 2) {
                useCrane(request.charAt(0));
            } else
                useCarBFS(request.charAt(0));
            }

        return remaining;
    }

    private void useCrane(char target) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == target) {
                    map[i][j] = '.'; // 전역 처리
                    remaining--;
                }
            }
        }
    }

    private void useCarBFS(char target) {
        boolean[][] visited = new boolean[n][m];

        // 2. 테두리 전체를 시작점으로 잡기
        for (int x = 0; x < m; x++) {
            if (!visited[0][x]) BFS(0, x, target, visited);
            if (!visited[n - 1][x]) BFS(n - 1, x, target, visited);
        }
        for (int y = 1; y < n - 1; y++) {
            if(!visited[y][0]) BFS(y, 0, target, visited);
            if(!visited[y][m - 1]) BFS(y, m - 1, target, visited);
        }
    }

    // @param visited: 해당 요청에 한해서만 방문 처리
    private void BFS(int y, int x, char target, boolean[][] visited) {
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(y, x));
        // 3. 제일 초기 지점 방문 처리
        visited[y][x] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            // 4-1. 전역적으로 이미 방문했을 때
            if(map[cur.y][cur.x] == '.') {
                for (int dir = 0; dir < 4; dir++) {
                    int ny = cur.y + dy[dir];
                    int nx = cur.x + dx[dir];

                    if (ny >= 0 && nx >= 0 && ny < n && nx < m) {
                        // 4-1-1. 해당 요청에 한해서만 방문하지 않았을 때 추가 탐색
                        if(!visited[ny][nx]) {
                            visited[ny][nx] = true;
                            q.add(new Node(ny, nx));
                        }
                    }
                }
                // 4-2. 전역적으로 방문하지 않았을 때
            } else {
                if (map[cur.y][cur.x] == target) {
                    map[cur.y][cur.x] = '.'; // 전역 처리
                    remaining--;
                    // 여기서 return 하면 안됨. 같은 요청에 대해 나머지 상하좌우는 탐색해야 하므로.
                }
            }
        }
    }

    // [방법 2] DFS
    public int sol2(String[] storage, String[] requests) {
       n = storage.length;
       m = storage[0].length();
       remaining = n * m;
       map = new char[n][m];

       for(int i=0; i<n; i++){
           map[i] = storage[i].toCharArray();
       }

       // 1. 케이스 분류
       for(String request: requests){
           if(request.length() == 2) {
                useCrane(request.charAt(0));
           } else {
               useCarDFS(request.charAt(0));
           }
       }
       return remaining;
    }

    private void useCarDFS(char target) {
        boolean[][] visited = new boolean[n][m];
        // 2. 테두리 전체를 시작점으로 잡기
        for (int i=0; i<n; i++) {
            if(!visited[i][0]) DFS(i, 0, target, visited);
            if(!visited[i][m-1]) DFS(i, m-1, target, visited);
        }
        for (int i=1; i<m-1; i++) {
            if(!visited[0][i]) DFS(0, i, target, visited);
            if(!visited[n-1][i]) DFS(n-1, i, target, visited);
        }
    }

    // @param visited: 해당 요청에 한해서만 방문 처리
    private void DFS (int y, int x, char target, boolean[][] visited) {
        // 3. 제일 초기 지점 방문 처리
        visited[y][x] = true;

        // 4-1. 전역적으로 방문하지 않았을 때
        if(map[y][x] == target) {
            map[y][x] = '.'; // 전역 처리
            remaining--;
        // 4-2. 전역적으로 이미 방문했을 때
        } else if (map[y][x] == '.') { // else if는 상호배타적인 조건문으로 이전 조건문에서 if ~ return처럼 동작.
            for(int dir=0; dir<4; dir++) {
                int ny = y + dy[dir];
                int nx = x + dx[dir];
                if (ny >= 0 && nx >= 0 && ny < n && nx < m) {
                    // 4-2-1. 해당 요청에 한해서만 방문하지 않았을 때 추가 탐색
                    if (!visited[ny][nx]) {
                        visited[ny][nx] = true;
                        DFS(ny, nx, target, visited);
                        // 영역 탐색은 한번 방문한 칸을 다시 방문할 일이 없으므로 visited[ny][nx] = false 불필요.
                        // 그러나, 조합 탐색(N-Queen, 부분수열, 경로 완전탐색 등)은 다른 경로에서 같은 원소를 사용할 수 있으므로 상태 복구 필요.
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // expected: 11
        PG_388353 pg = new PG_388353();
        System.out.println(pg.sol2(
                new String[]{"AZWQY", "CAABX", "BBDDA", "ACACA"},
                new String[]{"A", "BB", "A"}
        ));
    }
}
