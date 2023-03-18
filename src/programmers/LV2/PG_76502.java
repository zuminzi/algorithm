package programmers.LV2;

import java.util.Stack;

public class PG_76502 {
    // ~67.85ms, 88.6MB
    public int codeOfMine(String s) {
        int answer = 0;
        Stack<Character> stack = new Stack<>();
        for(int i=0; i<s.length(); i++) {
            // clear 안해주면 이전 요소 반영
            stack.clear();
            int cnt = 0;

            while (cnt < s.length()) {
                if((s.charAt(i) == ']' || s.charAt(i) == ')' || s.charAt(i) == '}')) {
                    if (cnt == 0) break;
                    else {
                        if (!stack.isEmpty()) {
                            if ((stack.peek() == '(' && s.charAt(i) == ')') ||
                                    (stack.peek() == '[' && s.charAt(i) == ']') ||
                                    (stack.peek() == '{' && s.charAt(i) == '}')) {
                                stack.pop();
                            // 시간 단축 Point
                            } else break;
                        }
                    }
                } else {
                    stack.push(s.charAt(i));
                }

                if (cnt == s.length() - 1) {
                    if (stack.isEmpty()) answer++;
                }

                if(i == s.length() -1){
                    i = 0;
                } else {
                    i++;
                }

                cnt++;
            }
        }
        return answer;
    }

    // ~29.31ms, 73MB
    private final Stack<Character> stack = new Stack<>();
    public int exam(String s) {
        int answer = 0;
        StringBuilder stringBuilder = new StringBuilder(s);

        for (int i = 0; i < s.length(); i++) {
            // codeOfMine처럼 매번 전체 순서를 넣어서 검사하지 않고
            // 제일 마지막에 첫번째 요소를 붙이고
            stringBuilder.append(stringBuilder.charAt(0));
            // 기존 첫번째 요소는 제거하는 식으로 검사
            // -> 시간단축 Point **
            stringBuilder.deleteCharAt(0);
            if (correctParenthesis(stringBuilder.toString().toCharArray()))
                answer++;
        }
        return answer;
    }

    private boolean correctParenthesis(char[] s) {
        for (char c : s) {
            if (!(check(c, '(', ')') && check(c, '[', ']') && check(c, '{', '}')))
                return false;
        }
        // if (stack.isEmpty()) answer ++;
        return stack.isEmpty();
    }

    private boolean check(char c, char a, char b) {
        if (c == a)
            stack.push(a);
        else if (c == b)
            // 끝까지 가지 않고 짝이 맞지 않으면 바로 return false
            // -> 시간 단축 Point
            if (!stack.isEmpty() && stack.peek() == a) stack.pop(); else return false;
        return true;
    }

    public static void main(String[] args){
        PG_76502 pg_76502 = new PG_76502();
        System.out.println(pg_76502.exam("[](){}")); // expected : 3
        System.out.println(pg_76502.exam("}]()[{")); // expected : 2
        System.out.println(pg_76502.exam("[)(]")); // expected : 0
        System.out.println(pg_76502.exam("}}}")); // expected : 0
    }
}
