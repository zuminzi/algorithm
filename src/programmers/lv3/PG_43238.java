package programmers.lv3;

import java.util.Arrays;

public class PG_43238 {
    // f(T) = 시간 T 동안 심사 가능한 총 인원 수  → 단조 증가 함수
    // f(T) ≥ n이 되는 가장 작은 T를 찾아라
    // 즉, “가능해지는 전환점”을 이분 탐색으로 찾기
    public long solution(int n, int[] times) {
        // 1. 최소/최대 시간 구하기
        long left = 1;
        // 최대 시간: 가장 느린 심사관이 n명을 모두 처리할 때
        long right = (long) Arrays.stream(times).max().getAsInt() * n;

        // 2. 탐색 전 최댓값으로 해 초기화
        long answer = right;

        // 3. 탐색 범위가 남아있는 동안 반복 (mid = left = right 인 케이스가 있을 수 있으므로 <=)
        while (left <= right) {
            long mid = left + (right - left) / 2;
            long people = 0;
            // 4. 각 심사관별로 mid 시간(임의로 잡은 총 시간) 동안 처리할 수 있는 사람 수 계산
            for (int i=0; i<times.length; i++) {
                people += mid / times[i]; // 사람 수 → 정수 내림
            }

            // 5. 가능/불가능 경계 판단
            if (people >= n) {
                answer = mid;
                // [케이스 5-1] 감소 가능한지 탐색 (right 갱신)
                right = mid - 1;
            } else  {
                // [케이스 5-2] 증가 가능한지 탐색 (left 갱신)
                left = mid + 1;
            }
        }
        return answer;
    }
}
