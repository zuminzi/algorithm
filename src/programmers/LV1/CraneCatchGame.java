package programmers.LV1;


import java.util.ArrayList;
import java.util.List;

public class CraneCatchGame {

    public int solution(int[][] board, int[] moves) {
        List<Integer> basket = new ArrayList<>();
        int prev = -1;
        int popCnt = 0;
        int[] rowCntArray = new int[board.length];
        for(int i=0; i< rowCntArray.length; i++){
            rowCntArray[i] = 0;
        }

        for(int i=0; i< moves.length; i++){
            int rowCnt = rowCntArray[moves[i] - 1];
            if(rowCnt < board.length) {


                while (board[rowCnt][moves[i] - 1] == 0) {
                    rowCnt += 1;
                    if (rowCnt > board.length) break;
                }
                int target = board[rowCnt][moves[i] - 1];
                // Problem Point 1 -> 값이 증가된 rowCnt rowCntArray에 update 필요
                rowCntArray[moves[i] - 1] = ++rowCnt;

                if (target == prev) {
                    basket.remove(basket.size() - 1);
                    popCnt++;
                    // 동일 요소 소거 후에는 이전 요소를 해당 요소의 그 이전 요소(스택 바구니 제일 위의 요소)로 재설정
                    // Problem Point 2 -> 바구니에서 꺼낸 후 이전 요소 재설정해줄 때 바구니 사이즈 0인 경우 예외 처리 미흡
                    prev = basket.isEmpty() == true? 0 : basket.get( basket.size() - 1);
                } else {
                    basket.add(target);
                    prev = target;
                }
            }
        }
        return popCnt*2;
    }


    public static void main(String[] args){
        CraneCatchGame craneCatchGame = new CraneCatchGame();
        System.out.println(craneCatchGame.solution(new int[][]{{0,0,0,0,0},{0,0,1,0,3},{0,2,5,0,1},{4,2,4,4,2},{3,5,1,3,1}},
                new int[]{1,5,3,5,1,2,1,4})); // expected : 4
        System.out.println(craneCatchGame.solution(new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 4, 4, 0}, {1, 2, 2, 1}},
                new int[] {2, 3, 1, 4, 2, 3})); // expected : 6
    }
}
/*
  ** Accuracy Test
테스트 1 〉	통과 (0.04ms, 71.1MB)
테스트 2 〉	통과 (0.07ms, 73.1MB)
테스트 3 〉	통과 (0.07ms, 76.8MB)
테스트 4 〉	통과 (0.37ms, 92.7MB)
테스트 5 〉	통과 (0.05ms, 76.2MB)
테스트 6 〉	통과 (0.05ms, 85.2MB)
테스트 7 〉	통과 (0.08ms, 78.7MB)
테스트 8 〉	통과 (0.12ms, 72.9MB)
테스트 9 〉	통과 (0.17ms, 81.7MB)
테스트 10 〉	통과 (0.14ms, 81.9MB)
테스트 11 〉	통과 (0.26ms, 77.4MB)
 */
