package programmers.LV2;

public class PG_12924 {
    // Sliding Window(고정 사이즈의 윈도우가 이동하면서 윈도우 내에 있는 데이터를 이용)
    // Accuracy Test ~0.13ms, 83.8MB, Efficiency Test ~1.65ms, 51.6MB
    public int solution(int n){
        int a = 1;
        int cnt = 1; // 자기 자신
        int sum = 0;
        for(;a<n-1;a++) {
            sum += a;
            for (int b= a+1; b < n; b++) {
                sum += b;
                // Problem  Point 1 - break 조건
                if(sum > n) {
                    // Problem Point 2 - b가 증가하는 for문을 끝내기 전에(break) sum 초기화
                    sum = 0;
                    break;
                } else if (sum == n) {
                    cnt++;
                    // Problem Point 2 - b가 증가하는 for문을 끝내기 전에(break) sum 초기화
                    sum = 0;
                    break;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args){
        PG_12924 pg_12924 = new PG_12924();
        System.out.println(pg_12924.solution(15));
    }
}
