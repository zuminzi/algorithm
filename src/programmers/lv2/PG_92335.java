package programmers.lv2;

public class PG_92335 {
    // Integer.parseInt(n, radix) : radix -> 10진수
    // Integer.toString(n, radix) : 10진수 -> radix
    public int solution(int n, int k) {
        int answer = 0;
        String radixNum = Integer.toString(n,k); // n을 10진수에서 k진수로 변환
        String[] arr = radixNum.split("[0]+");
        for(String el : arr){
            if(isPrime(el)){
                answer++;
            }
        }
        return answer;
    }

    private boolean isPrime(String el){
        long target = Long.parseLong(el); // int type은 RunTimeError
        if(target == 1) {
            return false;
        } else if (target == 2) {
            return true;
        } else if(target % 2 == 0) {
            return false;
        } else {
            for (int i = 3; i <= Math.sqrt(target); i+=2) {
                if (target % i == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args){
        PG_92335 pg_92335 = new PG_92335();
        System.out.println(pg_92335.solution(437674,3)); // expected : 3
        System.out.println(pg_92335.solution(110011,10)); // expected : 2
    }
}
