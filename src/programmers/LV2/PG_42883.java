package programmers.LV2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PG_42883 {
    // Stack, Queue
    // ~92.70ms, 105MB
    public String codeOfMine(String number, int k) {
        Queue<Integer> waitingQ = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();
        for(int i=0; i<number.length(); i++){
            waitingQ.add(number.charAt(i) - '0');
        }

        while(!waitingQ.isEmpty()){
            while (!stack.isEmpty() && stack.peek() < waitingQ.peek() && k > 0){
                stack.pop();
                k--;
            }
            stack.add(waitingQ.poll());
        }

        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        // k 제외하고 남길 개수들까지만 정답에 append
        // cnt 사용이유 // 제일 처음 들어간 요소부터 꺼내기 위해
        for(int el : stack) {
            if (cnt >= stack.size() - k) break;
            sb.append(el);
            cnt++;
        }
        // waitingQ에 남은 요소가 있다면 그 전에 제거할 수 k가 다 카운팅 됐기 때문
        // 따라서 waitingQ에 남은 요소도 append
        while (!waitingQ.isEmpty()){
            sb.append(waitingQ.poll());
        }
        return sb.toString();
    }

    // fail TC10(time out)
    public String fail_sol(String number, int k) {
        StringBuilder sb = new StringBuilder(number);
        while (sb.length() > 0 && k > 0) {
            boolean isFlag = false;
            int cnt = 0;
            while (!isFlag) {
                // Problem Point 1 - 마지막요소는 비교 X
                if (cnt >= sb.length() - 1) {
                    break;
                }
                if (sb.charAt(cnt) < sb.charAt(cnt + 1)) {
                    sb.deleteCharAt(cnt);
                    isFlag = true;
                    break;
                }
                cnt++;
            }
            // Problem Point 2 - 모든 요소가 바로 뒤 요소보다 크면 제일 마지막 요소 제거
            if (!isFlag) {
                sb.deleteCharAt(sb.length() - 1);
            }
            k--;
        }
        return sb.toString();
    }

        public static void main(String[] args){
        PG_42883 pg_42883 = new PG_42883();
        System.out.println(pg_42883.codeOfMine("1924",2)); // expected : "94"
        System.out.println(pg_42883.codeOfMine("1231234", 3)); // expected : "3234"
        System.out.println(pg_42883.codeOfMine("4177252841"	,4)); // expected : "775841"
        System.out.println(pg_42883.codeOfMine("4321",1)); // expected : "432"
        System.out.println(pg_42883.codeOfMine("1000000",6)); // expected : "1"
        System.out.println(pg_42883.codeOfMine("989898",1)); // expected : 99898
        System.out.println(pg_42883.codeOfMine("999992",1)); // expected : 99999
    }
}
