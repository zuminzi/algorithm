package baekjoon;

import java.io.*;
import java.util.*;

public class BOJ_16236 {
    private static int ans; // 최단 시간(이동 거리) 합
    private static int N, babySize, eatenCount;
    private static int[][] dir = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        ans = 0;
        babySize = 2;
        eatenCount = 0;
        int[][] arr = new int[N][N];
        int[] startPoint = new int[2];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int k = 0; k < N; k++) {
                int target = Integer.parseInt(st.nextToken());
                if (target == 9) {
                    startPoint = new int[]{i, k}; // 아기 상어의 시작 위치 확인 후, 해당 좌표의 값은 0으로 설정하기 위해 대입 X
                } else {
                    arr[i][k] = target;
                }
            }
        }

        while (true) { // 먹이 한 번 먹을 때마다 루프문 1회 실행
            PriorityQueue<Point> candidates = findFeed(arr, startPoint);

            if (candidates.isEmpty()) {
                break;
            }

            Point feed = candidates.poll();

            startPoint = new int[]{feed.r, feed.c};
            ans += feed.dist;
            arr[startPoint[0]][startPoint[1]] = 0;
            if (++eatenCount == babySize) {
                babySize++;
                eatenCount = 0; // 초기화
            }
        }

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static PriorityQueue<Point> findFeed(int[][] arr, int[] startPoint) {
        boolean[][] visited = new boolean[N][N];
        Queue<Point> q = new LinkedList<>();
        q.offer(new Point(startPoint[0], startPoint[1], 0));
        visited[startPoint[0]][startPoint[1]] = true;

        PriorityQueue<Point> candidates = new PriorityQueue<>(new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if (p1.dist != p2.dist) {
                    return Integer.compare(p1.dist, p2.dist); // 오름차순
                } else if (p1.r != p2.r) {
                    return Integer.compare(p1.r, p2.r); // 오름차순
                } else {
                    return Integer.compare(p1.c, p2.c); // 오름차순
                }
            }
        });

        while (!q.isEmpty()) {
            Point curr = q.poll();

            for (int i = 0; i < 4; i++) {
                int nr = curr.r + dir[i][0];
                int nc = curr.c + dir[i][1];
                int currDist = curr.dist + 1;

                if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                    if(!visited[nr][nc]) {
                        int otherSize = arr[nr][nc];

                        if (babySize >= otherSize) {
                            visited[nr][nc] = true;
                            q.offer(new Point(nr, nc, currDist));

                            if (babySize > otherSize && otherSize != 0) {
                                candidates.add(new Point(nr, nc, currDist));
//                                break; // Problem Point // 상하좌우로 이동한 후보들을 모두 담아야 하기 때문에 break X
                            }
                        }
                    }
                }
            }
        }

        return candidates;
    }
}

class Point {
    int r, c, dist;

    Point(int r, int c, int dist) {
        this.r = r;
        this.c = c;
        this.dist = dist;
    }
}
