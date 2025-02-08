package programmers.lv3;

public class PG_49191 {
        // 플로이드-워셜 알고리즘(모든 노드 간의 최단 경로)의 경로갱신 개념 차용 (cf. 다익스트라: 한 지점에서 다른 특정 지점까지의 최단 경로)
        public int solution(int n, int[][] results) {
            int answer = 0;
            int[][] floyd = new int[n+1][n+1];

            // 초기 결과 입력 (승, 패 관계 기록)
            for(int i=0; i<results.length; i++){
                int A = results[i][0];
                int B = results[i][1];

                floyd[A][B] = 1; // A가 B를 이겼으므로 (A,B) = 1
                floyd[B][A] = -1; // B는 A에게 졌으므로 (B,A) = -1
            }

            // 간접적인 승패 관계 파악 (i -> j, j -> k가 있으면 i -> k도 성립)
            for(int i=1; i<=n; i++){
                for(int j=1; j<=n; j++){
                    for(int k=1; k<=n; k++){
                        if(floyd[i][k] == 1 && floyd[k][j] == 1){
                            floyd[i][j] = 1;
                            floyd[j][i] = -1;
                        } else if(floyd[i][k] == -1 && floyd[k][j] == -1){
                            floyd[i][j] = -1;
                            floyd[j][i] = 1;
                        }
                    }
                }
            }
            for(int i = 1; i <= n; i++){
                int cnt = 0;
                for(int j = 1; j <= n; j++){
                    if(floyd[i][j] != 0) cnt++;
                }
                if(cnt == n-1) answer++; // n-1명과 승패가 확정되면 순위 확정
            }
            return answer;
        }
}
