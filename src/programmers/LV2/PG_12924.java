package programmers.LV2;

public class PG_12924 {
    // Sliding Window(고정 사이즈의 윈도우가 이동하면서 윈도우 내에 있는 데이터를 이용)
    // Accuracy Test ~0.13ms, 83.8MB, Efficiency Test ~1.65ms, 51.6MB
    public int codeOfMine(int n){
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

    // Accuracy Test ~0.04ms, 82.4MB, ~Efficiency Test ~0.03ms, 52.3MB
    // 등차수열 공식
    public int codeOfMine_refactor(int n) {
        int answer = 0;
        for(int i = 1; i<=n; i++){
            int a = ( n/i) -((i-1)/2); // 첫 항
            if( a <= 0 ) break;

            // double 이유
            // double(실수) 변수여야 소수점까지 계산하여, int a값(소수점은 floor처리된 a값)으로 S(n)이 올바르게 나오는지 확인가능하기 때문
            double temp = ((2*a)+(i-1))/(double)2;
            if(i*temp == n) // i*temp == 등차수열의 합 S(n)이 n과 똑같다면
                answer++;
        }
        return answer;
    }

    // Accuracy Test ~0.04ms, 75.2MB, Efficiency Test ~0.11ms, 52.3MB
    // 주어진 자연수를 연속된 자연수의 합으로 표현하는 방법의 수 = 주어진 수의 홀수 약수 개수
    public int exam(int num) {
        int answer = 0;
        for (int i = 1; i <= num; i += 2)
            if (num % i == 0)
                answer++;

        return answer;
    }

    // Accuracy Test ~0.05ms, 72.4MB, Efficiency Test ~0.02ms, 52.5MB
    // 등차수열의 공식
    // n번째 항 = a + (n-1)d
    // 합 공식 1 = n(a+l)/2 , l = 마지막항
    // 합 공식 2 = n(2a+(n-1)d)/2
    // d=1, i=1~n
    public int exam2(int n) {
        int answer = 0;

        for(int i = 1; n-((i*(i-1))/2) > 0; i++){

            if((n-((i*(i-1))/2))% i == 0){
                answer++;
            }

        }
        return answer;
    }

    public static void main(String[] args){
        PG_12924 pg_12924 = new PG_12924();
        System.out.println(pg_12924.codeOfMine_refactor(15));
    }
}
