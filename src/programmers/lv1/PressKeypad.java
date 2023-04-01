package programmers.lv1;

public class PressKeypad {
    /* 반복문에서 (String += 연산자) vs (StringBuffer) vs (StringBuilder) 비교

    속도: += > StringBuffer > StringBuilder
    StringBuffer와 StringBuilder 차이점: StringBuffer는 멀티스레드에서 동기화 지원, StringBuilder 미지원(단일 스레드에서만 사용가능)
     */
    /* String += 연산자 사용 시 속도와 메모리 사용량
    테스트 1 〉	통과 (2.35ms, 74.2MB)
    테스트 2 〉	통과 (1.71ms, 82.5MB)
    테스트 3 〉	통과 (1.56ms, 73.6MB)
    테스트 4 〉	통과 (2.10ms, 73.2MB)
    테스트 5 〉	통과 (2.07ms, 78.1MB)
    테스트 6 〉	통과 (3.43ms, 71.3MB)
    테스트 7 〉	통과 (3.38ms, 74.7MB)
    테스트 8 〉	통과 (2.75ms, 77.1MB)
    테스트 9 〉	통과 (2.17ms, 78.7MB)
    테스트 10 〉	통과 (2.74ms, 73MB)
    테스트 11 〉	통과 (2.66ms, 82.5MB)
    테스트 12 〉	통과 (2.68ms, 72.7MB)
    테스트 13 〉	통과 (2.49ms, 67.2MB)
    테스트 14 〉	통과 (4.52ms, 72.2MB)
    테스트 15 〉	통과 (6.12ms, 79.1MB)
    테스트 16 〉	통과 (4.58ms, 75.4MB)
    테스트 17 〉	통과 (6.22ms, 81MB)
    테스트 18 〉	통과 (6.81ms, 82.2MB)
    테스트 19 〉	통과 (8.32ms, 65.5MB)
    테스트 20 〉	통과 (5.69ms, 68.5MB)
     */
    /* StringBuffer 사용 시 속도와 메모리 사용량
    테스트 1 〉	통과 (0.08ms, 75.8MB)
    테스트 2 〉	통과 (0.20ms, 76.1MB)
    테스트 3 〉	통과 (0.13ms, 69MB)
    테스트 4 〉	통과 (0.10ms, 72.1MB)
    테스트 5 〉	통과 (0.14ms, 72.6MB)
    테스트 6 〉	통과 (0.13ms, 74.1MB)
    테스트 7 〉	통과 (0.18ms, 78.3MB)
    테스트 8 〉	통과 (0.33ms, 71.3MB)
    테스트 9 〉	통과 (0.34ms, 74MB)
    테스트 10 〉	통과 (0.33ms, 73.9MB)
    테스트 11 〉	통과 (0.67ms, 84.4MB)
    테스트 12 〉	통과 (0.58ms, 70.8MB)
    테스트 13 〉	통과 (0.74ms, 75MB)
    테스트 14 〉	통과 (1.11ms, 73.8MB)
    테스트 15 〉	통과 (2.48ms, 66.2MB)
    테스트 16 〉	통과 (2.75ms, 75MB)
    테스트 17 〉	통과 (3.26ms, 72.2MB)
    테스트 18 〉	통과 (3.85ms, 79MB)
    테스트 19 〉	통과 (3.93ms, 73.6MB)
    테스트 20 〉	통과 (3.44ms, 74.4MB)
     */
    public String codeOfMine(int[] numbers, String hand) {
        int target;
        int min = -1;
        int left = '*'; // 42
        int right = '#'; // 35
        int left_x = -1;
        int left_y = -1;
        int right_x = -1;
        int right_y = -1;
        int target_x = -1;
        int target_y = -1;
        String[][] arr = {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}, {"42", "0", "35"}};
        //String answer = "";
        StringBuffer sb = new StringBuffer();

        for(int i=0; i<numbers.length; i++){
            target = numbers[i];
            if(target == 1 || target == 4 || target == 7){
                sb.append("L");
                left = target;
            } else if(target == 3 || target == 6 || target == 9){
                sb.append("R");
                right = target;
            } else {
                for (int j = 0; j < arr.length; j++) {
                    //System.out.println("---new target start="+numbers[i]+"---");
                    for (int k = 0; k < arr[0].length; k++) {
                        if (Integer.parseInt(arr[j][k]) == left) {
                            left_x = j;
                            left_y = k;
                        }
                        if (Integer.parseInt(arr[j][k]) == right) {
                            right_x = j;
                            right_y = k;
                        }
                        if (Integer.parseInt(arr[j][k]) == target) {
                            target_x = j;
                            target_y = k;
                        }
                        //if(left_x != -1 && left_y != -1 && right_x != -1 && right_y != -1 && target_x != -1 && target_y != -1){break;}
                    }
                }
                    int left_distance = Math.abs(target_x-left_x) + Math.abs(target_y-left_y);
                    int right_distance = Math.abs(target_x-right_x) + Math.abs(target_y-right_y);
                    //System.out.println("left_dt="+left_distance);
                    //System.out.println("right_dt="+right_distance);
                    min = Math.min(left_distance, right_distance);
                    if (left_distance == right_distance) {
                        sb.append( hand.substring(0, 1).toUpperCase() );
                        if (hand.equals("left")) { // String 비교는 equals ## 주의
                            left = target;
                        } else {
                            right = target;
                        }
                    } else if(min == left_distance){
                        sb.append("L");
                        left = target;
                    } else {
                        sb.append("R");
                    right = target;}
                }
        //System.out.println("left="+left+",right="+right);
        }
        return sb.toString();
    }
    public static void main(String[] args){
        PressKeypad pressKeypad = new PressKeypad();
        int[] num1 = {1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5};
        int[] num2 = {7, 0, 8, 2, 8, 3, 1, 5, 7, 6, 2};
        int[] num3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        System.out.println(pressKeypad.codeOfMine(num1,"right")); // expected : "LRLLLRLLRRL"
        System.out.println(pressKeypad.codeOfMine(num2,"left")); // expected : "LRLLRRLLLRR"
        System.out.println(pressKeypad.codeOfMine(num3,"right")); // expected : "LLRLLRLLRL"
    }
}
