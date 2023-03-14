package programmers.LV2;

import java.util.*;

// 3진수(4,1,2 총 3가지로만 표현되므로 이는 3진수 나머지 0,1,2 활용가능)를 활용하여 각 자릿수 추출
public class PG_12899 {
    public String exam1(int n) {
    String[] num = {"4","1","2"};
    String answer = "";
    // 0.3이 3진수 계산에서 1로 나누어떨어지지 않도록
    // 한 자릿수씩 당겨서 3 Modular(나머지) 연산
    while (n >0){
        answer = num[n%3] + answer; // 이전 값 앞에 붙여주기 위해 answer += num[n%3] (X)
        n = (n-1) / 3;
    }
    return answer;
    }

    public String exam2(int n) {
        ArrayList<String> list = new ArrayList<>();
        final String[] DIGIT = { "4", "1", "2" };

        while (n != 0) {
            int index = n % 3;
            n /= 3;

            // 인덱스가 0일 경우
            // = 3의 배수일 경우
            // 자릿수가 하나 올라가기 때문에 이를 보정해서 -1 처리 필요
            if (index == 0) {
                n--;
            }
            list.add(DIGIT[index]);
        }
        Collections.reverse(list);
        return list.stream().reduce(String::concat).get();
    }

    static int[] arr;
    static List<String> list;
    static Set<String> set;
    public String fail_sol(int n) {
        set = new HashSet<>();
        list = new ArrayList<>();
        arr = new int[]{1,2,4};
        int num = 11;
        for(int i=1; i<num; i++){
            comb(0, i,new int[i], 0, num);
        }
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1.length() == s2.length()) {
                    return s1.compareTo(s2);
                } else if(s1.length() > s2.length()){
                    return 1;
                } else{
                    return -1;
                }
            }
        }
    );
        return list.get(n-1);
    }

    private static void comb(int cnt, int r, int[] output, int idx, int n) {
        if (cnt == r) {
            String sum = "";
           for(int i=0; i<output.length; i++){
               sum += output[i];
           }
           if(set.add(sum)) list.add(sum);
           return;
        }
        for (int i = idx; i <n ; i++) {
            output[cnt] = arr[i%3];
            comb(cnt + 1, r,output, i, n);
        }
    }

    public static void main(String[] args){
        PG_12899 pg_12899 = new PG_12899();
        System.out.println(pg_12899.exam2(10));
    }

}
