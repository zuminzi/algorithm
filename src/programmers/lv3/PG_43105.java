package programmers.lv3;

import java.util.Arrays;

public class PG_43105 {
    // top-down
    // 대각선 Left, Right 중 큰 수 합하여 반영
    // Accuracy Test ~0.31ms, 78.3MB, Efficiency Test ~10.31ms, 61.9MB
    public int codeOfMine(int[][] triangle) {
        for(int i= triangle.length-2; i>=0; i--){
            for(int j=0; j<triangle[i].length; j++){
                triangle[i][j] += Math.max( triangle[i+1][j], triangle[i+1][j+1] );
            }
        }
        return triangle[0][0];
    }

    // bottom-up
    // Accuracy Test ~1.52ms, 78.4MB, Efficiency Test ~9.86ms, 76MB
    public int exam(int[][] triangle) {
        for (int i = 1; i < triangle.length; i++) {
            // 첫 번째 열 이전 요소와 합계산 // Math.max()로 비교할 필요 없으니 분리
            triangle[i][0] += triangle[i-1][0];
            // 마지막 열 이전 요소와 합계산 // Math.max()로 비교할 필요 없으니 분리
            triangle[i][i] += triangle[i-1][i-1];

            // 첫 번째 열, 마지막 열 제외한 가운데 열(1~)은 이전의 두 요소 중 Math.max() 비교하여 반영
            for (int j = 1; j < i; j++)
                triangle[i][j] += Math.max(triangle[i-1][j-1], triangle[i-1][j]);
        }

        // 삼각형 밑변 요소에서 max return
        return Arrays.stream(triangle[triangle.length-1]).max().getAsInt();
    }

    public static void main(String[] args){
        PG_43105 pg_43105 = new PG_43105();
        // expected : 30
        System.out.println(pg_43105.exam(new int[][]{{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}}));
    }
}
