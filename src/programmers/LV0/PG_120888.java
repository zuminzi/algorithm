package programmers.LV0;

import java.util.*;
import java.util.stream.Collectors;

public class PG_120888 {
    // ~0.47ms, 87.2MB
    public String solution(String my_string) {
        String answer = "";
        Queue<Character> queue = new LinkedList<>();
        Set<Character> set = new HashSet<>();
        for(int i=0; i<my_string.length(); i++){
            char target = my_string.charAt(i);
            if(set.add(target)){
                queue.add(target);
            }
        }
        StringBuilder sb = new StringBuilder(answer);
        while(!queue.isEmpty()){
            if(queue.isEmpty()) break;
            sb.append(queue.poll());
        }
        return sb.toString();
    }

    // ~3.50ms, 76.6MB
    public String exam1(String my_string) {
        return my_string.chars()
                .mapToObj(Character::toString)
                .distinct()
                .collect(Collectors.joining());

        //return Arrays.stream(myString.split("")).distinct().collect(Collectors.joining());
    }

    // ~1.30ms, 75.6MB
    // LinkedHashSet
    public String exam2(String my_string) {
        String[] answer = my_string.split("");
        // LinkedHashSet에 split된 my_string 넣어서 바로 중복제거
        Set<String> set = new LinkedHashSet<String>(Arrays.asList(answer));

        // String.join(CharSequence delimiter, Iterable<? extends CharSequence> elements)
        // CharSequence 타입 자료구조의 각 요소를 가져와 첫번째인수(delimiter, 구분문자)로 요소를 바로 합칠 수 있음
        // 아래는 ""로 따로 구분자나 공백없이 바로 이어붙임
        return String.join("", set);
    }

     //~0.35ms, 75.1MB
    // String.contains()
    public String exam3_refactor(String my_string) {
        String answer = "";
        StringBuilder sb = new StringBuilder(answer);
        for(int i=0; i<my_string.length(); i++){
            if(!String.valueOf(sb).contains(String.valueOf(my_string.charAt(i)))){
                sb.append(my_string.charAt(i));
            }
        }
        return sb.toString();
    }

    // Top1
    // ~0.09ms, 72.6MB
    // indexOf는 찾는 문자 인덱스들 중 첫번째 인덱스를 반환하므로
    public String exam4(String my_string) {
        String answer = "";
        StringBuilder sb = new StringBuilder(answer);
        for(int i=0; i<my_string.length(); i++){
            if(i==my_string.indexOf(my_string.charAt(i)))
                sb.append(my_string.charAt(i));
        }

        return sb.toString();
    }

    // Top2
    // ~0.08ms, 89MB
    // character를 boolean 배열의 인덱스로 넣으면서 아스키코드(0~127,10진수)로 변환
    public String exam5(String my_string) {
        String answer = "";
        StringBuilder sb = new StringBuilder(answer);

        boolean[] askii = new boolean[128];

        for(int i=0; i<my_string.length(); i++){
            char c = my_string.charAt(i);
            if(!askii[c]){
                askii[c] = true;
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args){
        PG_120888 pg_120888 = new PG_120888();
        System.out.println(pg_120888.exam5("people")); // expected : peol
    }
}
