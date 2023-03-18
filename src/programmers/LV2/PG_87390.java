package programmers.LV2;

import java.util.stream.LongStream;

public class PG_87390 {
    // ~11.55ms, 94.2MB
    public int[] codeOfMine(int n, long left, long right) {
        int [] answer = new int[(int)(right-left)+1];

        int index = 0;
        for(long i=left; i<=right; i++){
            int share = (int) Math.ceil( (double) ( i + 1) / n );
            int remainder = (i + 1) % n == 0? n : (int) ( (i + 1) % n);

            // 2차원 배열 요소를 전부 구하지 않고 행 / 열 관계만으로 파악 가능
            if(share < remainder) {
                answer[index++] = remainder;
            }else{
                answer[index++] = share;
            }
        }
        return answer;
    }

    // 기본형 특화 스트림인 IntStream과 LongStream에는 정적 메서드인 range와 rangeClosed가 있다
    // 특정 범위의 숫자를 차례대로 생성해주는 기능이다
    // range는 연산 범위에 마지막 값을 포함시키지 않고 rangeClosed는 마지막 값을 포함시킨다
    public int[] exam(int n, long left, long right) {
        return LongStream
                .rangeClosed(left, right)
                // 몫과 나머지 중 더 큰 수에 해당
                .mapToInt(value -> (int) (Math.max(value / n, value % n) + 1))
                .toArray();
    }

    // fail Accuracy Test
    public int[] fail_sol(int n, long left, long right) {
        int[][] two_dimensional_arr = new int[n][n];
        int [] answer = new int[(int)(right-left)+1];
        for(int i=0; i<n; i++){
            int k = 0;
            for(; k<i+1; k++){
                two_dimensional_arr[i][k] = i+1;
            }
            for(int j=k-1; j>=0; j--){
                two_dimensional_arr[j][i] = i+1;
            }
        }
        int index = 0;
        for(long i=left; i<=right; i++){
            double share = Math.ceil( (double) ( i + 1) / n );
            int remainder = (i + 1) % n == 0? n : (int) ( (i + 1) % n);
            answer[index++] = two_dimensional_arr[ (int)share - 1 ][ remainder - 1];
        }
        return answer;
    }

    public static void main(String[] args){
        PG_87390 pg_87390 = new PG_87390();
        System.out.println(pg_87390.codeOfMine(3,2,5)); // expected : [3,2,2,3]
        System.out.println(pg_87390.codeOfMine(4,7,14)); // expected : [4,3,3,3,4,4,4,4]
    }
}
