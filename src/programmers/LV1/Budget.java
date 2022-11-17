package programmers.LV1;

import java.util.Arrays;

public class Budget {
    public int solution(int[] d, int budget) {
        int i = 0;
        Arrays.sort(d);
            for (i = 0; i < d.length; i++) {
                if(budget-d[i] < 0) break;

                budget -= d[i];
            }
        return i;
    }

    public static void main(String[] args){
        Budget budget = new Budget();
        int[] arr1 = {1, 3, 2, 5, 4};
        int[] arr2 = {2, 2, 3, 3};
        System.out.println(budget.solution(arr1,9)); // expected : 3
        System.out.println(budget.solution(arr2,10)); // expected : 4
    }

}
