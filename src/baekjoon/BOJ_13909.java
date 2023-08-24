package baekjoon;

import java.util.Scanner;

public class BOJ_13909 {
    // 제곱 수의 약수의 개수
    // 약수의 개수가 홀수 개일 때 창문이 열린다.
    // 정수의 약수의 개수가 홀수인 경우는 바로 그 정수가 제곱수일 때이다.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int sqrtN = (int) Math.sqrt(N); // N 이하의 제곱수 중 가장 큰 수의 제곱근 // 즉, N 이하의 제곱수 개수
        System.out.println(sqrtN);
    }

    // time out
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int N = sc.nextInt();
//        int openWindowCnt = 0;
//        for (int k = 1; k <= N; k++) {
//            int divisorCnt = 0;
//            // 각 숫자의 약수의 개수 구하기
//            for (int i = 1; i <= Math.sqrt(k); i++) {
//                if (k % i == 0) {
//                    if (k / i == i) divisorCnt++; // k의 제곱근인 i는 약수 1개
//                    else divisorCnt += 2; // k / i가 몫, i가 나머지인 두 약수
//                }
//            }
//            // divisorCnt가 odd num일 때 open
//            if (divisorCnt % 2 == 1) {
//                openWindowCnt++;
//            }
//        }
//        System.out.println(openWindowCnt);
//    }
}
