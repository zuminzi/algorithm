package programmers.LV1;

import java.util.Arrays;

public class SortStringOnMyOwn {
    public String codeOfMine(String[] strings, int n) {
        // n번째 인덱스 기준으로 먼저 사전순 비교
        // n번째 인덱스가 같다면, 전체 String 사전순 비교

        for(int i = 0; i < strings.length-1; i++){

            // 1. i+1부터 끝까지 i번째 요소와 비교 (n번째 인덱스 기준으로)
            for(int j = i+1; j < strings.length; j++) {
                // 1-1. 앞의 n번째 인덱스가 더 크다면 자리 교체
                if (strings[i].charAt(n) > strings[j].charAt(n)) {
                    String temp = strings[i];
                    strings[i] = strings[j];
                    strings[j] = temp;
                }

                // 1-2. n번째 인덱스가 같을 때
                if(strings[i].charAt(n) == strings[j].charAt(n)){
                        // 1-2-a. 사전순으로 비교했을 때 뒤에 있는 String이 더 앞에 있다면 자리 교체
                        if(strings[i].compareTo(strings[j]) > 0){
                         String temp = strings[i];
                         strings[i] = strings[j];
                         strings[j] = temp;
                     }
                 }
             }
            }

        // 2. 리턴
        return Arrays.toString(strings);
    }

    public static void main(String[] args){
        SortStringOnMyOwn sortStringOnMyOwn = new SortStringOnMyOwn();
        String[] arr1 = {"sun", "bed", "car"};
        String[] arr2 = {"abce", "abcd", "cdx"};
        System.out.println(sortStringOnMyOwn.codeOfMine(arr1,1)); // expected : ["car", "bed", "sun"]
        System.out.println(sortStringOnMyOwn.codeOfMine(arr2,2)); // expected : ["abcd", "abce", "cdx"]
    }
}
