package programmers.lv1;

public class ReverseTernary {
    public int solution(int n) {
        int answer = 0;
        StringBuffer sb = new StringBuffer(); // use StringBuffer, to improve speed

        // 10진수 -> 3진수
        while(n >= 1){
            sb.append(n%3);
            n/=3;
        }

        // 뒤집기
        sb.reverse();

        // 3진수 -> 10진수
        String[] arr = sb.toString().split("");
        for(int i=0; i<arr.length; i++){
            answer+= (Integer.parseInt(arr[i]) * (int) Math.pow(3,i));
        }

        return answer;
    }

    public int refactor(int n) {
        int answer = 0;
        String ternary = "";
        // 10진수 -> 3진수
        while(n >= 1){
            ternary = ternary + (n%3); // 기존 ternary를 앞에 더하면 뒤집어진 상태로 더해짐
            n/=3;
        }

        // 3진수 -> 10진수
        return Integer.parseInt(ternary,3);
    }

    public int exam1(int n) {
        int answer = 0;
        // 10진수 -> 3진수
        String third = Integer.toString(n, 3);
        //System.out.println(third);
        StringBuffer sb = new StringBuffer(third);
        String reversed = sb.reverse().toString();

        // 3진수 -> 10진수
        int exp = 0;
        for (int i = reversed.length() - 1; i >= 0; i--) {
            answer += Integer.parseInt(String.valueOf(reversed.charAt(i))) * Math.pow(3, exp);
            exp++;
        }

        return answer;
    }

    /* reverse 인터페이스 따로 사용 X, (char -> int) - '0' */
    public int exam2(int n) {
        int answer = 0;
        String str = "";

        while(n>0) {
            int r = n%3;
            n = n / 3;
            str = r + str;
        }

        for(int i=0; i<str.length(); i++) {
            answer += Math.pow(3,i) * (str.charAt(i) - '0');
        }

        return answer;
    }

    public static void main(String[] args){
        ReverseTernary reverseTernary = new ReverseTernary();
        System.out.println(reverseTernary.refactor(45)); // 45 -> 1200 -> 0021 -> 7
        System.out.println(reverseTernary.refactor(125)); // 45 -> 11122 -> 22111 -> 229
    }
}
