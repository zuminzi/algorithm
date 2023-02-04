package programmers.LV1;


import java.util.*;

public class CraneCatchGame_02 {

    public class Matrix {
        Queue<Integer> columnQueue;

        public Matrix(){
            columnQueue = new LinkedList<>();
        }

        public Queue<Integer> getColumnQueue() {
            return columnQueue;
        }
    }

    public int codeOfMine(int[][] board, int[] moves) {
        List<Matrix> matrixList = new ArrayList<>();
        Stack<Integer> basket = new Stack<>();
        int prev = -1;
        int popCnt = 0;

        // add board to matrixList
        for(int i=0; i< board.length; i++){
            Matrix matrix = new Matrix();
            matrixList.add(matrix);

            for(int k=0; k<board[0].length; k++){
                if(board[k][i] != 0){
                    // 열끼리 저장
                    matrixList.get(i).getColumnQueue().offer(board[k][i]);
                }
            }
        }

        for(int i=0; i< moves.length; i++){
            if(matrixList.get(moves[i] - 1).getColumnQueue().isEmpty() == false) {
                int target = matrixList.get(moves[i] - 1).getColumnQueue().poll();
                if (target == prev) {
                    basket.pop();
                    popCnt++;
                    // pop 후에 prev는 더 이상 바구니에 남아있지 않으므로 pop한 요소의 이전 요소 참조
                    prev = basket.isEmpty() == true? 0 : basket.peek();
                } else {
                    basket.push(target);
                    prev = target;
                }
            }
        }
        return popCnt*2;
    }


    public static void main(String[] args){
        CraneCatchGame_02 craneCatchGame_02 = new CraneCatchGame_02();
        System.out.println(craneCatchGame_02.codeOfMine(new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 4, 4, 0}, {1, 2, 2, 1}},
                new int[] {2, 3, 1, 4, 2, 3})); // expected : 6
    }
}
/*
 ** Accuracy Test
 * 01 solution보다 효율성은 떨어지지만 가독성 높은 풀이 시도
 * 1) First In Last Out -> Use Stack, 2) Generate Class Object
테스트 1 〉	통과 (0.39ms, 70.6MB)
테스트 2 〉	통과 (0.61ms, 67.7MB)
테스트 3 〉	통과 (0.48ms, 76.4MB)
테스트 4 〉	통과 (2.58ms, 72.4MB)
테스트 5 〉	통과 (0.41ms, 65.9MB)
테스트 6 〉	통과 (0.63ms, 82.4MB)
테스트 7 〉	통과 (0.52ms, 75.2MB)
테스트 8 〉	통과 (1.73ms, 85.2MB)
테스트 9 〉	통과 (1.42ms, 67.1MB)
테스트 10 〉	통과 (1.32ms, 74.3MB)
테스트 11 〉	통과 (1.71ms, 73.1MB)
 */