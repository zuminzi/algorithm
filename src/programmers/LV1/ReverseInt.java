package programmers.LV1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReverseInt {
    public Long codeOfMine(long n) {
        String result="";
        List<String>str_arr = Stream.of(String.valueOf(n).split("")).collect(Collectors.toList());

        Collections.sort(str_arr);
        Collections.reverse(str_arr);

        for(String temp : str_arr) {
            result = result.concat(temp);
        }
        return Long.parseLong(result);
    }

    /* String 배열 index [0]만 사용해서 바로 리턴 */
    public int exam1(int n){
        final String[] res = {""};
        Integer.toString(n).chars().sorted().forEach(c -> res[0] = Character.valueOf((char)c) + res[0]);
        return Integer.parseInt(res[0]);
    }

    /* StringBuilder 이용 */
    public long exam2(long n) {
        String[] list = String.valueOf(n).split("");
        Arrays.sort(list);

        StringBuilder sb = new StringBuilder();
        for (String aList : list) sb.append(aList); // StringBuilder의 append() 메서드 사용

        return Long.parseLong(sb.reverse().toString());
    }

    /* temp 이용하여 reverse */
    public int exam3(int n){
        char[] numbers = Integer.toString(n).toCharArray();
        String strSort = "";

        if(n <= 0) return 0;

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < i; j++) {
                if(numbers[i] - 48 > numbers[j] - 48) {
                    char temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
        strSort = new String(numbers);

        return Integer.parseInt(strSort);
    }

    public static void main(String[] args) {
        ReverseInt reverseInt = new ReverseInt();
        System.out.println(reverseInt.exam2(118372)); // expected: 873211
    }

    }

