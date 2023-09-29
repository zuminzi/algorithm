package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1541 {
    /**
     * -와 - 사이를 괄호쳐 최소값을 찾는 알고리즘
     * Key Point : split()으로 자르면 구분자는 포함되지 않는다.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = br.readLine().split("-");
        int ans = 0;
        for(int i=0; i<nums.length; i++){
            int result = 0;
            String[] subNums = nums[i].split("\\+");
            for (String subNum : subNums) {
                result += Integer.parseInt(subNum);
            }


            if (i == 0) {
                ans += result;
            } else {
                ans -= result;
            }
        }
        System.out.println(ans);
    }
}
