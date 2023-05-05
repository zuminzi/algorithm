package concept.graph;

import java.util.LinkedList;
import java.util.Queue;

// 그래프 최단거리 탐색 알고리즘
// [input] e.g [[1,0,1,1,1],[1,0,1,0,1],[1,0,1,1,1],[1,1,1,0,1],[0,0,0,0,1]] // 가중치가 0 or 1
// [problem] e.g https://school.programmers.co.kr/learn/courses/30/lessons/1844
public class BFS {
    private boolean[][] visited;
    // {x,y} => {0,1}, {0,-1}, {-1,0}, {1,0} // 상, 하, 좌, 우
    int[] dx = {0,0,-1,1};
    int[] dy = {1,-1,0,0};

    public class Node {
        int x;
        int y;
        int cost;

        public Node(int x, int y, int cost){
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }

    public int solution(int[][] map){
        visited = new boolean[map.length][map[0].length];
        return bfs(map);
    }

    private int bfs(int[][] map) { // map [y][x]
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(0, 0, 1));
        visited[0][0] = true;

        while (!q.isEmpty()) {
            Node node = q.poll();
            int x = node.x;
            int y = node.y;
            int cost = node.cost;
            if(y == map.length && x == map[y].length)
            return cost;

            for(int i=0; i<4; i++){
                int nx = node.x + dx[i];
                int ny = node.y + dy[i];
                if(ny >= 0 && nx >= 0 && ny < map.length && nx < map[y].length) {
                    // 가중치가 1이고, 방문하지 않았으면
                    if(map[ny][nx] == 1 && !visited[ny][nx]) {
                        visited[ny][nx] = true;
                        q.add(new Node(nx, ny, cost+1));
                    }
                }
            }
        }
    return -1;
    }
}
