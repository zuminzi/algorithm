package programmers.lv2;

import java.util.Stack;

public class PG_121685 {
    static String[] genotypeArr = {"RR", "Rr", "Rr", "rr"};
    /**
     * 규칙을 찾아 주어진 세대와 위치에 따라 완두콩의 형질을 계산하는 알고리즘
     */
    public String[] exam1(int[][] queries) {
        int len = queries.length;

        String result[] = new String[len];
        for(int i = 0; i < len; i++) {
            int gen = queries[i][0];
            int num = queries[i][1] - 1;
            Stack<Integer> stk = new Stack<>();
            if(gen == 1) {
                result[i] = "Rr";
            } else {
                while(gen > 1) {
                    gen--;
                    stk.push(num % 4);
                    num /= 4;
                }
                boolean flag = false;
                while(!stk.isEmpty()) {
                    int pop = stk.pop();
                    if(pop == 0 || pop == 3) {
                        result[i] = genotypeArr[pop];
                        flag = true;
                        break;
                    }
                }
                if(!flag) {
                    result[i] = "Rr";
                }

            }
        }
        return result;
    }

    /**
     * 형질을 적용할 수 있는 세대까지 내려가는 재귀 알고리즘
     */
    public String[] exam2(int[][] queries) {
        String[] result = new String[queries.length];
        for(int i = 0; i < queries.length; i++)
            result[i] = queries[i][0] == 1 ? "Rr" : recursive(queries[i][0], queries[i][1]);
        return result;
    }

    private String recursive(int n, int p) {
        int currentTotal = (int) Math.pow(4, n - 1); // 현재 세대의 총 개체 수
        // p가 0보다 작거나 같으면 "RR" 형질
        if (p <= 0) return "RR";

        // p가 현재 세대의 3/4 이상이면 "rr" 형질 반환
        if (p > (currentTotal / 4) * 3) return "rr";

        // 2세대인 경우 "Rr" 형질 반환
        if (n == 2) return "Rr";

        // 재귀 호출로 세대 내려가서 형질 결정
        if (p > (currentTotal / 4) && p <= (currentTotal / 2)) return recursive(n - 1, p - (currentTotal / 4));
        else return recursive(n - 1, p - (currentTotal / 2));
    }


    // Hidden TC Memory Limit Exceeded
    public String[] fail(int[][] queries) {
        String[] answer = new String[queries.length];
        int maxRow = 0;
        for(int i=0; i<queries.length; i++){
            maxRow = Math.max(maxRow, queries[i][0]);
        }
       String[][] dp = new String[maxRow + 1][(int) Math.pow(4, maxRow - 1) + 1];
        for(int i=1; i<=maxRow; i++){
            if(i == 1){
                dp[1][1] = "Rr";
                continue;
            }
            for(int j=1; j<=Math.pow(4,i-1); j++){
                int parentRow = i - 1;
                int remainder = j % 4;
                int divisor= j / 4;
                int parentCol = remainder == 0 ? divisor : divisor + 1;
                String parent = dp[parentRow][parentCol];
                switch(parent){
                    case "RR":
                        dp[i][j] = "RR";
                        break;
                    case "Rr":
                        if(j % 4 == 1){
                            dp[i][j] = "RR";
                        } else if(j % 4 == 2 || j % 4 == 3){
                            dp[i][j] = "Rr";
                        } else {
                            dp[i][j] = "rr";
                        }
                        break;
                    case "rr":
                        dp[i][j] = "rr";
                        break;
                }
            }
        }
        for(int i=0; i<answer.length; i++){
            answer[i] = dp[queries[i][0]][queries[i][1]];
        }
        return answer;

    }

    public static void main(String[] args){
        PG_121685 pg_121685 = new PG_121685();
        System.out.println(pg_121685.exam2(new int[][]{{6, 20}, {6, 689}})); // expected : [RR, rr]
    }
}
