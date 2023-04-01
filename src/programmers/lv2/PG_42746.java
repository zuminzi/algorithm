package programmers.lv2;

import java.util.*;

public class PG_42746 {
    // 두 인수 앞뒤로 붙인 후 큰 수 비교
    // ~284.56ms, 137MB
    public String codeOfMine(int[] numbers) {
        String answer = "";
        String[] arr = new String[numbers.length];
        int zeroCnt = 0;
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i] == 0){zeroCnt++;}
            arr[i] = String.valueOf(numbers[i]);
        }

        // Problem Point - return 타입은 String이지만, 가장 큰 수를 리턴하라고 했으므로 0000 대신 0 리턴 필요
        if(zeroCnt == numbers.length) return "0";

        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // return (o2 + o1).compareTo(o1 + o2); // 이렇게 한 줄로도 가능

                String str1 = o1 + o2;
                String str2 = o2 + o1;
                if (Integer.parseInt(str1) > Integer.parseInt(str2)) {
                    return -1;
                } else if (Integer.parseInt(str1) < Integer.parseInt(str2)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        //Arrays.sort(arr, (Comparator<String>) PG_42746::compare);

        StringBuilder sb = new StringBuilder(answer);
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }

//    private static int compare(String o1, String o2) {
//        String str1 = o1 + o2;
//        String str2 = o2 + o1;
//        if(Integer.parseInt(str1) > Integer.parseInt(str2)){
//            return -1;
//        } else if(Integer.parseInt(str1) < Integer.parseInt(str2))
//            return 1;
//        else {
//            return 0;
//        }
//    }

    // 구현 아이디어는 동일
    // Integer.compare로 비교 후 - 붙여서 내림차순 정렬 // Collections.sort는 기본적으로 오름차순 정렬이므로
    public String exam1(int[] numbers) {
        String answer = "";

        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < numbers.length; i++) {
            list.add(numbers[i]);
        }
        Collections.sort(list, (a, b) -> {
            String as = String.valueOf(a), bs = String.valueOf(b);
            return -Integer.compare(Integer.parseInt(as + bs), Integer.parseInt(bs + as));
        });
        StringBuilder sb = new StringBuilder();
        for(Integer i : list) {
            sb.append(i);
        }
        answer = sb.toString();
        if(answer.charAt(0) == '0') {
            return "0";
        }else {
            return answer;
        }
    }

    // ~2910.11ms, 399MB // the Worst Efficiency
    public String exam2(int[] numbers) {
        String answer = "";
        answer = Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .sorted((s1, s2) -> -s1.concat(s2).compareTo(s2.concat(s1)))
                .reduce("", (s1, s2) -> s1.equals("0") && s2.equals("0") ? "0" : s1.concat(s2));
        return answer;
    }

    public static void main(String[] args) {
        PG_42746 pg_42746 = new PG_42746();
        System.out.println(pg_42746.codeOfMine(new int[]{6, 10, 2})); // expected : "6210"
        System.out.println(pg_42746.codeOfMine(new int[]{3, 30, 34, 5, 9})); // expected : "9534330"
        System.out.println(pg_42746.codeOfMine(new int[]{12, 1213})); // expected : 121312
        System.out.println(pg_42746.codeOfMine(new int[]{12, 1210})); // expected: 121210
        System.out.println(pg_42746.codeOfMine(new int[]{212,21})); // expected : 21221
        System.out.println(pg_42746.codeOfMine(new int[]{0,0,0,0})); // expected: 0 // not 0000
    }
}