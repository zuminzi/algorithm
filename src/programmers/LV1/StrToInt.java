package programmers.LV1;

public class StrToInt {
    public int codeOfMine(String s) {
        return Integer.parseInt(s);
    }

    public int exam(String str) {
        boolean Sign = true;
        int result = 0;

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '-')
                Sign = false;
            else if (ch != '+')
                result = result * 10 + (ch - '0'); // 0 + 1 // 10 + 2 // 120 + 3 // 1230 + 4 // ch - '0'은 char->int
            /* ch -'0' 원리
            charAt으로 반환받은 '1'은 int형이 아닌 char형, 즉 int로 변환시키면 아스키코드값인 49로 변환

            '0'(char) -> 48
            '1'(char) -> 49
            '2'(char) -> 50
            '3'(char) -> 51
            '4'(char) -> 52

            따라서 -'0'(48)을 해주면

            48 - 48('0') = 0
            49 - 48('0') = 1
            50 - 48('0') = 2
            51 - 48('0') = 3
            52 - 48('0') = 4
             */
        }
        return Sign ? 1 : -1 * result;
    }

    public static void main(String args[]) {
        StrToInt strToInt = new StrToInt();
        System.out.println(strToInt.exam("-1234"));
    }
}
