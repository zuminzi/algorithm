package programmers.LV1;

public class SumMatrix {
    public int[][] codeOfMine(int[][] arr1, int[][] arr2) {
        int row_size = arr1.length;
        int column_size = arr1[0].length;
        int[][] answer = new int[row_size][column_size];

        for(int i=0; i<row_size; i++){
           for(int j=0; j<column_size; j++){
               answer[i][j] = arr1[i][j] + arr2[i][j];
           }
        }
        return answer;
    }

    /* answer 배열에 arr1 할당 후, arr2 더하기 */
    public int[][] exam(int[][] arr1, int[][] arr2) {
        int[][] answer = {};
        answer = arr1;
        for(int i=0; i<arr1.length; i++){
            for(int j=0; j<arr1[0].length; j++){
                answer[i][j] += arr2[i][j];
            }
        }
        return answer;
    }
}
