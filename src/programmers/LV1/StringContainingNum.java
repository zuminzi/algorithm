package programmers.LV1;

public class StringContainingNum {
    /** 숫자 포함하는지 판별하는 정규식

    1. \\d{자릿수}
    2. [0-9]{자릿수}

    System.out.print(s.matches("\\d")); // false // error
    System.out.print(s.matches("\\d{4}")); // true

     */
    public boolean codeOfMine(String s) {
        return (s.length()== 4 || s.length()== 6)
                && (s.matches("[0-9]{4}") || s.matches("[0-9]{6}"));
    }

    public boolean refactor(String s) {
        // 여기서 이미 길이 검사도 하고 있으므로 길이 검사 조건문 별개로 넣을 필요 X
        return (s.matches("[0-9]{4}") || s.matches("[0-9]{6}"));
    }

    public boolean exam1(String s) {
        return (s.length() != 4 && s.length() != 6) || (s.split("[0-9]").length > 0) ? false:true;
    }

    /* 속도, 메모리 측면에서 refactor 코드보다 뛰어나진 않지만 아이디어 참고 */
    public boolean exam2(String s) {
        int length = s.length();
        if (length != 4 && length != 6)
            return false;
        for (int i = 0; i < length; ++i) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') // 0 미만, 9 초과
                return false;

        }
        return true;
    }

    public static void main(String[] args){
        StringContainingNum stringContainingNum = new StringContainingNum();
        System.out.println(stringContainingNum.refactor("a234"));// expected : false
        System.out.println(stringContainingNum.refactor("1234"));// expected : true

    }
}
