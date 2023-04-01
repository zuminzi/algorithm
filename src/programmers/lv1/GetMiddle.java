package programmers.lv1;

public class GetMiddle {
    public String codeOfMine(String s) {
        String answer = "";

        if(s.length()%2==0){
            answer = String.valueOf(s.charAt(s.length() /2 - 1)) + String.valueOf(s.charAt(s.length() /2));
        } else {
            answer = String.valueOf(s.charAt(((int)s.length()) /2));
        }
        return answer;
    }

    // subString(a,b) -> a이상 b미만
    String exam1(String word){
        return word.substring((word.length()-1) / 2, word.length()/2 + 1); // 홀수 길이는 b가 소수점 붙어서 포함 안된다는 점을 이용
    }

    public String exam2(String s) {
        int ans = s.length();
        if (ans % 2 == 1){
            return Character.toString(s.charAt(ans/2));
        }
        else{
            return s.substring(ans/2-1, ans/2+1);
        }
    }

    public static void main(String[] args) {
        GetMiddle getMiddle = new GetMiddle();
        System.out.println(getMiddle.exam1("abcde")); // expected: "c"
        System.out.println(getMiddle.exam1("qwer")); // expected: "we"

    }
}
