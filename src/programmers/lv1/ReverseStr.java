package programmers.lv1;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReverseStr {
    /** Stream sorted() 활용
     * sorted() 디폴트는 오름차순
     * 학생들 점수 내림차순으로 정렬하기
     public List<Student> getStudentsOrderByMathScoreDesc(List<Student> students) {
                   return students.stream()
                  .sorted((a, b) -> b.mathScore() - a.mathScore())
                  .collect(Collectors.toList());
      }

     */
     /** ASCII Table
        System.out.println((int)'A'); // 65
        System.out.println((int)'Z'); // 90
        System.out.println((int)'a'); // 97
        System.out.println((int)'z'); // 122

      ** 숫자 CHAR(0~9)는 ASCII 코드 48부터 시작하므로 48을 빼주면 숫자형으로 변환 가능
        ex.
        char c = '5';
        int n = 0;
        n = c - '0';
      */
    public String solution(String s) {
        String answer = "";
        answer = Stream.of(s.split(""))
                .sorted(Collections.reverseOrder()) // == Comparator.reverseOrder()
                .collect(Collectors.joining()); // 입력 요소를 단일 문자열로 연결 // 인수로 (delimiter, prefix, suffix) 넣어줄 수도 있음
        return answer;
    }
    public static void main(String[] args){
        ReverseStr reverseStr = new ReverseStr();
        System.out.println(reverseStr.solution("Zbcdefg")); // expected : gfedcbZ
    }
}
