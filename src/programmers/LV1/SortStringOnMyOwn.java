package programmers.LV1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

    /** Comparator.comparing()을 이용한 다중 조건 정렬

     - compare
     - comparing(keyExtractor, keyComparator)
     - keyExtractor : 정렬 key를 뽑아내는 함수적 인터페이스 (the function used to extract the sort key)
     - keyComparator : 정렬 key를 비교하기 위한 함수적 인터페이스 (the Comparator used to compare the sort key)
     */
    public String[] exam1(String[] strings, int n){
        // 1. 사전순 오름차순으로 먼저 정렬
        Arrays.sort(strings);
        // 2. n번째 인덱스를 기준으로 정렬
        Arrays.sort(strings, Comparator.comparing(s -> s.substring(n,n+1)));
        return strings;
    }

    public String exam1_refactor(String[] strings, int n){
        // 1. 사전순 오름차순으로 먼저 정렬
        Arrays.sort(strings);
        // 2. n번째 인덱스를 기준으로 정렬
        Arrays.sort(strings, Comparator.comparing(s -> s.charAt(n)));
        return Arrays.toString(strings);
    }

    /* Comparator.compare() Override */
    public String[] exam2(String[] strings, int n) {
        Arrays.sort(strings, new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                if(s1.charAt(n) > s2.charAt(n)) return 1;
                else if(s1.charAt(n) == s2.charAt(n)) return s1.compareTo(s2);
                else if(s1.charAt(n) < s2.charAt(n)) return -1;
                else return 0;
            }
        });
        return strings;
    }

    public String[] exam3(String[] strings, int n) {
        List<String> list = Arrays.asList(strings);
        list.sort((a, b) -> {
            int result = (a.split(""))[n].compareTo((b.split(""))[n]);
            if(result == 0)
                return a.compareTo(b);
            return result;
        });
        return list.toArray(new String[0]);
    }

    public String[] exam4(String[] strings, int n) {
        int l = strings.length;
        for(int i=0;i<l;i+=1){
            strings[i] = strings[i].substring(n,n+1)+strings[i];
        }
        Arrays.sort(strings);
        for(int i=0;i<l;i+=1){
            strings[i] = strings[i].substring(1);
        }
        return strings;
    }

    public static void main(String[] args){
        SortStringOnMyOwn sortStringOnMyOwn = new SortStringOnMyOwn();
        String[] arr1 = {"sun", "bed", "car"};
        String[] arr2 = {"abce", "abcd", "cdx"};
        System.out.println(sortStringOnMyOwn.codeOfMine(arr1,1)); // expected : ["car", "bed", "sun"]
        System.out.println(sortStringOnMyOwn.codeOfMine(arr2,2)); // expected : ["abcd", "abce", "cdx"]
    }
}
