package programmers.lv2;

public class PG_12900 {
    public int codeOfMine(int n) {
        int[] memo = new int[60001];
        memo[1] = 1;
        memo[2] = 2;
        // 더 이상 분리될 수 없는 단위 1)세로 1개 2)가로 2개
        // [n] = [n-1에 세로 1개 붙이기] + [n-2에 가로 2개 붙이기]
        for(int i=3; i<memo.length-3; i++) {
            memo[i] = (memo[i - 2] + memo[i - 1]) % 1000000007;
        }
        return memo[n];
    }

    // codeOfMine과 동일한 원리
    public int tiling(int n) {
        int a = 1;
        int b = 1;
        for (int i = 0; i < n - 1; i++) {
            int fib = (a + b) % 100000;
            a = b; // 한 칸 뒤로
            b = fib; // 한 칸 뒤로
        }
        return b;
    }

    public static void main(String[] args){
        PG_12900 pg_12900 = new PG_12900();
        System.out.println(pg_12900.codeOfMine(4)); // expected : 5
    }
}
