package programmers.LV1;

public class HidePrivacy {
    public String codeOMine(String phone_number) {
        /**remind
         * String.subString(begin_index, end_index)은 *end_index 이전* 문자열까지 잘라서 리턴
         * String.replaceAll(regex, replacement)에서 regex 사용시 ""로 감싸주기
         */
        String lastFour = phone_number.substring(phone_number.length()-4,phone_number.length()-0);
        String previousNums = phone_number.substring(0,phone_number.length()-4);
        String replacePreviousNums =previousNums.replaceAll("[0-9]","*");

        return replacePreviousNums + lastFour;
    }

    /* toCharArray() */
    public String exam1(String phone_number) {
        char[] ch = phone_number.toCharArray(); // String -> charArray
        for(int i = 0; i < ch.length - 4; i ++){
            ch[i] = '*';
        }
        return String.valueOf(ch);
    }

    /* repeat() */
    public String exam2(String phone_number) {
        String prev = phone_number.substring(phone_number.length() - 4);
        String star = "*";

        return star.repeat(phone_number.length() - 4) + prev;
    }

    /* for문 +=로 '*' 붙이기 */
    public String exam3(String phone_number) {
        String answer = "";
        for (int i = 0; i < phone_number.length() - 4; i++)
            answer += "*";
        answer += phone_number.substring(phone_number.length() - 4);
        return answer;
    }


    public String exam4(String phone_number) {
        String answer = "";
        int len = phone_number.length();
        for(int i = 0; i < len; i++){
            if(i < len - 4) // 마지막 4자리 숫자와 아닌 숫자 if-else문
                answer += "*";
            else
                answer += phone_number.charAt(i);
        }
        return answer;
    }

    public static void main(String[] args) {
        HidePrivacy hidePrivacy = new HidePrivacy();
        System.out.println(hidePrivacy.codeOMine("01033334444")); // expected: "*******4444"
        System.out.println(hidePrivacy.codeOMine("027778888")); // expected: "*****8888"
    }
}
