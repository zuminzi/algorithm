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
    public int exam1(int n, int a, int b) {
        return Integer.toBinaryString((a-1)^(b-1)).length();
    }

    public static void main(String[] args){
        PG_12985 pg_12985 = new PG_12985();
        System.out.println(pg_12985.exam1(8,4,7)); // expected : 3
        System.out.println(pg_12985.exam1(16,9,12)); // expected : 2
        System.out.println(pg_12985.exam1(16,4,5)); // expected : 3

    }
}
