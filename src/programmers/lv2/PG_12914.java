package programmers.lv2;

public class PG_12914 {
    /* DP(Dynamic Programming) 성립조건
    1.최적 부분 구조(Optimal Substructure)
     - 상위 문제를 하위 문제로 나눌 수 있으며 하위 문제의 답을 모아서 상위 문제를 해결할 수 있다.
    2. 중복되는 부분 문제(Overlapping Subproblem)
     - 동일한 작은 문제를 반복적으로 해결해야 한다.
    -> 즉 점화식을 수립하여 Tabulation(테이블-상향식)이나 Memoization(메모-하향식)으로 해결하기
     */
    public long exam(int n) {
        long[] memo = new long[2001];
        memo[1] = 1;
        memo[2] = 2;

        for (int i = 3; i <= n; i++) {
            // (n칸에 도달 할 수 있는 경우의 수)
            // = (n-1칸에 도달할 수 있는 경우의 수) + (n-2칸에 도달할 수 있는 경우의 수)
            // = (n번째에 1칸 뛰기) + (n번째에 2칸 뛰기)
            memo[i] = (memo[i - 1] + memo[i - 2]) % 1234567;
        }

        return memo[n];
    }

    // 재귀
    public long fail_sol(long n) {
        long answer = 0;
        long multiplier = n/2;
        for(long i=0; i<=multiplier; i++){
            if(i==0) {
                answer++;
                continue;
            } else if(i == multiplier && n%2 == 0) {
                answer++;
                continue;
            }
            else {
                double divided = factorial(n-(2*i)+i)%1234567;
                double firstDivisor = factorial(i);
                double secondDivisor = factorial(n-(2*i));
                answer += (divided/firstDivisor*secondDivisor);
            }
        }
        return answer;
    }

    private double factorial(long num){
        if(num==1) return 1;
        return factorial(num-1)*num;
    }

    public static void main(String[] args){
        PG_12914 pg_12914 = new PG_12914();
        System.out.println(pg_12914.exam(4)); // expected : 5
        System.out.println(pg_12914.exam(3)); // expected : 3
        System.out.println(pg_12914.exam(2000)); // expected : 3
    }
}
