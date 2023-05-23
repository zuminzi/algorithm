package programmers.lv2;

import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * input : 비내림차순 정수 배열 sequence, 수열로 만들어야 하는 합 k
 * output : 위 조건을 만족하는 부분 수열의 시작 인덱스와 마지막 인덱스를 배열에 담아 return
 - 합이 k인 부분 수열이 여러 개인 경우 길이가 짧은 수열을 찾습니다. (우선순위 기준 1)
 - 길이가 짧은 수열이 여러 개인 경우 앞쪽(시작 인덱스가 작은)에 나오는 수열을 찾습니다. (우선순위 기준 2)
 */
public class PG_178870 {
    // 투포인터 활용
    // ~47.87ms, 159MB
    public int[] solution(int[] sequence, int k) {
        int left = 0;
        int right = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() { // 우선순위 기준 1,2 반영
            @Override
            public int compare(int[] o1, int[] o2) {
                int diff1 = o1[1] - o1[0];
                int diff2 = o2[1] - o2[0];
                if (diff1 > diff2) {
                    return 1;
                } else if (diff1 < diff2) {
                    return -1;
                } else {
                    return Integer.compare(o1[0], o2[0]);
                }
            }
        });

        int sum = sequence[left];
        while (left <= sequence.length - 1 && right <= sequence.length - 1) {
            // CASE 1. k와 같을 때는 자료구조에 넣고 오른쪽 포인터 이동
            // 오른쪽 포인터가 끝에 도달한 경우 왼쪽 포인터 이동
            if (sum == k) {
                pq.add(new int[]{left, right});
                if (right + 1 < sequence.length) {
                    right++;
                    sum += sequence[right];
                } else if (left + 1 < sequence.length) {
                    sum -= sequence[left];
                    left++;
                // Problem Point - 마지막 else문 처리
                } else {
                    break;
                }
            // CASE 2. k보다 작을 때는 오른쪽 포인터 이동
            } else if (sum < k) {
                if (right + 1 >= sequence.length) {
                    break;
                }
                right++;
                sum += sequence[right];
            // CASE 3. k보다 클 때는 왼쪽 포인터 이동
            } else if (sum > k) {
                if (left + 1 >= sequence.length) {
                    break;
                }
                // 이동하기 전 왼쪽 포인터가 가리키고 있던 요소 합에서 제외하기
                sum -= sequence[left];
                left++;
            }
        }
        return pq.peek();
    }

    public static void main(String[] args){
        PG_178870 pg_178870 = new PG_178870();
        System.out.println(pg_178870.solution(new int[]{1, 2, 3, 4, 5},7)); // expected : [2,3]
        System.out.println(pg_178870.solution(new int[]{1, 1, 1, 2, 3, 4, 5},5)); // expected : [6,6]
        System.out.println(pg_178870.solution(new int[]{2, 2, 2, 2, 2},6)); // expected : [0,2]
    }
}
