package programmers.lv3;

import java.util.*;

public class PG_42895 {
        public int solution(int N, int number) {
            List<Set<Integer>> dp = new ArrayList<>();
            for(int i=0; i<=8; i++){
                dp.add(new HashSet<>());
            }

            for(int i=1; i<=8; i++){
                // j는 dp[j]와 dp[i-j]를 조합하기 위한 변수 (예: dp[4] = dp[1]과 dp[3]의 조합, dp[2]와 dp[2]의 조합, dp[3]과 dp[1]의 조합(중복이므로 생략))
                for (int j=1; j<=i/2; j++){
                    union(dp.get(i), dp.get(i - j), dp.get(j));
                    union(dp.get(i), dp.get(j), dp.get(i - j));
                }
                dp.get(i).add(Integer.parseInt(String.valueOf(N).repeat(i)));

                if(dp.get(i).contains(number)) return i;
            }

            return -1;
        }
        // 이전 dp 값들을 사칙연산으로 조합
        private void union(Set<Integer> union, Set<Integer> set1, Set<Integer> set2) {
            for (int n1 : set1) {
                for (int n2 : set2) {
                    union.add(n1 + n2);
                    union.add(n1 - n2);
                    union.add(n1 * n2);
                    if (n2 != 0) union.add(n1 / n2);
                }
            }
        }
}
