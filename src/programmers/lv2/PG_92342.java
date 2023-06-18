package programmers.lv2;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/*
 * @param n 화살의 개수
 * @param  info 어피치가 맞힌 과녁 점수의 개수를 10점부터 0점까지 순서대로 담은 정수 배열
 * @return 라이언이 "가장 큰 점수 차이"로 우승하기 위해 n발의 화살을 어떤 과녁 점수에 맞혀야 하는지를 10점부터 0점까지 순서대로 담은 정수 배열
 * @return 만약, 라이언이 우승할 수 없는 경우(무조건 지거나 비기는 경우)는 [-1]
 * 만약, k(k는 1~10사이의 자연수)점을 어피치가 a발을 맞혔고 라이언이 b발을 맞혔을 경우 더 많은 화살을 k점에 맞힌 선수가 k 점을 가져갑니다.
 * 단, a = b일 경우는 어피치가 k점을 가져갑니다.
 * 또한 a = b = 0 인 경우, 즉, 라이언과 어피치 모두 k점에 단 하나의 화살도 맞히지 못한 경우는 어느 누구도 k점을 가져가지 않습니다.
 * 단, 최종 점수가 같을 경우 어피치를 우승자로 결정합니다.
 * 라이언이 "가장 큰 점수 차이"로 우승할 수 있는 방법이 여러 가지 일 경우, 가장 낮은 점수를 더 많이 맞힌 경우를 return 해주세요.
 * */
public class PG_92342 {
    private static int diff;
    private static int scoreRange;
    private static int[] apeachScore;
    private static PriorityQueue<int[]> pq;

    public int[] solution(int n, int[] info) {
        diff = 0;
        scoreRange = info.length;
        apeachScore = info;
        pq = new PriorityQueue<>((o1, o2) -> {
            for (int i = o1.length - 1; i >= 0; i--) {
                if (o1[i] > o2[i]) {
                    return -1;
                } else if (o1[i] < o2[i]) {
                    return 1;
                }
            }
            return 0;
        });
        int[] arr = IntStream.range(0, scoreRange)
                .map(i -> scoreRange - 1 - i)
                .toArray();

        combination(0, 0, new int[n], arr, n);

        if(pq.isEmpty()){
            return new int[]{-1};
        }
        return Arrays.copyOfRange(pq.peek(), 0, scoreRange);
    }

    // 중복 조합 (idx는 다음 대상을 선택할때 집합에서 탐색 시작할 인덱스)
    private static void combination(int cnt, int idx, int[] result, int[] arr, int r) {
        if (cnt == r) {
            int[] lionScore = new int[scoreRange];
            for (int i = 0; i < result.length; i++) {
                lionScore[scoreRange - 1 - result[i]]++;
            }

            int aScore = 0;
            int lScore = 0;
            for (int i = scoreRange - 1; i >= 0; i--) {
                if(apeachScore[i] == 0 && lionScore[i] == 0) continue;
                if (apeachScore[i] >= lionScore[i]) {
                    aScore += scoreRange - 1 - i;
                } else {
                    lScore += scoreRange - 1 - i;
                }
            }

            if (aScore < lScore) {
                if(diff <= lScore - aScore) {
                    int[] diffArr = new int[lionScore.length + 1];
                    for(int i=0; i<lionScore.length; i++){
                        diffArr[i] = lionScore[i];
                    }
                    diff = lScore - aScore;
                    diffArr[diffArr.length-1] = diff;
                    pq.add(diffArr);
                }
            }
            return;
        }

        for (int i = idx; i < scoreRange; i++) {
            result[cnt] = arr[i];
            combination(cnt + 1, i, result, arr, r);
        }
    }

        public static void main(String[] args){
        PG_92342 pg_92342 = new PG_92342();
        System.out.println(pg_92342.solution(5, new int[]{2,1,1,1,0,0,0,0,0,0,0})); // expected : [0,2,2,0,1,0,0,0,0,0,0]
        System.out.println(pg_92342.solution(1, new int[]{1,0,0,0,0,0,0,0,0,0,0})); // expected : [-1]
        System.out.println(pg_92342.solution(9, new int[]{0,0,1,2,0,1,1,1,1,1,1})); // expected : [1,1,2,0,1,2,2,0,0,0,0]
        }
}
