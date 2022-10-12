package programmers.LV1;

import java.util.Arrays;

public class CharCount {
    boolean codeOfMine(String s) {
        String[] str_arr = s.split("");

        int p_count = (int) Arrays.asList(str_arr).stream().filter(p -> p.equals("p") || p.equals("P")).count();
        int y_count = (int) Arrays.asList(str_arr).stream().filter(p -> p.equals("y") || p.equals("Y")).count();

        if (y_count == p_count || (p_count ==0 && y_count == 0)) {
            return true;
        }

        return false;
    }

    boolean exam1(String s) {
        s = s.toUpperCase(); // 주어진 인수 대문자로 통일
        return s.chars().filter( e -> 'P'== e).count() == s.chars().filter( e -> 'Y'== e).count(); // return문 자체를 조건문으로
    }

/* count 변수 하나만으로 p와 y의 개수 비교 */
    boolean exam2(String s) {
        s = s.toLowerCase();
        int count = 0;

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == 'p')
                count++;
            else if (s.charAt(i) == 'y')
                count--;
        }

        return count ==0; // return문 자체를 조건문으로 // 0이면 ture, 0아니면 false
    }

    /* replaceAll(String 정규식표현식, String replacements) 사용 */
    // cf. replace(CharSequence target, CharSequence replacement)
    // replace는 문자열만 변환가능하지만 replaceAll은 정규식을 사용하여 불특정 문자열 변환가능
    boolean exam3(String s) {
        return s.replaceAll("[^yY]", "").length() - s.replaceAll("[^pP]", "").length() == 0 ? true : false;
    }

    public static void main(String[] args) {
        CharCount charCount = new CharCount();
        System.out.println(charCount.exam3("pPoooyY")); // expected: true
        System.out.println(charCount.exam3("Pyy")); // expected: false

    }
}
