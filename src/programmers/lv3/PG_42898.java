package programmers.lv3;

public class PG_42898 {
    public int solution(int m, int n, int[][] puddles) {
        int[][] dp = new int[n][m];

        // 웅덩이는 -1 처리
        for (int i = 0; i < puddles.length; i++) {
            // Problem Point **
            // 주어진 (m,n) = (x,y)이므로 배열 puddle 또한 puddle[y][x]
            int col = puddles[i][0] - 1;
            int row = puddles[i][1] - 1;
            dp[row][col] = -1;
        }

        for (int i = 1; i < Math.max(m, n); i++) { // i가 행, k가 열
            for (int k = 0; k < i; k++) {
                if (i >= n || k >= m) break;
                if (k == 0) {
                    // Problem Point
                    // 이 라인에서도 웅덩이(-1)에 해당할 경우 예외 처리
                    if (dp[i][0] != -1 ) {
                        if(dp[i-1][0] != -1) {
                            dp[i][k] = 1;
                        // 바로 전 요소뿐만 아니라 이전 요소에 한번이라도 해당할 경우 거치지 못하므로 접근 불가 처리
                        } else {
                            dp[i][k] = -1;
                        }
                    }
                } else {
                    if (dp[i][k] == -1) continue;
                    int prevCol = dp[i][k - 1] != -1 ? dp[i][k - 1] : 0;
                    int prevRow = dp[i - 1][k] != -1 ? dp[i - 1][k] : 0;
                    dp[i][k] = (prevCol + prevRow) % 1000000007;
                }
            }
            for (int k = 0; k <= i; k++) { // k가 행, i가 열
                if (k >= n || i >= m) break;
                if (k == 0) {
                    // Problem Point
                    // 이 라인에서도 웅덩이(-1)에 해당할 경우 예외 처리
                    if (dp[0][i] != -1) {
                        if(dp[0][i-1] != -1) {
                            dp[k][i] = 1;
                        } else {
                            dp[k][i] = -1;
                        }
                    }
                } else {
                    if (dp[k][i] == -1) continue;
                    int prevCol = dp[k][i - 1] != -1 ? dp[k][i - 1] : 0;
                    int prevRow = dp[k - 1][i] != -1 ? dp[k - 1][i] : 0;
                    dp[k][i] = (prevCol + prevRow) % 1000000007;
                }
            }
        }
        return dp[n - 1][m - 1];
    }

    public static void main (String[] args){
        PG_42898 pg_42898 = new PG_42898();
        // expected : 4
        System.out.println(pg_42898.solution(4,3,new int[][]{{2,2}}));
        // expected : 4
        System.out.println(pg_42898.solution(4,3,new int[][]{{2,1}}));
        // expected : 6
        System.out.println(pg_42898.solution(4,3,new int[][]{{1,2}}));
        // 웅덩이로 전부 막힌 경우
        // expected : 0
        System.out.println(pg_42898.solution(3,2,new int[][]{{2,1},{3,1},{1,2},{2,2}}));
        // 행이 하나인 경우
        // expected : 1
        System.out.println(pg_42898.solution(2,1,new int[][]{}));
        // 열이 하나인 경우
        // expected : 1
        System.out.println(pg_42898.solution(1,2,new int[][]{}));
    }
}
