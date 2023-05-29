package programmers.lv3;

import java.util.Arrays;

/*
 * input : 출전 순서대로 나열되어있는 배열 A,  i번째 원소가 B팀의 i번 팀원이 부여받은 수를 의미하는 배열 B
 * output : B 팀원들이 얻을 수 있는 최대 승점
 */
public class PG_12987 {
    // 투포인터 활용
    public int solution(int[] A, int[] B) {
        int point = 0;
        int pointerA = A.length - 1;
        int pointerB = B.length - 1;

        Arrays.sort(A);
        Arrays.sort(B);

        // 두 배열 다 오름차순으로 정렬되어 있으므로, A 숫자를 이길 수 없다면 다음 A 숫자와 비교 **
        // 더지니어스 흑과 백 게임의 전략처럼 이길 수 없는 숫자면 제일 작은 숫자를 제시하여 큰 숫자를 이길 수 있는 숫자와 비교
        // 즉, pointerB를 줄여서 다음 A 숫자와 비교하는 행위는 이전에 이길 수 없는 A 숫자에는 B의 가장 작은 숫자 카드를 제시한 것이나 마찬가지
        while(pointerA >= 0 && pointerB >= 0){
            if(A[pointerA] < B[pointerB]){
                point++;
                pointerB--;
            }
            pointerA--;
        }

        return point;
    }

    public static void main (String[] args){
        PG_12987 pg_12987 = new PG_12987();
        System.out.println(pg_12987.solution(new int[] {5,1,3,7}, new int[]{2,2,4,8}));
        System.out.println(pg_12987.solution(new int[] {5,1,3,7}, new int[]{2,2,6,8}));
        System.out.println(pg_12987.solution(new int[]{2,2,2,2}, new int[]{1,1,1,1}));
    }
}
