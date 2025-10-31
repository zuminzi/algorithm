package programmers.etc;

import java.util.Arrays;

//~10:20
public class PG_389480 {
    // [방법 1] DP
    public int sol1(int[][] info, int n, int m) {
       int total = info.length;
       int INF = 120;
       // B는 정해진 합 (constraint), A는 최소화해야 할 값 (objective)이므로 dp[bSum] = A의 최소합
       // constraint를 상태로, objective를 값으로 두는 게 정석
       int[][] dp = new int[total+1][m];
       for (int i=0; i<=total; i++) Arrays.fill(dp[i], INF);

       // 1. 상태 저장
       // 아무 물건도 가져가지 않은 경우
       dp[0][0] = 0;
       for (int i=1; i<=dp[0].length; i++){
           int a = info[i - 1][0];
           int b = info[i - 1][1];
           // 이전 상태 전이하기 위해 조회
           for (int j=0; j<dp.length; j++) {
               // 1-1. A가 훔치는 경우
               dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + a);

               // 1-2. B가 훔치는 경우
               if (j + b < m) dp[i][j+b] = Math.min(dp[i][j+b], dp[i-1][j]);
           }
       }
       // 2. 최솟값 조회
        int ans = INF;
        for (int j = 0; j < m; j++) ans = Math.min(ans, dp[total][j]);
        // a의 최솟값이 n 이상이면 해가 없는 문제
        return ans < n ? ans : -1;
    }

    // [방법 2] 그리디 + 완전탐색(DFS + 백트래킹)
    public int answer;
    public int sol2 (int[][] info, int n, int m){
        answer = Integer.MAX_VALUE;
        // 1. 위험도가 큰 물건을 조기 평가하도록 정렬
        Arrays.sort(info, (a, b) -> (b[0] + b[1]) - (a[0] + a[1]));
        powerSet(info, 0, 0, 0, n, m);
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    // visited 배열은 필요 없음(인덱스 기반으로 include/exclude 분기)
    private void powerSet(int[][] info, int idx, int sumA, int sumB, int n, int m) {
        int total  = info.length;

        // 2. 조기 가지치기: 이미 조건을 만족할 수 없는 경우
        if (sumA >= n) return;
        if (sumB >= m) return;
        if (sumA >= answer) return;

        if(idx == total){
            answer = Math.min(answer, sumA);
            return;
        }

        // 3. 케이스 분류하여 재귀 호출 시점에 sumA, sumB를 누적해서 넘기기
        // 각 리프(부분집합 완성)에서 전체 합 다시 계산 시, O(n * 2^n)이지만, 누적해서 넘기면 O(1)
        // 3-1. info[idx]를 A에 포함시키는 경우
        powerSet(info, idx + 1, sumA + info[idx][0], sumB, n, m);
        // 3-2. info[idx]를 A에 포함하지 않는 경우
        powerSet(info, idx + 1, sumA, sumB + info[idx][1], n, m);
    }

    public static void main(String[] args) {
        PG_389480 pg389480 = new PG_389480();
        // expected: 2
        System.out.println(pg389480.sol2(
                new int[][]{{1, 2}, {2, 3}, {2, 1}},
                4,
                4
        ));
        // expected: 0
        System.out.println(pg389480.sol2(
                new int[][]{{1, 2}, {2, 3}, {2, 1}},
                1,
                7
        ));
        // expected: 6
        System.out.println(pg389480.sol2(
                new int[][]	{{3, 3}, {3, 3}},
                7,
                1
        ));
        // expected: -1
        System.out.println(pg389480.sol2(
                new int[][]	{{3, 3}, {3, 3}},
                6,
                1

        ));
    }
}
