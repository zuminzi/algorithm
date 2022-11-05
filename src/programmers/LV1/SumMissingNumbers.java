package programmers.LV1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SumMissingNumbers {
    /*
    approach : 단순 차집합으로 접근하기 보다는 sum에서 차감하는 방식으로
     */
    public int codeOfMine(int[] numbers) {
        int answer = 0;

        // Array to List
        List<Integer> numberList = new ArrayList<>();
        for(int i=0; i < numbers.length; i++){
          numberList.add(numbers[i]);
        }

        List<Integer> allNumbers = new ArrayList<>();
        for(int i=0; i < 10; i++){
            allNumbers.add(i);
        }

        allNumbers.removeAll(numberList);

        for(int i=0; i< allNumbers.size(); i++){
            answer += allNumbers.get(i);
        }


        return answer;
    }

    public int exam1(int[] numbers) {
        int sum = 45;
        for (int i : numbers) {
            sum -= i;
        }
        return sum;
    }

    public int exam2(int[] numbers) {
        return 45- Arrays.stream(numbers).sum();
    }

    public int exam3(int[] numbers) {
        int answer = 0;
        int[] sum = {0,1,2,3,4,5,6,7,8,9};

        for(int i = 0; i< sum.length; i++){
            for(int j=0; j< numbers.length; j++){
                if(sum[i] == numbers[j]){
                    sum[i] -= numbers[j];
                }
            }
            answer += sum[i];
        }
        return answer;
    }

    public static void main(String[] args) {
        SumMissingNumbers sumMissingNumbers = new SumMissingNumbers();
        int[] arr1 = {1,2,3,4,6,7,8,0};
        int[] arr2 = {5,8,4,0,6,7,9};
        System.out.println(sumMissingNumbers.exam1(arr1)); // expected:14
        System.out.println(sumMissingNumbers.exam1(arr2)); // expected:6
    }
}
