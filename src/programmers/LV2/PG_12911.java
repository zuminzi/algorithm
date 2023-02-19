package programmers.LV2;

public class PG_12911 {
    public int solution(int n) {
        int answer = 0;
        int cnt = 0;
        String binaryString = Integer.toBinaryString(n);

        for(int i=binaryString.length() - 1; i >= 0; i--){
            if(binaryString.charAt(i) == '1'){
                cnt++;
            }
        }

        for(int i=n+1; i<=1000000;i++){
            int cntOne = 0;
            String comparator = Integer.toBinaryString(i);
            for(int k=0; k<comparator.length(); k++){
                if(comparator.charAt(k) == '1'){
                    cntOne++;
                }
            }
            if(cnt == cntOne){
                answer = i;
                break;
            }
        }
        return answer;
    }

    // 45/100, out of time
    static int nearest;
    public int fail_sol(int n) {
        nearest = 0;
        // 10진수 -> 2진수로 변환
        String binaryString = Integer.toBinaryString(n);
        int length = binaryString.length();
        int[] binaryArray = new int[length];
        for(int i=0; i<length; i++){
            binaryArray[i] = binaryString.charAt(i) - '0';
        }
        boolean[] check = new boolean[length];
        int[] output = new int[length];
        nearest = permutation(0, length, n, binaryArray, check, output);

        if(nearest == 0){
            int[] nextBinaryArray = new int[length+1];
            boolean[] nextCheck = new boolean[length+1];
            int[] nextOutput = new int[length+1];
            for(int i=0; i<binaryArray.length; i++){
                nextBinaryArray[i] = binaryArray[i];
            }
            nextBinaryArray[length] = 0;
            nearest = permutation(0, length+1, n, nextBinaryArray, nextCheck, nextOutput);
        }
        return nearest;
    }

    static int permutation(int start, int n, int comparator, int[] binaryArr, boolean[] check, int[] output) {
        if (start == n) {
            return compare(output, comparator);
        } else {
            for (int i = 0; i < n; i++) {
                if (!check[i]) {
                    check[i] = true;
                    output[start] = binaryArr[i];
                    permutation(start + 1, n, comparator, binaryArr, check, output);
                    check[i] = false;
                }
            }
        }

        return nearest;
    }

    static int compare(int[] output, int num) {
        String sum = "";
        for(int i : output){
            sum += i;
        }
        //System.out.println(sum);
        int temp = Integer.parseInt(sum,2);
        //System.out.println(temp);
        if(temp > num) {
            if(nearest == 0){
                nearest = temp;
            } else {
                if ((temp - num) < (nearest - num)) {
                    nearest = temp;
                }
            }
        }
        return nearest;
    }

    public static void main(String[] args){
        PG_12911 pg_12911 = new PG_12911();
        System.out.println(pg_12911.solution(78));
        System.out.println(pg_12911.solution(15));

    }
}
