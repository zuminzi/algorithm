package programmers.LV2;

import java.util.Arrays;
import java.util.Stack;

// input : 초 단위로 기록된 주식가격이 담긴 배열 prices // 1 이상 10,000 이하
// output :  각 가격이 떨어지지 않은 기간은 몇 초인지
public class PG_42584 {
    // Accuracy Test ~0.27ms, 72.1MB, Efficiency Test ~17.68ms, 71.5MB
    public int[] solution(int[] prices){
        int[] answer = new int[prices.length];
        for(int i=0; i<prices.length; i++){
            for(int k=i+1; k< prices.length; k++){
                if(prices[i] > prices[k]){
                    answer[i] = k - i;
                    break;
                }
            }
        }
        for(int i=0; i<answer.length; i++){
            if(answer[i] == 0){
                answer[i] = (answer.length-1) - i;
            }
        }
        return answer;
    }

    // Stack
    // 가격이 아닌 인덱스를 스택으로 관리
    public int[] fail_sol_2(int[] prices) {
        int[] answer = new int[prices.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < prices.length; i++) {
            if(!stack.isEmpty()) {
                while(prices[i] < prices[stack.peek()]) {
                    answer[stack.peek()] = i - stack.pop();
                }
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            answer[stack.peek()] = (prices.length-1) - stack.pop();
        }
        return answer;
    }

    // Stack
    public int[] fail_sol(int[] prices) {
        int[] answer = new int[prices.length];
        Stack<Integer> stack = new Stack<>();

        for(int i=0; i<prices.length-1; i++){
            if(stack.isEmpty() || prices[i] > stack.peek()){
                stack.push(prices[i]);
            } else if(!stack.isEmpty() && prices[i] < stack.peek()){
                while (prices[i] < stack.peek()) {
                    if(prices[i] >= stack.peek()) break;
                    answer[stack.size()-1] = i - (stack.size()-1);
                    stack.pop();
                }
            }
        }

        for(int i=0; i<answer.length; i++){
            if(answer[i] == 0){
                answer[i] = (answer.length-1) - i;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        PG_42584 pg_42584 = new PG_42584();
        System.out.println(pg_42584.solution(new int[]{1, 2, 3, 2, 3})); // expected : [4, 3, 1, 1, 0]
        System.out.println(pg_42584.solution(new int[]{1, 2, 3, 2, 3, 1})); // expected : [5, 4, 1, 2, 1, 0]
    }
}
