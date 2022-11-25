package programmers.LV1;

/* sitch문 동시에 여러 케이스 */
public class PressKeypad_exam2 {
    private static final String LEFT = "L";
    private static final String RIGHT = "R";
    private static final int[][] KEYPAD = new int[][] {
            {3, 1},
            {0, 0},
            {0, 1},
            {0, 2},
            {1, 0},
            {1, 1},
            {1, 2},
            {2, 0},
            {2, 1},
            {2, 2}
    };

    public String solution(int[] numbers, String hand) {
        // 시작지점
        int[] leftXY = {3,0};
        int[] rightXY = {3,2};

        StringBuilder sb = new StringBuilder();
        for (int i : numbers) {
            switch (i) {
                case 1:
                case 4:
                case 7:
                    leftXY = KEYPAD[i];
                    sb.append(LEFT);
                    break;
                case 3:
                case 6:
                case 9:
                    rightXY = KEYPAD[i];
                    sb.append(RIGHT);
                    break;
                case 2 :
                case 5:
                case 8:
                case 0:
                    String finger = getSuitablefinger(leftXY, rightXY, i, hand);
                    sb.append(finger);

                    if (finger.equalsIgnoreCase(LEFT)) {
                        leftXY = KEYPAD[i];
                    } else {
                        rightXY = KEYPAD[i];
                    }

                    break;
            }
        }

        return sb.toString();
    }

    private int getDifference(int[] xy, int[] keypad) {
        return Math.abs(xy[0] - keypad[0]) + Math.abs(xy[1] - keypad[1]);
    }

    private String getSuitablefinger(int[] leftXY, int[] rightXY, int number, String hand) {
        int leftDifference = getDifference(leftXY, KEYPAD[number]);
        int rightDifference = getDifference(rightXY, KEYPAD[number]);

        String finger;
        if (leftDifference > rightDifference) {
            finger = RIGHT;
        } else if (leftDifference < rightDifference) {
            finger = LEFT;
        } else {
            finger = ("right".equals(hand) ? RIGHT : LEFT);
        }

        return finger;
    }
}
