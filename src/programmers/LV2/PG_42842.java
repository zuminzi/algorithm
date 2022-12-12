package programmers.LV2;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class PG_42842 {
    /*
    * CodeOfMine Solution Point
    1) not only ( width*height == brown+yellow ), but also ( (width-2)(height-2)==yellow )
    2) 즉 이차 방정식(구하는 값 width(x),height(y))를 풀기 위해서는
      brown과 yellow 관계뿐만 아니라, (가로-세로)와 brown or yellow 관계로 식을 하나 더 설계
    3) 모든 약수를 구하는 알고리즘은 O(sqrt(n))
      - n의 약수들 중 n‾√ 이하인 것들만 구하면, 나머지는 n을 그것들로 나눠 구할 수 있다는 것
      - 그러나 약수 판별이 아닌 소수 판별인 경우 약수가 아닌 경우를 구하지 말고 에라토스테네스의 체 풀이로
    4) ( width*height == brown+yellow ) 즉, (brown+yellow)의 약수 중에서도 중앙값이 의미있다는 것을 문제 입출력 예제들로 캐치
      - 1,3,9 -> [3,3]
      - 1,2,3,4,6,8,12,16,24,48 -> [8,6]
      - 1,2,3,4.6,12 -> [4,3]
     */

    public String codeOfMine(int brown, int yellow) {
        int[] answer = new int[2];
        int total = brown + yellow;
        Map<Integer,Integer> divisors = new LinkedHashMap<>(); // 입출력 순서 유지 필요

        for(int i=1; i<=Math.sqrt(total); i++){
            if(total % i == 0){
                if((i-2) * ((total/i) - 2) == yellow) {
                    // total‾√까지만 루프문을 돌린 후, 짝을 이루는 약수를 한번에 집어넣으면
                    // 중앙값(median)은 가장 마지막에 추가된 요소가 중앙값이 됨
                    divisors.put( i , total / i );
                }
            }
        }

        // size를 indx에 넣을 때는 항상 -1 처리 필요 주의
        answer[1] = (int) divisors.keySet().toArray()[divisors.size() - 1]; // key
        answer[0] = (int) divisors.values().toArray()[divisors.size() - 1]; // value
        return Arrays.toString(answer);
    }

    // 그림으로 brown, yellow 각각의 식 도출하여 방정식 해(x,y) 구하기
    public int[] exam1(int brown, int yellow) {
        // brown = 2x+2y-4
        // yellow = (x-2)(y-2)

        int[] answer = {0, 0};
        for (int col = 1; col <= yellow; col++) { // col = yellowColumn, row = yellowRow
            int row = yellow / col;
            if (col * row == yellow) {
                // (yellowColumn * 2) + (yellowRow * 2) + corner(4) = brown
                if (col * 2 + row * 2 + 4 == brown) {
                    // total Width = yellowRow + 2
                    // total Height = yellowColumn + 2
                    answer[0] = row + 2;
                    answer[1] = col + 2;
                    return answer;
                }
            }
        }
        return answer;
    }

    public int[] exam2(int brown, int yellow) {
        int[] answer = {};
        int area = brown + yellow;
        for (int i = 1; i <= area; i++) {
            int row = i;
            // area % i == 0 체크하지 않고
            // 바로 소수점 떼서 정수 타입 변수 col로 선언
            int col = area / row;


            if (row > col) {
                // continue 아래 코드는 실행하지 않고 다음차례 i 반복문으로 넘어감
                // 약수는 짝을 이루므로 row > col 이후부터는 반복할 필요 없음
                continue;
            }

            if ((row-2)*(col-2) == yellow) {
                answer = new int[]{col, row};
                break;
            }
        }
        return answer;
    }

    public static void main(String[] args){
        PG_42842 pg_42842 = new PG_42842();
        System.out.println(pg_42842.exam2(10,2)); // expected : [4,3]
        System.out.println(pg_42842.exam2(8,1)); // expected : [3,3]
        System.out.println(pg_42842.exam2(18,6)); // expected : [8,3]
    }
}
/*
* Exam2 Accuracy Test

테스트 1 〉	통과 (0.03ms, 79.1MB)
테스트 2 〉	통과 (0.02ms, 77.7MB)
테스트 3 〉	통과 (0.09ms, 66.6MB)
테스트 4 〉	통과 (0.03ms, 78.7MB)
테스트 5 〉	통과 (0.02ms, 76.7MB)
테스트 6 〉	통과 (0.03ms, 64.8MB)
테스트 7 〉	통과 (0.05ms, 70.2MB)
테스트 8 〉	통과 (0.06ms, 77.2MB)
테스트 9 〉	통과 (0.07ms, 77.3MB)
테스트 10 〉	통과 (0.08ms, 71MB)
테스트 11 〉	통과 (0.02ms, 77.6MB)
테스트 12 〉	통과 (0.03ms, 78.5MB)
테스트 13 〉	통과 (0.02ms, 75.1MB)

* Exam1 Accuracy Test
- Math.sqrt(brown+yellow)가 아닌 yellow 횟수만큼만 루프문 반복하므로 속도 향상

테스트 1 〉	통과 (0.01ms, 72.7MB)
테스트 2 〉	통과 (0.02ms, 74.8MB)
테스트 3 〉	통과 (0.03ms, 72.8MB)
테스트 4 〉	통과 (0.02ms, 77.2MB)
테스트 5 〉	통과 (0.03ms, 72.6MB)
테스트 6 〉	통과 (0.04ms, 78.3MB)
테스트 7 〉	통과 (0.03ms, 71.2MB)
테스트 8 〉	통과 (0.06ms, 76.9MB)
테스트 9 〉	통과 (0.03ms, 77.6MB)
테스트 10 〉	통과 (0.06ms, 78MB)
테스트 11 〉	통과 (0.02ms, 78.9MB)
테스트 12 〉	통과 (0.01ms, 75.3MB)
테스트 13 〉	통과 (0.01ms, 79.5MB)

* CodeOfMine Accuracy Test

테스트 1 〉	통과 (0.25ms, 86.8MB)
테스트 2 〉	통과 (0.50ms, 76.9MB)
테스트 3 〉	통과 (0.32ms, 77MB)
테스트 4 〉	통과 (0.17ms, 71.7MB)
테스트 5 〉	통과 (0.23ms, 71.9MB)
테스트 6 〉	통과 (0.28ms, 84.5MB)
테스트 7 〉	통과 (0.21ms, 73.7MB)
테스트 8 〉	통과 (0.22ms, 77.5MB)
테스트 9 〉	통과 (0.22ms, 75MB)
테스트 10 〉	통과 (0.24ms, 72.3MB)
테스트 11 〉	통과 (0.17ms, 79.4MB)
테스트 12 〉	통과 (0.27ms, 72.3MB)
테스트 13 〉	통과 (0.34ms, 81.9MB)
 */