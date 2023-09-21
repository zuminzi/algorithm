package baekjoon;

import java.io.*;
import java.util.*;

public class BOJ_16234 {
    private static int N, L, R, ans;
    private static int[][] arr;
    private static boolean[][] visited;
    static int[][] dir = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

    /**
     * 각 나라를 탐색하면서 연합을 찾고, 연합에 속한 나라들끼리 인구를 재분배하는 인구 이동 시뮬레이션
     * Key Point 1 : 완전 탐색
     * Key Point 2 : 2차원 배열에 대입하는 인덱스(row, column) 순서 바뀌지 않게 주의
     * Key Point 3 : 반복문에서 사용되는 변수 초기화
     * Key Point 4 : 무조건 한 번은 실행해야 하므로 do-while문 사용
     * 이 코드는 https://codin9.tistory.com/3 를 참고했습니다.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        ans = 0;
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        arr = new int[N][N];

        // 각 나라의 인구 수 배열 초기화 작업
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean moved;
        do {
            moved = false; // 인구 이동 여부 초기화
            visited = new boolean[N][N]; // 각 나라 방문 여부 초기화

            // 하루에 연합이 여러 개로 갈라져있을 수 있으므로 완전 탐색
            for(int i=0; i<N; i++){
                for(int j=0; j<N; j++){
                    if(!visited[i][j]) { // 방문하지 않았다면
                        List<Country> alliances = new ArrayList<>();
                        if(hasUnion(alliances, i, j)) {// 연합이 존재한다면
                            int sum = alliances.stream().mapToInt(country -> country.population).sum();
                            for(Country country : alliances) { // 연합 인구 재분배
                                arr[country.r][country.c] = sum / alliances.size();
                            }
                            moved = true;
                        }
                    }
                }
            }

            if (moved) ans++;

        } while (moved);

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // BFS
    private static boolean hasUnion(List<Country> alliances, int startR, int startC) {
        Queue<Country> q = new LinkedList<>();
        q.offer(new Country(startR, startC));
        alliances.add(new Country(startR, startC, arr[startR][startC]));
        visited[startR][startC] = true;

        while (!q.isEmpty()) {
            Country curr = q.poll();

            for(int i=0; i<4; i++) {
                int nr = curr.r + dir[i][0];
                int nc = curr.c + dir[i][1];
                if (nr >= 0 && nc >= 0 && nr < N && nc < N) { // 인덱스 범위 내에 있는지 체크
                    if (!visited[nr][nc]) { // 방문여부 체크
                        int diff = Math.abs(arr[curr.r][curr.c] - arr[nr][nc]);
                        if (diff >= L && diff <= R) { // 연합 가능여부 체크
                            visited[nr][nc] = true;
                            q.add(new Country(nr, nc));
                            alliances.add(new Country(nr, nc, arr[nr][nc]));
                            // sum += arr[nr][nc]; // 효율을 줄이는 방법이지만 메서드명에는 맞지 않는 기능
                        }
                    }
                }
            }
        }

        return alliances.size() > 1;
    }
}

class Country {
    int r, c, population;

    public Country(int r, int c) {
        this.r = r;
        this.c = c;
    }
    public Country(int r, int c, int population) {
        this.r = r;
        this.c = c;
        this.population = population;
    }
}