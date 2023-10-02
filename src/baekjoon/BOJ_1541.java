package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1541 {
    /**
     * '-'와 '-' 사이를 괄호쳐서 최솟값 찾는 알고리즘
     * Key Point : split()으로 자르면 구분자는 포함되지 않는다.
     * 구분자로 문자열을 잘라주면서 구분자도 결과에 추가하려면 StringTokenizer 클래스를 사용하면 된다.
     * StringTokenizer st = new StringTokenizer(br.readLine(), "-", true); // input, delim, returnDelims
     * while (st.hasMoreTokens()) {String token = st.nextToken();}
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
