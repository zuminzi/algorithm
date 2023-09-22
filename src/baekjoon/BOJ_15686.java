package baekjoon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_15686 {
    private static int ans, N, M;
    private static int[][] arr;

    /**
     * 가능한 모든 조합을 탐색하여 최적의 조합을 찾는 완전 탐색(Exhaustive Search) 알고리즘
     * Key Point 1: min의 의미를 나타내거나 min과 비교해야 하는 변수 초기화 주의
     *              - 0으로 초기화하지 않거나, 비교 시 초기값 그대로 0이 아닌지 검사
     * Key Point 2 : 보통 조합에서는 각 요소에 대해 선택/선택안함의 선택지만 필요하고, 순열에서는 선택여부와 순서까지 고려해야 되기 때문에 별개의 output 자료구조로 원소 관리하지만,
     *               조합에서도 output 자료구조가 필요하다면, 고정 배열 output length는 기존 배열 length로 생성하거나 가변 자료구조 생성.
     * Key Point 3 : 중복를 허용하지 않는 조합에서 시간 줄이는 방법
     *               1) 백트래킹 루프문에서 visited 배열 대신 index부터 시작하여 중복 방문 방지
     *               2) 반복문 내에서 재귀호출할 때 index 자리에 index+1 대신 i(반복자 변수)+1를 인자로 넘겨 중복 줄이기
     *                  - 이미 for문을 돌리고 있으므로
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        ans = Integer.MAX_VALUE;
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][N];
        List<int[]> chickenShops = new ArrayList<>(); // 치킨 집 리스트

        // 배열 초기값 작업
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if(arr[i][j] == 2) { // 치킨 집이면
                    chickenShops.add(new int[]{i,j});
                }
            }
        }

        selectRemainingChickenShops(chickenShops, new ArrayList<>(), 0, 0);

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 조합(백트래킹 이용)으로 남겨둘 치킨 집 고르기
    private static void selectRemainingChickenShops(List<int[]> chickenShops, ArrayList<int[]> output, int index, int depth) {
        if(depth == M){
            getDistSum(output);
            return;
        }

        // Point 3
        for(int i=index; i<chickenShops.size(); i++){
            output.add(chickenShops.get(i));
            selectRemainingChickenShops(chickenShops, output, i+1, depth+1);
            output.remove(chickenShops.get(i));
        }
    }

    private static void getDistSum(List<int[]> output) {
        int distSum = 0;

        for(int j=0 ;j<N; j++) {
            for (int k = 0; k < N; k++) {
                if (arr[j][k] == 1) { // 일반 집이면
                    int minDist = Integer.MAX_VALUE;
                    for (int i = 0; i < output.size(); i++) {
                        minDist = Math.min(minDist, Math.abs(j - output.get(i)[0]) + Math.abs(k - output.get(i)[1]));
                    }
                    distSum += minDist;
                }
            }
        }
        if(distSum != 0) {
            ans = Math.min(ans, distSum);
        }
    }

}
