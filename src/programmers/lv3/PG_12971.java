package programmers.lv3;

// 단 스티커 한 장을 뜯어내면 양쪽으로 인접해있는 스티커는 찢어져서 사용할 수 없게 됩니다.
// 원형의 스티커 모양을 위해 배열의 첫 번째 원소와 마지막 원소가 서로 연결되어 있다고 간주합니다.
public class PG_12971 {
    public int solution(int sticker[]) {
        int n = sticker.length;
        if (n == 1) return sticker[0];
        if (n == 2) return Math.max(sticker[0], sticker[1]);

        int[] dp1 = new int[n]; // 첫 번째 스티커부터 뜯는 경우
        int[] dp2 = new int[n]; // 두 번째 스티커부터 뜯는 경우

        // 첫 번째 스티커 뜯는 경우
        dp1[0] = sticker[0];
        dp1[1] = dp1[0]; // ** 두번째 스티커를 뜯지 않으므로, dp1[1]은 추가로 점수를 쌓지 않고 그대로 dp1[0]이다

        // 두 번째 스티커 뜯는 경우
        dp2[0] = 0; // ** 첫번째 스티커를 뜯지 않으므로, dp2[0]은 점수를 쌓지 않고 0이다.
        dp2[1] = sticker[1];

        for (int i = 2; i < n; i++) { // i번째 스티커를 뜯을 때(dp[i-2] + sticker[i])와 i번째 스티커를 뜯지 않을 때(dp[i-1]) 비교하여 최대합 계산하기
            if (i != n - 1) {
                dp1[i] = Math.max(dp1[i - 2] + sticker[i], dp1[i - 1]); // 첫 번째 스티커 뜯는 경우
            }
            dp2[i] = Math.max(dp2[i - 2] + sticker[i], dp2[i - 1]); // 두 번째 스티커 뜯는 경우
        }

//        // 첫 번째 스티커 뜯는 경우
//        dp1[0] = sticker[0];
//        dp1[1] = sticker[0];
//        for (int i = 2; i < n - 1; i++) {
//            dp1[i] = Math.max(dp1[i - 2] + sticker[i], dp1[i - 1]);
//        }
//
//        // 두 번째 스티커 뜯는 경우
//        dp2[0] = 0;
//        dp2[1] = sticker[1];
//        for (int i = 2; i < n; i++) {
//            dp2[i] = Math.max(dp2[i - 2] + sticker[i], dp2[i - 1]);
//        }

        return Math.max(dp1[n - 2], dp2[n - 1]);
    }


    public static void main(String[] args){
        PG_12971 pg_12971 = new PG_12971();
        System.out.println(pg_12971.solution(new int[]{14, 6, 5, 11, 3, 9, 2, 10})); // expected : 36
        System.out.println(pg_12971.solution(new int[]{1, 3, 2, 5, 4})); // expected : 8
        System.out.println(pg_12971.solution(new int[]{4, 3, 2, 9, 4})); // expected : 13
    }
}
