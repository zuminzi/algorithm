package programmers.lv3;

public class PG_12979 {
    /**
     * @param n 아파트의 개수
     * @param stations 현재 기지국이 설치된 아파트의 번호가 담긴 배열
     * @param w 전파의 도달 거리
     * @return 모든 아파트에 전파를 전달하기 위해 증설해야 할 기지국 개수의 최솟값
     - Accuracy Test ~0.03ms, 79.3MB, Efficiency Test ~0.92ms, 53.1MB
     - 1) 1개만 증설이 필요한 범위인지 2) 증설이 필요 없는 범위인지 3) 1개 이상 증설이 필요한 범위인지 판단(몫과 나머지 활용하여 증설 개수 카운팅)
     */
    public int solution(int n, int[] stations, int w) {
        int answer = 0;
        int range = w * 2 + 1; // 전파 범위 // + 1은 기지국
        int ptr = 0;
        int divisor = 0;
        int remainder = 0;
        int end = 0;

        for (int i = 0; i < stations.length; i++) {

            if (i == 0) {
                // 기지국 이전 아파트 처리
                // CASE 1 or 2
                if (stations[i] - range < 1) { // 시작점 1과 비교
                    // problem point - 위 조건을 만족하지만 증설하지 않는 경우도 고려
                    if(stations[i] - w > 1) {
                        answer++;
                    }
                // CASE 3
                } else {
                    ptr = stations[i] - w - 1;
                    divisor = ptr / range;
                    remainder = ptr % range;
                    answer += divisor;
                    if (remainder != 0) {
                        answer++;
                    }
                }
            }

            // 기지국 이후 아파트 처리
            // CASE 1 or 2
            end = i != stations.length - 1 ? stations[i + 1] - w - 1 : n;
            if (stations[i] + range > end) {
                // problem point - 위 조건을 만족하지만 증설하지 않는 경우도 고려
                if(stations[i] + w < end) {
                    answer++;
                }
            // CASE 3
            } else {
                ptr = stations[i] + w + 1;
                divisor = (end - ptr + 1) / range;
                remainder = (end - ptr + 1) % range;
                answer += divisor;
                if (remainder != 0) {
                    answer++;
                }
            }
        }

        return answer;
    }

    public static void main (String[] args) {
        PG_12979 pg_12979 = new PG_12979();
        System.out.println(pg_12979.solution(11, new int[]{4, 11}, 1)); // expected : 3
        System.out.println(pg_12979.solution(16, new int[]{9}, 2)); // expected : 3
        System.out.println(pg_12979.solution(13, new int[]{3, 7, 11}, 1)); // expected : 4
        System.out.println(pg_12979.solution(5, new int[]{3}, 2)); // expected : 0
        System.out.println(pg_12979.solution(6, new int[]{3}, 2)); // expected : 1
        System.out.println(pg_12979.solution(16, new int[]{1, 16}, 2)); // expected : 2
        System.out.println(pg_12979.solution(6, new int[]{4}, 2)); // expected : 1
        System.out.println(pg_12979.solution(11, new int[]{1,4}, 1)); // expected : 2
        System.out.println(pg_12979.solution(11, new int[]{1, 5}, 1)); // expected : 3
        System.out.println(pg_12979.solution(200000000, new int[]{100000000},5)); // expected : 18181818
    }
}
