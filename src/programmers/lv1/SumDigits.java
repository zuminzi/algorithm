package programmers.lv1;

import java.util.Arrays;
import java.util.stream.Stream;

public class SumDigits {
    public int codeOfMine(int n) {
        int total=0;

        String num = String.valueOf(n);
        String[] num_array = num.split("");

        for(int i = 0; i < num_array.length; i++) {
            total += Integer.parseInt(num_array[i]);
        }

        return total;
    }

    public int exam (int n){
        int total=0;
        while(n > 0) {
            total+=(n %10);

            n /= 10; // 증감식
        }
        return total;
    }

    /* stream 이용하기 */
    public int exam_ (int n) {
        int[] arrNum = Stream.of(String.valueOf(n).split("")).mapToInt(Integer::parseInt).toArray();
        int total = Arrays.stream(arrNum).sum();

        return total;
    }

    public static void main(String[] args) {
        int x = 123;
        SumDigits sumDigits = new SumDigits();
        System.out.println("자릿수 합 : " + sumDigits.exam_(x));
    }
}
