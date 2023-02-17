package programmers.LV2;

public class PG_12985 {
    // Accuracy Test ~0.05ms, 80MB
    // When for(;i<n;) Accuracy Test ~0.09ms, 74.4MB
    public int codeOfMine(int n, int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a,b);
        int cnt = 0;

        // Problem Point 1 - i < Math.ceil(Math.log(n)) (X)
        for (int i=0; i< Math.sqrt(n); i++) {

            // Problem Point 2 - 예외처리
            if (max%2 == 0 && max - 1 == min) {
                cnt++;
                return cnt;
            }

            if (max % 2 != 0) {
                max += 1;
            }

            if (min % 2 != 0) {
                min += 1;
            }

            max /= 2;
            min /= 2;
            cnt++;

            if (max <= 1) {break;}
    }
        return cnt;
    }

    // (a-1) XOR (b-1)
    // a,b에 각 -1 처리한 이유: 0(000), 1(001) / 2(010), 3(011) / 4(100), 5(101) / 6(110), 7(111)
    // 1단계에서 만나는 참가자들은 끝에 한자리만 다르고, 2단계에서 만나는 참가자들은 두 자리가 다르다는 것 .. * 반복을 이용하기 위해
    public int exam1(int n, int a, int b) {
        return Integer.toBinaryString((a-1)^(b-1)).length();
    }

    // Accuracy Test ~0.04ms, 74MB
    public int exam2(int n, int a, int b)
    {
        int round = 0;
        while(a != b)
        {
            // +(n%2)의 의미? 홀수일 경우 +1
            // 나머지도 더해주므로 min 값이 1에 도달하면, 0으로 갈 일 없이 계속 1
            a = a/2 + a%2;
            b = b/2 + b%2;
            round++;
        }
        return round;
    }
    public static void main(String[] args){
        PG_12985 pg_12985 = new PG_12985();
        System.out.println(pg_12985.exam2(8,4,7)); // expected : 3
        System.out.println(pg_12985.exam2(16,9,12)); // expected : 2
        System.out.println(pg_12985.exam2(16,4,5)); // expected : 3

    }
}
