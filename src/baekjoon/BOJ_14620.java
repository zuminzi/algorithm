package baekjoon;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BOJ_14620 {
    // 최소비용으로 겹치지 않게
    // 두 꽃이 겹치지 않으려면 중심 간의 거리가 최소 3 이상
    // 직선 거리(대각선 포함)가 아닌 상하좌우로만 움직인다면 유클리드 공식 대신 맨해튼 공식 사용 가능
    // 유클리드: Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    static final int FLOWER_CNT = 3;
    static int answer  = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });

        int[][] garden = new int[N][N];
        for(int i=0; i<N; i++){
            String[] line = br.readLine().split(" ");
            for(int j=0; j<N; j++){
                garden[i][j] = Integer.parseInt(line[j]);
            }
        }
        DFS(garden, 0, new int[FLOWER_CNT][2], 0);
        bw.write(String.valueOf(answer));
        bw.flush();
    }
    private static void DFS(int[][] garden, int depth, int[][] output, int cost) {
        if(depth == FLOWER_CNT){
            answer = Math.min(answer, cost);
            return;
        }
        for (int i=1; i<garden.length-1; i++){
            for (int j=1; j<garden.length-1; j++){
                boolean canPlant = true;
                for(int k=0; k<depth; k++){
                    int distance = Math.abs(output[k][0] - i) + Math.abs(output[k][1] - j);
                    if(distance < 3){
                        canPlant = false;
                        break;
                    }
                }
                if(canPlant){
                    output[depth][0] = i;
                    output[depth][1] = j;
                    int newCost = cost + garden[i][j] + garden[i-1][j] + garden[i+1][j] + garden[i][j-1] + garden[i][j+1];
                    DFS(garden, depth+1, output, newCost);
                }

            }
        }
    }

}
