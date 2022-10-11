package programmers.LV1;

import java.util.Arrays;

public class GetMean {
    public double codeOfMine(int arr[]) {
        /* TODO
         * (arr==null)일 때 처리
         * 변수 초기화
         * TEST CODE
         */

        double total =0;

        for(int temp : arr){
            total +=temp;
        }

        double result = total/arr.length;

        return result;
    }

    public double exam(int arr[]) {
        return (int) Arrays.stream(arr).average().orElse(0);
    }

    public static void main(String[] args) {
        int x[] = {-1, 0, 1};
        GetMean getMean = new GetMean();
        System.out.println("평균값 : " + getMean.codeOfMine(x));
    }
}
