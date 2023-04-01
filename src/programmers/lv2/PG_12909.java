package programmers.lv2;

import java.util.Stack;

public class PG_12909 {
    // Accuracy Test ~0.35ms, ~72.9MB
    // Efficiency Test ~16.99ms, ~53.5MB
    boolean codeOfMine(String s) {
        Stack stack = new Stack<>();
        for(int i=0; i< s.length(); i++){
            char target = s.charAt(i);

            if(target == '('){stack.push(target);}
            else {
                // Problem Point 1
                // if(i == 0) (X) // 중간에 짝 없는 ')'이 먼저 들어갈 때 EmptyStackException 발생
                if(stack.isEmpty()){
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    // No Stack
    // Accuracy Test ~0.05ms, ~75.7MB
    // Efficiency Test ~8.31ms, ~52.6MB
    boolean exam(String s) {
        boolean answer = false;
        int count = 0;
        for(int i = 0; i<s.length();i++){
            if(s.charAt(i) == '('){
                count++;
            }
            if(s.charAt(i) == ')'){
                count--;
            }
            // 짝 없는 ')'이 먼저 들어갈 때 break -> return false
            if(count < 0){
                break;
            }
        }
        if(count == 0){
            answer = true;
        }

        return answer;
    }

    public static void main(String[] args){
        PG_12909 pg_12909 = new PG_12909();
        System.out.println(pg_12909.exam("()())(()")); // expected : false //
        System.out.println(pg_12909.exam("()()")); // expected : true
        System.out.println(pg_12909.exam("(())()")); // expected : true
        System.out.println(pg_12909.exam(")()(")); // expected : false
        System.out.println(pg_12909.exam("(()(")); // expected : false
    }
}
