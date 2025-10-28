package programmers.etc;

public class PG_389479 {
    public int mine(int[] players, int m, int k) {
        int answer = 0;
        int[] arr = new int[24];
        for (int i = 0; i < players.length; i++) {
            if (players[i] / m > arr[i]) {
                int expansion = (players[i] / m) - arr[i];
                answer += expansion;
                for (int j = i + 1; j < i + 1 + k - 1; j++) {
                    if (j >= players.length) break;
                    arr[j] += expansion;
                }
            }
        }
        return answer;
    }
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        int[] install = new int[players.length]; // k 시간 전 서버 증설 확인용
        int active = 0; //  [이전 시간 서버 활성화 수]와 [k 시간 전 서버 증설 수]를 이용해 현재 활성화된 서버 수 계산
        for(int i=0; i<players.length; i++){
            // 독립 조건 1. 서버 유지 기간 종료
            if (i >= k) {
                active -= install[i-k]; // 슬라이딩 윈도우
            }
            // 독립 조건 2. 서버 증설 필요
            if(players[i] / m > active) {
                int add = (players[i] / m) - active;
                active += add;
                answer += add;
                install[i] = add;
            }
        }
        return answer;
    }
    public  static void main(String[] args) {

        PG_389479 solution = new PG_389479();
        System.out.println(solution.solution(new int[]{0, 2, 3, 3, 1, 2, 0, 0, 0, 0, 4, 2, 0, 6, 0, 4, 2, 13, 3, 5, 10, 0, 1, 5},
                3,
                5)
        );
        System.out.println(solution.solution(new int[]{0, 0, 0, 10, 0, 12, 0, 15, 0, 1, 0, 1, 0, 0, 0, 5, 0, 0, 11, 0, 8, 0, 0, 0},
                5,
                1)
        );
        System.out.println(solution.solution(new int[]{0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 5, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                1,
                1)
        );
    }
}
