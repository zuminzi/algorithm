package programmers.LV1;

public class GcdAndLcm {
    /** Euclidean algorithm(유클리드 호제법/유클리드 알고리즘) -> O(logn)

        2개의 자연수 a, b(a > b)에 대해서 a를 b로 나눈 나머지가 r일 때, a와 b의 최대공약수는 b와 r의 최대공약수와 같다
     */
    public int[] solution(int n, int m) {
        int[] answer = new int[2];
        answer[0] = gcd(n,m);
        answer[1] = lcm(n,m);

        return answer;
    }
    private int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    private int gcd(int a, int b) {
        int remainder = 1;
        while (b != 0) {
            remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    /* O(n) */
    public int[] exam(int n, int m) {
        int[] answer = new int[2];
        for (int i = 1; i < n + m; i++) {
            if (n % i == 0 && m % i == 0) {
                answer[0] = i;
                answer[1] = n * m / answer[0];
            }
        }
        return answer;
    }

    public static void main(String[] args){
        GcdAndLcm gcdAndLcm = new GcdAndLcm();
        System.out.println(gcdAndLcm.solution(3,12)); // expected : [3, 12]
    }
}
