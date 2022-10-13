package programmers.LV1;

public class Collatz {

    public int codeOfMine(long num) {
        /* todo
        * 실행한 테스트값이 결과값과 다를 때 오버플로우도 고려해보기
            (테스트 인수 626331의 경우 loop문 500번 반복했다는 것을 알 수 있었는데도 int형 범위 벗어났을 거라고 유추 못함)
         */
        int cnt=0;

        while(num!=1&&cnt<500){
            if(num%2==0){
                num=num/2;
                cnt++;
            } else {
                num=num*3+1;
                cnt++;
            }
        }
        return cnt<500? cnt:-1;
    }

    public int exam(int num) {
        long n = (long)num;
        for(int i =0; i<500; i++){
            if(n==1) return i;
            n = (n%2==0) ? n/2 : n*3+1;
        }
        return -1;
    }

    public static void main(String[] args) {
        Collatz collatz = new Collatz();
        System.out.println(collatz.exam(1)); // expected: 0
        System.out.println(collatz.exam(6)); // expected: 8
        System.out.println(collatz.exam(16)); // expected: 4
        System.out.println(collatz.exam(626331)); // expected: -1
    }
}
