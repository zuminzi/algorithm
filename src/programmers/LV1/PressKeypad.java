package programmers.LV1;

public class PressKeypad {
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
        String answer = "";

        for(int i=0; i<numbers.length; i++){
            target = numbers[i];
            if(target == 1 || target == 4 || target == 7){
                answer += "L";
                left = target;
            } else if(target == 3 || target == 6 || target == 9){
                answer += "R";
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
                        answer += hand.substring(0, 1).toUpperCase();
                        if (hand.equals("left")) { // String 비교는 equals ## 주의
                            left = target;
                        } else {
                            right = target;
                        }
                    } else if(min == left_distance){
                        answer += "L";
                        left = target;
                    } else {
                        answer += "R";
                    right = target;}
                }
        //System.out.println("left="+left+",right="+right);
        }
        return answer;
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
