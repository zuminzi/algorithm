package programmers.lv1;

public class SumOfArithmeticSequences {
    public long codeOfMine(int a, int b) {
        long sum=0;

        if(a>b){
            for(int i=0; i<=a-b; i++)
                sum += b+i;
        } else if(a==b){
            sum = a;
        } else {
            for(int i=0; i<=b-a; i++)
                sum += a+i;
        }
        return sum;
    }

    /* 등차수열의 합 공식 이용 */
    public long exam1(int a, int b) {
        return sumAtoB(Math.min(a, b), Math.max(b, a));
    }
    private long sumAtoB(long a, long b) {
        return (b - a + 1) * (a + b) / 2;
    }

    /* 조건문에서 int i!=0, 초기값:smaller부터 조건값:larger까지 */
    public long exam2(int a, int b){
        long answer=0;

        if(a<b) {
            for (int i = a; i <= b; i++)
                answer += i;
        } else {
                for(int i=b; i<=a;i++)
                    answer += i;
            }
        return answer;
    }

    /* exam2를 삼항조건식으로 */
    public long exam3(int a, int b) {
        long cnt = 0;
        for (int i = ((a < b) ? a : b); i <= ((a < b) ? b : a); i++)
            cnt += i;

        return cnt;
    }

    public static void main(String[] args) {
        SumOfArithmeticSequences sumOfArithmeticSequences = new SumOfArithmeticSequences();
        System.out.println(sumOfArithmeticSequences.codeOfMine( -10000000, 10000000)); // sum = 0
        System.out.println(sumOfArithmeticSequences.codeOfMine( -3, 3)); // sum = 0
    }
}
