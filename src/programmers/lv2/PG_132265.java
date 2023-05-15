package programmers.lv2;

import java.util.HashSet;
import java.util.Set;

/*
 * input : 롤케이크에 올려진 토핑들의 번호를 저장한 정수 배열 topping
 * output : 롤케이크를 공평하게 자르는 방법의 수
 */
public class PG_132265 {
    public int solution(int[] topping) {
        int answer = 0;
        Set<Integer> duplicateCheck = new HashSet<>();
        int[] sumFromFront = new int[topping.length];
        int[] sumFromRear = new int[topping.length];

        // 1. 누적합 계산
        for(int i=0; i<topping.length; i++){
            if(i == 0){
                sumFromFront[i] = 1;
                duplicateCheck.add(topping[i]);
            } else {
                if (duplicateCheck.add(topping[i])) {
                    sumFromFront[i] = sumFromFront[i-1] + 1;
                } else {
                    sumFromFront[i] = sumFromFront[i-1];

                }
            }
        }

        // Problem Point - 초기화
        duplicateCheck.clear();

        for(int i=topping.length - 1; i>=0; i--){
            if(i == topping.length - 1){
                sumFromRear[i] = 1;
                duplicateCheck.add(topping[i]);
            } else {
                if (duplicateCheck.add(topping[i])) {
                    sumFromRear[i] = sumFromRear[i+1] + 1;
                } else {
                    sumFromRear[i] = sumFromRear[i+1];
                }
            }
        }

        // 2. 경우의 수 계산
        for(int i=0; i<topping.length-1; i++){
            if(sumFromFront[i] == sumFromRear[i+1]){
                answer++;
            }
        }
        return answer;
    }
    public static void main(String[] args){
        PG_132265 pg_132265 = new PG_132265();
        System.out.println(pg_132265.solution(new int[]{1, 2, 1, 3, 1, 4, 1, 2})); // expected : 2
        System.out.println(pg_132265.solution(new int[]{1, 2, 3, 1, 4})); // expected : 0
    }
}
