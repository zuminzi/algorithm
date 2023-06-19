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
    // ~36.51ms, 99.9MB
    private static int diff;
    private static int scoreRange;
    private static int[] apeachScore;
    private static PriorityQueue<int[]> pq;

    private int[] solution(int n, int[] info) {
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

        if (pq.isEmpty()) {
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
                if (apeachScore[i] == 0 && lionScore[i] == 0) continue;
                if (apeachScore[i] >= lionScore[i]) {
                    aScore += scoreRange - 1 - i;
                } else {
                    lScore += scoreRange - 1 - i;
                }
            }

            if (aScore < lScore) {
                if (diff <= lScore - aScore) {
                    int[] diffArr = new int[lionScore.length + 1];
                    for (int i = 0; i < lionScore.length; i++) {
                        diffArr[i] = lionScore[i];
                    }
                    diff = lScore - aScore;
                    diffArr[diffArr.length - 1] = diff;
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

    // ~3.82ms, 73MB
    // 비트마스크 알고리즘 : 비트(이진수 0 또는 1)를 활용한 풀이
    // Integer.bitCount() -> 정수를 매개변수로 넣었을때 그 정수의 1 의 개수를 반환
    private int max, ans[], apeach[];

    private int[] exam(int n, int[] info) {
        apeach = info;
        // 라이언은 어떤 점수 x에 대하여 x점을 얻거나 0점을 얻는 두가지 경우만 고려해주면 된다. [라이언이 얻을 수 있는 점수 조합의 수]
        // n(화살 개수)는 1부터 10까지이므로, 1(00000000001)부터 2의 11승(11111111111)까지 총 2048개의 케이스를 탐색하면 된다.
        for (int i = 1; i < 1 << 11; i++) {
            if (Integer.bitCount(i) <= n) {
                find(n, i);
            }
        }

        // 최대 점수가 0인 경우 조건대로 -1 리턴, 아니면 ans 배열 리턴
        return max == 0 ? new int[]{-1} : ans;
    }

    // 매개변수 cur은 라이언이 얻을 수 있는 점수조합(비트마스크)의 10진수 표현
    private void find(int n, int cur) {
        int score = 0, state[] = new int[11];
        for (int i = 1; i <= 10; i++) {
            // 연산 순위
            // 1. 1 << i [비트연산 - 시프트 연산]
            // 2. cur & (1 << i) [비트연산 - AND 연산]
            // 3. (cur & (1 << i)) > 0 [논리연산] // & 연산 결과가 0보다 크려면, 두 숫자 사이의 모든 비트 위치에서 1이 동시에 존재해야 함
            int a = 1 << i;
            int b = cur & a;
            if (b > 0) { // 1점~10점 루프를 돌면서 해당 점수를 택한 것인지 판단
                n -= state[10 - i] = apeach[10 - i] + 1; // apeach 배열의 화살 개수보다 1개 많게 state 배열 업데이트 후, 사용 화살 수만큼 n 감소
                if (n < 0) return;
                score += i; // 점수 업데이트
            } else if (apeach[10 - i] > 0) {
                score -= i;
            }
        }
        // 점수 비교하여 점수 차이가 가장 클 시, max 갱신
        if (score > 0) {
            state[10] = n; // line 120에서 apeach보다 1개 많게 화살 사용하므로, 항상 화살을 남김없이 모두 사용하지 않음. 따라서 화살을 모두 사용할 수 있도록 제일 작은 0점에 화살 사용
            if (max < score) {
                max = score;
                ans = state;
            // 라이언이 "가장 큰 점수 차이"로 우승할 수 있는 방법이 여러 가지 일 경우, 가장 낮은 점수를 더 많이 맞힌 경우를 return
            } else if (max == score) {
                for (int i = 10; i >= 0; i--) {
                    if (ans[i] != state[i]) {
                        if (state[i] > ans[i]) {
                            ans = state;
                        }
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        PG_92342 pg_92342 = new PG_92342();
        System.out.println(pg_92342.exam(5, new int[]{2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0})); // expected : [0,2,2,0,1,0,0,0,0,0,0]
        System.out.println(pg_92342.exam(1, new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})); // expected : [-1]
        System.out.println(pg_92342.exam(9, new int[]{0, 0, 1, 2, 0, 1, 1, 1, 1, 1, 1})); // expected : [1,1,2,0,1,2,2,0,0,0,0]
    }
}
