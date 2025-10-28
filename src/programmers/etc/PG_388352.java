package programmers.etc;

public class PG_388352 {
    // DFS로 조합 생성 후 각 q와 비교
    private int combination;
    public int solution(int n, int[][] q, int[] ans) {
        combination = 0;
        DFS(1, n, 0, new int[5], q, ans, new boolean[n+1]); // 1~n까지 수이므로 n+1 크기 사용
        return combination;
    }

    private void DFS(int start, int n, int depth, int[] output, int[][] q, int[] ans, boolean[] used) {
        if(depth == 5) {
            // 2. 교집합 원소 개수 카운트
            // 2-[방법1]. 비트마스크 활용
            int combinationMask = 0;
            for (int num : output) {
                // << (왼쪽시프트, 왼쪽으로 num만큼 이동, 오른쪽에는 0채움 = 2의 num제곱)
                // a |= b (OR 대입 연산자, a = a | b, a와 b의 비트 중 하나라도 1이면 1)
                combinationMask |= (1 << num);
            }
            for (int i=0; i<q.length; i++) {
                int qMask = 0;
                for (int qNum : q[i]) {
                    qMask |= (1 << qNum); //
                }
                // 합집합: A | B, 교집합: A & B, 차집합: A & ~B, XOR(A와 B중 하나에만 포함된 원소): A ^ B
                int matchCount = Integer.bitCount(combinationMask & qMask);
                /* 2-[방법2]. 투 포인터 활용
                int k = 0, j = 0, matchCount = 0;
                while (k < q[i].length && j < output.length) {
                    if (q[i][k] == output[j]) {
                        matchCount++;
                        k++;
                        j++;
                    } else if (q[i][k] < output[j]) {
                        k++;
                    } else {
                        j++;
                    }
                }
                 */
                if (matchCount != ans[i]) {
                    return;
                }
            }
            combination++;
            return;
        }

        // 1. 오름차순 조합이므로 start부터 n까지 반복
        for (int i=start; i<=n; i++) {
            if(!used[i]) {
                used[i] = true;
                output[depth] = i;
                DFS(i+1, n, depth+1, output, q, ans, used);
                used[i] = false;
            }
        }
    }

        public static void main(String[] args) {
        PG_388352 pg388352 = new PG_388352();
        // expected : 3
        System.out.println(pg388352.solution(
                10,
                new int[][] {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {3, 7, 8, 9, 10}, {2, 5, 7, 9, 10}, {3, 4, 5, 6, 7}},
                new int[] {2, 3, 4, 3, 3}
        ));
        // expected : 5
        System.out.println(pg388352.solution(
                15,
                new int[][] {{2, 3, 9, 12, 13}, {1, 4, 6, 7, 9}, {1, 2, 8, 10, 12}, {6, 7, 11, 13, 15}, {1, 4, 10, 11, 14}},
                new int[] {2, 1, 3, 0, 1}
        ));
    }
}
