package programmers.LV2;

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
    // Accuracy Test ~1.81ms, 83.2MB, Efficiency Test ~83.49ms, 72.8MB
    public int[] solution_refactor(int[] prices) {
        int[] answer = new int[prices.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < prices.length; i++) {
            //if(!stack.isEmpty()) // while문 조건에 stack not empty 조건 넣지 않고 이것만 넣으면 Fail
                // Problem  Point - while문 조건이나 break 조건에 !stack.isEmpty()를 걸어주지 않을 경우 stack이 없음에도 비교하려는 EmptyStackException 발생
                while (!stack.isEmpty() && prices[i] < prices[stack.peek()]) {
                    //if(stack.isEmpty()) break; // while문 조건에 stack not empty 조건 넣지 않고 이것만 넣으면 Fail // while문에서 stack.peek와 비교 전에 EmptyStackException 발생
                    answer[stack.peek()] = i - stack.pop();
                }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            // 마지막 인덱스 - 본인 인덱스
            answer[stack.peek()] = (prices.length-1) - stack.pop();
        }
        return answer;
    }

    public static void main(String[] args) {
        PG_42584 pg_42584 = new PG_42584();
        System.out.println(pg_42584.solution_refactor(new int[]{1, 2, 3, 2, 3})); // expected : [4, 3, 1, 1, 0]
        System.out.println(pg_42584.solution_refactor(new int[]{1, 2, 3, 2, 3, 1})); // expected : [5, 4, 1, 2, 1, 0]
        System.out.println(pg_42584.solution_refactor(new int[]{5,4,3,2,5})); // expected : [1,1,1,1,0]
        System.out.println(pg_42584.solution_refactor(new int[]{1,2,3,2,1})); // expected : [4,3,1,1,0]
    }
}
