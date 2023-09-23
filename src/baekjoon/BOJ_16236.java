package baekjoon;

import java.io.*;
import java.util.*;

public class BOJ_16236 {
    private static int N, babySize, eatenCount, time;
    private static int[][] ocean;
    private static int[][] dir = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // 상, 좌, 하, 우

    /**
     * 최적의 경로(최단 거리 > y > x)로 물고기를 먹으며 아기 상어의 크기를 업데이트하고 이동 시간을 계산하는 BFS(너비 우선 탐색) 기반의 알고리즘
     * Point 1 : 하나의 정렬 기준만 있는 경우, Comparable<> 인터페이스의 compareTo 메서드를 구현하여 기본 정렬 기준 설정
     * Point 2 : 다양한 조건으로 인해 탐색 과정에서 최적의 선택이 어려운 경우 후보 노드 관리하는 자료구조 활용
     * Point 3: while 문을 종료하기 위한 조건을 문제에 맞게 설정 - 후보 노드가 더 이상 없다면 while문 종료
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        ocean = new int[N][N];
        Node babyShark = null;

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                ocean[i][j] = Integer.parseInt(st.nextToken());
                if (ocean[i][j] == 9) {
                    babyShark = new Node(i, j);
                    ocean[i][j] = 0; // 아기 상어의 시작 위치 확인했으므로, 사이즈 비교에 잘못 사용되지 않도록 0으로 초기화
                }
            }
        }

        babySize = 2;
        eatenCount = 0;
        time = 0;

        while (true) {
            Node closestFish = findClosestFish(babyShark);
            if (closestFish == null) break; // 더 이상 먹을 물고기가 없으면 종료

            int dist = closestFish.dist;
            time += dist;

            babyShark = closestFish; // 시작 좌표를 현재 좌표로 갱신
            ocean[babyShark.x][babyShark.y] = 0; // [문제 조건] 물고기를 먹으면, 그 칸은 빈 칸이 된다.
            eatenCount++;

            if (eatenCount >= babySize) {
                babySize++; // 상어 크기 업데이트
                eatenCount = 0; // 상어 크기 업데이트 조건 초기화
            }
        }

        System.out.println(time);
    }

    private static Node findClosestFish(Node start) {
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<Node> q = new LinkedList<>();
        q.offer(start); // 탐색할 큐에 추가
        dist[start.x][start.y] = 0; // 현재 거리값 갱신하여 방문 인증

        List<Node> candidates = new ArrayList<>();

        while (!q.isEmpty()) {
            Node curr = q.poll();

            for (int i=0; i< dir.length; i++) {
                int nx = curr.x + dir[i][0];
                int ny = curr.y + dir[i][1];

                if (nx >= 0 && nx < N && ny >= 0 && ny < N && dist[nx][ny] == -1) { // 인덱스 범위 및 방문 여부 검사
                    if (ocean[nx][ny] <= babySize) { // 아기 상어보다 사이즈가 작거나 같으면
                        q.offer(new Node(nx, ny));
                        dist[nx][ny] = dist[curr.x][curr.y] + 1; // 현재 거리값 갱신하여 방문 인증

                        if (ocean[nx][ny] > 0 && ocean[nx][ny] < babySize) { // 아기 상어보다 사이즈가 작으면
                            candidates.add(new Node(nx, ny, dist[nx][ny]));
                        }
                    }
                }
            }
        }

        if (candidates.isEmpty()) {
            return null;
        }

        Collections.sort(candidates);

        return candidates.get(0);
    }

    static class Node implements Comparable<Node> {
        int x, y, dist;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        // 거리 > y좌표 > x좌표 순으로 정렬
        // [문제 조건] 거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면, 가장 왼쪽에 있는 물고기 선택한다.
        @Override
        public int compareTo(Node o) {

            if (this.dist == o.dist) {
                if (this.x == o.x) {
                    return Integer.compare(this.y, o.y);
                }
                return Integer.compare(this.x, o.x);
            }
            return Integer.compare(this.dist, o.dist);
        }
    }
}