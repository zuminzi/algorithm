package baekjoon;

import java.util.Scanner;

public class BOJ_9663 {
    private static int ans;

    /**
     * NxN 체스판에 N개의 퀸을 서로 공격할 수 없도록 1차원 배열로 행 기록하며 배치하는 백트래킹 알고리즘
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ans = 0;
        int[] checked = new int[n];

        findNumberOfCases(checked, 0, n);

        System.out.println(ans);
    }

    private static void findNumberOfCases(int[] checked, int depth, int n) {
    if(depth == n){
        ans++;
        return;
    }

    // 행은 순차대로 루프문 돌리기 때문에 중복 검사 필요 없음
    for(int col=0; col<n; col++){
        checked[depth] = col; // 열 기록
        if(isPossible(checked, depth, col)){
            findNumberOfCases(checked, depth+1, n);
            //checked[depth] = 0; // 0인지 아닌지로 체크하지 않으므로 필요 없음
        }
    }
    }

    private static boolean isPossible(int[] checked, int depth, int col) {
    for(int i=0; i<depth; i++){
        // 동일한 열인지 검증
        if(checked[i] == col) {
            return false;
        }
        // 동일한 대각선인지 검증
        if(Math.abs(checked[depth] - checked[i]) == Math.abs(depth - i)){
            return false;
        }
    }
        return true;
    }
}
