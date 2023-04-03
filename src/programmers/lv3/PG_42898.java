package programmers.lv3;

public class PG_42898 {
    // ~1.27ms, 52.5MB
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

    // ~0.88ms, 52.4MB
    public int exam(int m, int n, int[][] puddles) {
        // (1,1)부터 시작하기 위해 m+1행, n+1열로 셋팅
        int[][] dp = new int[m+1][n+1];
        for(int i=0;i<puddles.length;i++){
            dp[puddles[i][0]][puddles[i][1]]=-1;
        }
        dp[1][1]=1;
        // 문제에서 좌표가 (0,0) 대신 (1,1)로 시작하므로 똑같이 셋팅
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(dp[i][j]==-1){
                    // 다음 덧셈에 영향이 없는 0으로 처리
                    dp[i][j]=0;
                    continue;
                }
                // 1행과 1열은 인접 행 or 열만 합 계산
                if(i!=1)    dp[i][j] = dp[i-1][j]%1000000007;
                if(j!=1)    dp[i][j] += dp[i][j-1]%1000000007;
            }
        }
        return dp[m][n]%1000000007;
    }

    public static void main (String[] args){
        PG_42898 pg_42898 = new PG_42898();
        // expected : 4
        System.out.println(pg_42898.exam(4,3,new int[][]{{2,2}}));
        // expected : 4
        System.out.println(pg_42898.exam(4,3,new int[][]{{2,1}}));
        // expected : 6
        System.out.println(pg_42898.exam(4,3,new int[][]{{1,2}}));
        // 웅덩이로 전부 막힌 경우
        // expected : 0
        System.out.println(pg_42898.exam(3,2,new int[][]{{2,1},{3,1},{1,2},{2,2}}));
        // 행이 하나인 경우
        // expected : 1
        System.out.println(pg_42898.exam(2,1,new int[][]{}));
        // 열이 하나인 경우
        // expected : 1
        System.out.println(pg_42898.exam(1,2,new int[][]{}));
    }
}
