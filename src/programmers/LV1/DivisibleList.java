package programmers.LV1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DivisibleList {

    public List<Integer> codeOfMine(int[] arr, int divisor) {
        List<Integer> answer = new ArrayList<>();
        for(int temp : arr){
            if (temp % divisor == 0) answer.add(temp);
        }
        if(answer.size()==0) answer.add(-1);

        Collections.sort(answer);

        return answer;
    }

    public int[] exam(int[] arr, int divisor) {
        int count = 0;

        int[] result = Arrays.stream(arr).filter(x -> x % divisor == 0).toArray();
        Arrays.sort(result);

        if (result.length == 0) {
            int[] nothing = {-1};
            return nothing;
        }
        return result;
    }

    public static void main(String[] args) {
        DivisibleList divisibleList = new DivisibleList();
        int[] array = {5, 9, 7, 10};
        System.out.println(divisibleList.codeOfMine(array, 5) ); // expected: [5,10]
        //System.out.println( Arrays.toString( div.divisible(array, 5) ));
    }
}
