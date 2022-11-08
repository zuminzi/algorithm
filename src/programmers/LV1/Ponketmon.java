package programmers.LV1;

import java.util.HashSet;
import java.util.Set;

public class Ponketmon {
    public int solution(int[] nums) {
        int cnt = 0;
        for(int i=0; i < nums.length; i++){
            for(int j=0; j < i; j++){
                if(nums[i] == nums[j]) {
                    cnt++;
                    //System.out.println("i="+i);
                    //System.out.println("j="+j);
                    //System.out.println("cnt="+cnt);
                    break; // 중복이 하나라도 있으면 cnt++이므로 break 필요 -> break 없으면 한 요소당 중복만큼 ++
                }
            }
        }

        //System.out.println(nums.length/2);
        //System.out.println(nums.length-cnt);

        int answer = Math.min(nums.length/2, nums.length-cnt);
        return answer;

    }

    public int exam1(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int n : nums)   set.add(n);

        return Math.min(nums.length / 2, set.size());
    }

    public static void main(String[] args) {
        Ponketmon ponketmon = new Ponketmon();
        int[] arr1 = {3,1,2,3};
        int[] arr2 = {3,3,3,2,2,4};
        int[] arr3 = {3,3,3,2,2,2};
        System.out.println(ponketmon.exam1(arr1)); // expected: 2
        System.out.println(ponketmon.exam1(arr2)); // expected: 3
        System.out.println(ponketmon.exam1(arr3)); // expected: 2
    }
}
