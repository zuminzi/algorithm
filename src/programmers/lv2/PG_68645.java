package programmers.lv2;

public class PG_68645 {
    /**
     * @param n 삼각형의 밑변과 높이
     * @return 반시계 방향으로 달팽이 채우기 후, 첫 행부터 마지막 행까지 모두 순서대로 합친 새로운 배열
     - 1) 인덱스 간단하게 연산하기 위해 2차원 배열에 채운 다음 1차원 배열로 변환
     - 2) 삼각형을 채울 때 n%3 나머지에 따라 진행 방향이 다르다는 점 이용
     - 3) 채우는 칸 수가 n부터 1씩 감소한다는 점 이용
       - down n칸, right n-1칸, diagonal n-2칸, down n-3칸 ...
     */
    public int[] solution(int n) {
        int[] answer = new int[(n * (n + 1)) / 2];
        int[][] matrix= new int[n][n];
        int row = -1; // line 17에서 전위연산하고 있기 때문에 -1부터 시작
        int col = 0;
        int cnt = 1;
        int total = n;

        while (total > 0) {
            for (int i=0; i<total; i++){
                matrix[++row][col] = cnt++;
            }
            total--;
            for (int i=0; i<total; i++){
                matrix[row][++col] = cnt++;
            }
            total--;
            for (int i=0; i<total; i++){
                matrix[--row][--col] = cnt++;
            }
            total--;
        }

        cnt = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(matrix[i][j] == 0) break;
                answer[cnt++] = matrix[i][j];
            }
        }
        return answer;
    }

    public static void main (String[] args){
        PG_68645 pg_68645 = new PG_68645();
        // expected : [1,2,9,3,10,8,4,5,6,7]
        System.out.println(pg_68645.solution(4));
    }
}
