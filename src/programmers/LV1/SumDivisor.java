package programmers.LV1;

public class SumDivisor {
    public int codeOfMine(int n) {
        int total = 0;
        for (int i=1;i<=n;i++){
            if(n%i==0)
                total += i;
        }
        return total;
    }

    public int refactor(int n) {
        int total = 0;
        for(int i = 1; i <= n/2; i++){ // 약수는 *자신 제외* n/2 보다 클 수 없기 때문에 절반값까지만 체크
            if(n%i == 0)
                total += i;
        }
        total+=n; // ** 자기 자신까지 total에 넣기
        return total;
    }

    public int refactor_(int n) {
        int total = 0;
        for (int i=1;i<=Math.sqrt(n);i++){ // 모든 약수는 짝이 있으며 그 짝의 작은 값은 자신의 제곱근을 초과할 수 없다
            if(n%i==0) {
                // 약수의 합을 구하기 위해서는 제곱근 이하의 값(1, 2, 4)만 구하면 되고 해당 값을 구하면 나머지(16, 8, 4)를 구할 수 있음
                total += i; // 1, 2, 4

                if(n/i!=i) // 4처럼 n의 제곱근은 중복으로 들어가므로 중복제거하기 위해 조건문
                    total += n / i; // 16, 8, 4(중복이라 제거)
            }
        }
        return total;
    }

    public static void main(String[] args) {
        SumDivisor sumDivisor = new SumDivisor();
        System.out.println(sumDivisor.refactor_(16)); // expected: 31
    }
}
