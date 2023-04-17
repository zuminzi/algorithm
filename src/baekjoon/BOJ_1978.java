package baekjoon;

import java.util.Scanner;

public class BOJ_1978 {

    public static void main(String[] args){
        Scanner sc= new Scanner(System.in);
        int length = sc.nextInt();
        int isPrimeCnt = 0;

        for(int i=0; i< length; i++){
            if(isPrime(sc.nextInt()) == true){
                isPrimeCnt++;
            }
        }
        System.out.println(isPrimeCnt);
    }

    private static boolean isPrime(int n) {
        // 1을 소수로 인정하면 소인수 분해의 유일성이 사라지기 때문에 소수로 인정하지 않음
        if (n == 1) {
            return false;
        } else if (n == 2){
            return true;
        } else if (n % 2 == 0) {
            return false;
        } else {
            for (int i = 3; i <= Math.sqrt(n); i += 2) {
                if (n % i == 0) {return false;}
            }
            return true;
        }
    }
}
