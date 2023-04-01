package programmers.lv2;

public class PG_12949 {
    public int[][] solution(int[][] arr1, int[][] arr2) {
        System.out.println(arr2[0].length);
        int[][] answer = new int[arr1.length][arr2[0].length];
        for(int i=0; i<arr1.length; i++){
            for(int k=0; k<arr2[0].length; k++){
                // 행렬 곱 가능한 조건 : arr1의 행 길이 = arr2의 열 길이
                for(int j=0; j<arr1[0].length; j++) {
                    answer[i][k] += arr1[i][j] * arr2[j][k];
                }
            }
        }
        return answer;
    }
    public static void main(String[] args){
        PG_12949 pg_12949 = new PG_12949();
        System.out.println(pg_12949.solution(new int[][]{{1, 4}, {3, 2}, {4, 1}}, new int[][]{{3,3},{3,3}})); // expected: [[15, 15], [15, 15], [15, 15]]
        System.out.println(pg_12949.solution(new int[][]{{1,2,3,4},{1,2,3,4}},new int[][]{{1,2},{1,2},{1,2},{1,2}})); // expected : [10,20], [10,20]
    }
}
