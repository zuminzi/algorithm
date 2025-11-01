package programmers.lv2;

import java.util.LinkedList;
import java.util.Queue;

public class PG_159993 {
    // BFS를 두 번 사용해 S→L / L→E로 가는 두 구간의 최단 거리를 각각 구하여 합산
    private char[][] graph;
    private int[] dx = {0, 0, -1, 1};
    private int[] dy = {1, -1, 0, 0};
    private class Node {
        int x;
        int y;
        int sec;
        public Node(int x, int y, int sec) {
            this.x = x;
            this.y = y;
            this.sec = sec;
        }
    }
    public int solution(String[] maps) {
        graph = new char[maps.length][maps[0].length()];
        int[] start = new int[2];
        int[] lever = new int[2];
        int[] end = new int[2];
        // 1. 최소 시간을 배열로 관리 // 단, 각 탐색마다 오염되지 않도록 최종 결과값만 저장
        int[][] time = new int[maps.length][maps[0].length()];

        for (int y = 0; y < maps.length; y++) {
            for(int x = 0; x < maps[y].length(); x++) {
                graph[y][x] = maps[y].charAt(x);
                if(graph[y][x] == 'S') {
                    start[0] = x;
                    start[1] = y;
                } else if (graph[y][x] == 'L') {
                    lever[0] = x;
                    lever[1] = y;
                } else if(graph[y][x] == 'E') {
                    end[0] = x;
                    end[1] = y;
                }
                time[y][x] = Integer.MAX_VALUE;
            }
        }

        BFS(start[0], start[1], lever[0], lever[1], time);
        if(time[lever[1]][lever[0]] == Integer.MAX_VALUE) {
            return -1;
        }

        BFS(lever[0], lever[1], end[0], end[1], time);
        return time[end[1]][end[0]] == Integer.MAX_VALUE ? -1 : time[lever[1]][lever[0]] + time[end[1]][end[0]];
    }

    private void BFS(int startX, int startY, int targetX, int targetY, int[][] time) {
        Queue<Node> q = new LinkedList<>();
        // 2. 중복 방문 가능하다고 방문체크 안하면 StackOverFlow 발생
        // 각 BFS는 독립적인 탐색으로, 첫 번째 BFS가 끝나면 visited 배열을 초기화하여 두 번째 BFS 새로 탐색
        boolean[][] visited = new boolean[graph.length][graph[0].length];
        q.add(new Node(startX, startY, 0));
        visited[startY][startX] = true;

        while(!q.isEmpty()) {
            Node node = q.poll();
            if(node.x == targetX && node.y == targetY) {
                time[node.y][node.x] = Math.min(time[node.y][node.x], node.sec);
            }
            for(int i = 0; i < 4; i++) {
                int newX = node.x + dx[i];
                int newY = node.y + dy[i];
                if(newX >= 0 && newX < graph[0].length && newY >= 0 && newY < graph.length) {
                    if(graph[newY][newX] != 'X' && !visited[newY][newX]) {
                        visited[newY][newX] = true;
                        q.add(new Node(newX, newY, node.sec+1));
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        PG_159993 pg159993 = new PG_159993();
        // expected: 16
        System.out.println(pg159993.solution(new String[] {"SOOOL", "XXXXO", "OOOOO", "OXXXX", "OOOOE"}));
    }
}
