package programmers.etc;

import java.util.Arrays;

public class PG_389481 {
    // 일반적인 n진법과 다르게 문제는 1-based라는 점 주의
    public String solution(long n, String[] bans) {
        // 1. 26진수 문자열 → 1-based 10진수로 변환
        long[] orders = new long[bans.length];
        for (int i = 0; i < bans.length; i++) {
            orders[i] = to10BasedNumber(bans[i]);
        }

        // 2. n번째 안전한 문자열 계산
        // 2-1. 정렬
        Arrays.sort(orders);
        for (long banned : orders) {
            // 2-2. 금지어 비교하여 n 보정
            if (banned <= n) n++;
            else break;
        }

        // 3. 1-based 숫자 → 알파벳 문자열
        return toAlphabet(n);
    }

    private long to10BasedNumber(String alphabet) {
        long value = 0;
        for (int i = 0; i < alphabet.length(); i++) {
            int num = alphabet.charAt(i) - 'a' + 1; // 'a' = 1
            value = value * 26 + num;
        }
        return value;
    }

    private String toAlphabet(long num) {
        if (num == 0) return "a";
        StringBuilder sb = new StringBuilder();
        while (num > 0) { // 루프 1회 = 한 자리 계산
            num--; // 1-based → 0-based 변환
            long mod = num % 26;
            sb.append((char) ('a' + mod));
            num /= 26;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        PG_389481 pg = new PG_389481();
        // expected: "ah"
        System.out.println(pg.solution(
                30,
                new String[]{"d", "e", "bb", "aa", "ae"}
        ));
    }
}
