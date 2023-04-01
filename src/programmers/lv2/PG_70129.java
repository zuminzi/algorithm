package programmers.lv2;

import java.util.Arrays;

public class PG_70129 {
    // Accuracy Test ~7.05ms, 81.3MB
    public String solution(String s) {
        int[] answer = new int[2];
        int toBinaryCnt = 0;
        int removeZeroCnt = 0;
        // Problem Point 1
        // while 조건문 -> contains("0") && (X), || (O)
        // 그러나 최종적으로 contains("0")을 빼는 것이 수행시간 down
        while(!s.equals("1")) {
            // break문 조건절에서는 오히려 !contains("0")을 빼면 수행시간 up
            if(!s.contains("0") && s.equals("1")) break;

            int beforeLength = s.length();
            s = s.replace("0", "");
            int afterLength = s.length();
            removeZeroCnt += beforeLength - afterLength;

            s = Integer.toBinaryString(afterLength);
            toBinaryCnt++;
        }
        answer[0] = toBinaryCnt;
        answer[1] = removeZeroCnt;
        return Arrays.toString(answer);
    }

    public static void main(String[] args){
        PG_70129 pg_70129 = new PG_70129();
        System.out.println(pg_70129.solution("110010101001")); // expected : [3,8]
        System.out.println(pg_70129.solution("01110")); // expected : [3,3]
        System.out.println(pg_70129.solution("1111111")); // expected : [4,1]
    }
}
