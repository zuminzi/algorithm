package programmers.LV1;

import java.util.stream.IntStream;

public class DotProduct {
    public int codeOfMine(int[] a, int[] b) {
        int answer = 0;
        for(int i=0; i<a.length; i++){
            answer += a[i]*b[i];
        }
        return answer;
    }
    public int exam1(int[] a, int[] b) {
        return IntStream.range(0, a.length).map(index -> a[index] * b[index]).sum();
    }

    public static void main(String[] args){
        DotProduct dotProduct = new DotProduct();
        int[] a = {1,2,3,4};
        int[] b = {-3,-1,0,2};
        System.out.println(dotProduct.codeOfMine(a,b)); // expected : 3
    }
}
