package programmers.lv1;


import java.util.Arrays;

public class Multiplication {

    public String codeOfMine(int x, int n) {
        /* ** todo
            * 주어진 경계값 Test
             (long이어야 하는 이유:x=10000000, n=1000일 때 int 범위 벗어남)
             * 업캐스팅도 명시적해주기 ex.(UpcastingType) or UpcastingType.valueOf()
         */
        long[] arr = new long[n];

        if(x!=0 && n!=0){
            for(int i=0;i<n;i++) {
                arr[i] = (long) x * (i + 1); // 처음 테스트케이스 13,14 통과 못했던 이유: int->long형변환 명시 안해서
            }
        }
        return Arrays.toString(arr);
        //return arr;
    }

    public static String exam(int x, int n) {
        long[] answer = new long[n];
        answer[0] = x;

        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] + x;
        }
        return Arrays.toString(answer);
        //return answer;
    }

    public static void main(String[] args) {
        Multiplication multiplication = new Multiplication();
        System.out.println(multiplication.exam(10000000,1000));
    }
}
