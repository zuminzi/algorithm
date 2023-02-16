package programmers.LV2;

import java.util.Stack;

public class PG_12973 {
    // char target 지역변수 생성했을 시
    // Accuracy Test ~29.03ms, 80.5MB
    // Efficiency Test ~63.01ms, 61MB

    // char target 지역변수 생성 안했을 시
    // Accuracy Test ~20.39ms, 92.7MB
    // Efficiency Test ~62.18ms, 61.2MB
    public int codeOfMine(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i=0; i<s.length(); i++){
            char target = s.charAt(i);
            if(stack.isEmpty() == false && stack.peek() == target){
                stack.pop();
            } else {
                stack.push(target);
            }
        }
        return stack.isEmpty()? 1:0;
    }

    // char(2byte) 대신 byte(1byte) 사용
    // Accuracy Test ~13.88ms, 86.4MB
    // Efficiency Test ~58.69ms, 69.8MB
    public int exam(String s) {
        // String -> byte sequence
        byte[] bytes = s.getBytes();
        int length = bytes.length;

        Stack<Integer> stack = new Stack<>();

        int iLeft = 0, iRight = iLeft + 1; // ileft = Left Pointer, iRight = Right Pointer
        for (; iLeft < length && iRight < length; ) {
            if (bytes[iLeft] == bytes[iRight]) {
                if (stack.empty()) {
                    iLeft = iRight + 1;
                    iRight = iLeft + 1;
                } else {
                    iLeft = stack.pop();
                    iRight++;
                }
            } else {
                stack.push(iLeft);

                iLeft = iRight;
                iRight = iLeft + 1;
            }
        }
        return iLeft >= length && iRight >= length ? 1 : 0;
    }

    public static void main(String[] args){
        PG_12973 pg_12973 = new PG_12973();
        System.out.println(pg_12973.exam("baabaa")); // expected : 1 // byte[] -> {98,97,97,98,97,97}
        System.out.println(pg_12973.exam("cdcd")); // expected : 0
    }
}
