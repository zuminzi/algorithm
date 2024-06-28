package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

/*
 * BufferedReader -> throws IOException 예외 시그니처 명시 필요
 */
public class BOJ_1749 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int totalRow = Integer.parseInt(st.nextToken());
        int totalCol = Integer.parseInt(st.nextToken());


        int[][] prefix_sum = new int[totalRow + 1][totalCol + 1];
        for (int i = 1; i < prefix_sum.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < prefix_sum[0].length; j++) {
                prefix_sum[i][j] = Integer.parseInt(st.nextToken()) + prefix_sum[i][j - 1] + prefix_sum[i - 1][j] - prefix_sum[i - 1][j - 1];
            }
        }

        int max = prefix_sum[1][1];
        for (int i = 1; i < prefix_sum.length; i++) {
            for (int j = 1; j < prefix_sum[0].length; j++) {
                for (int r = 0; r < i; r++) {
                    for (int c = 0; c < j; c++) {
                        max = Math.max(max, prefix_sum[i][j] - prefix_sum[r][j] - prefix_sum[i][c] + prefix_sum[r][c]);
                    }
                }
            }
        }
        System.out.println(max);
    }
}