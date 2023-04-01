package programmers.lv1;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class RemoveConsecutiveNum {
    public String solution(int []arr) {
        int cnt = arr.length;
        for(int i= arr.length-1; i > 0; i--){
            if(arr[i] == arr[i-1]) {
                // System.out.println("same"+arr[i]+":"+arr[i-1]);
                // System.out.println(i);
                // System.out.println(cnt);

                for(int j=i; j< cnt; j++){
                    arr[j-1] = arr[j];
                    // arr[arr.length-1] = -1; (다른 풀이)
                    // System.out.println("delete"+Arrays.toString(arr));
                }
                cnt--;
            }
        }

        int[] answer = new int[cnt];
        for(int i=0; i< cnt; i++){
            answer[i] = arr[i];;
        }
        /* (다른 풀이)
        String temp="";
        // int 배열 -> String -> -1 시작점부터 삭제
        for(int i=0; i < arr.length; i++){
            if(arr[i] == -1) break;
                temp += arr[i];
                System.out.println(temp);
        }

        int[] answer = new int[temp.length()];
        for(int i=0; i< temp.length(); i++){
            answer[i] = Integer.parseInt(String.valueOf(temp.charAt(i)));
        }
        */
        return Arrays.toString(answer);
    }

        /* Stack -> First In Last Out */
        public int[] exam1(int []arr) {
            int[] answer = {};

            Stack<Integer> stack = new Stack<>();

            for(int i : arr) {
                if(stack.isEmpty()) stack.push(i);
                else if(stack.peek() != i) stack.push(i);
                //System.out.println(stack);
            }
            // System.out.println("stack = " + stack);
            answer = new int[stack.size()];

            for(int i=answer.length -1; i>=0; i--) {
                answer[i] = stack.pop();
            }
            return answer;
        }

    /* Queue -> First In First Out */
    // queue의 head인 peek()은 첫번째 값
    public String exam2(int []arr) {
        int[] answer = {};
        int last = 0;

        Queue<Integer> queue = new LinkedList<>(); //int형 queue 선언, linkedlist 이용

        for(int i : arr) {
            if(queue.isEmpty()) {
                queue.add(i);
                last = i;
                //System.out.println("last"+last);
            }
            else if(last != i) {
                queue.add(i);
                last = i;
            }
            //System.out.println(queue);
        }
        // System.out.println("queue = " + queue);
        answer = new int[queue.size()];

        for(int i=0; i < answer.length; i++) { // queue.poll() -> queue.length-- -> i < queue.length (x)
            answer[i] = queue.poll();
        }
        return Arrays.toString(answer);
    }

    public int[] exam3(int []arr) {
        int[] answer ;
        int count = 1;

        for(int i=1; i<arr.length; i++){
            if(arr[i-1] != arr[i])
                count++;
        }
        answer = new int[count];

        count=1;

        answer[0] = arr[0];
        for(int i=1; i<arr.length; i++){
            if(arr[i-1] != arr[i]){
                answer[count] = arr[i]; // count를 배열 길이뿐만 아니라 index로도 사용
                count++;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        RemoveConsecutiveNum removeConsecutiveNum = new RemoveConsecutiveNum();
        int[] arr1 = {1,1,3,3,0,1,1};
        int[] arr2 = {4,4,4,3,3};
        System.out.println(removeConsecutiveNum.exam2(arr1)); // expected: [1,3,0,1]
        System.out.println(removeConsecutiveNum.exam2(arr2)); // expected: [4,3]
    }
}
