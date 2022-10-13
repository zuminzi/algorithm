package programmers.LV1;

import java.util.ArrayList;
import java.util.Collections;

public class MinOfModOne {
    public int solution(int n) {
        ArrayList<Integer> arr = new ArrayList<>();

        for (int x = 1; x <= n; x++) {
            if (n % x == 1)
                arr.add(x);
        }
        return Collections.min(arr);
    }



    public int exam1(int n) {
        int answer = 0;

        for (int i=2; i<n; i++) { // 나머지 1인 것을 찾으므로 i=2; i<n;
            if (n % i == 1) {
                answer = i;
                break; // 따로 min 뽑지 않고도 작은 수부터 순차적으로 loop문에 돌려지므로 찾으면 break;
            }
        }
        return answer;
    }

    public int exam2(int n) {
        int answer = 1;

        while(true) {
            if (n%answer==1)
                break;
            answer++;
        }
        return answer;
    }

    public static void main(String[] args) {
        MinOfModOne minOfModOne = new MinOfModOne();
        System.out.println(minOfModOne.exam2(10)); // expected: 3
        System.out.println(minOfModOne.exam2(12)); // expected: 11

    }
}
