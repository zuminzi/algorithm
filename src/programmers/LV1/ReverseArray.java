package programmers.LV1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.IntStream;

import java.util.*;

public class ReverseArray {
    public List<Long> codeOfMine(long n) {
        // 각 자릿수 쪼개서 배열로
        long[] arrNum = Stream.of(String.valueOf(n).split(""))
                .mapToLong(Long::parseLong)
                .toArray();

        // Long배열을 List<Long>으로 변환
        /* Arrays.asList() 메서드는 primitive 타입을 Wrapper 클래스로 형변환해주지 않기 때문에
       primitive 타입의 배열은, Arrays.asList()로는 List로 변환할 수 없다
        */
        List<Long> reverseList = new ArrayList<>();
        for (long element : arrNum) {
            reverseList.add(element);
        }

        // 역순 정렬
        // 그 전에 정렬이 필요하다면 Collection.sort();
        Collections.reverse(reverseList);

        return reverseList;
    }

    /* StringBuilder 사용(+reverse) */
    // StringBuilder는 변경 가능한 문자열 객체를 생성
    public String exam(long n) {
        long[] arr = new StringBuilder().append(n).reverse().chars().mapToLong(Character::getNumericValue).toArray();
        // [J@3f99bd52으로 출력되는 현상 fix하기 위해 Arrays.toString으로 바꾸기 메서드 리턴 타입 String으로 변경
        return Arrays.toString(arr);
    }

    public String exam_(long n) {
        int[] answer;
        int count=0;
        long temp= n;
        while(temp !=0){
            count++;
            temp/=10;
        }
        answer = new int[count];
        for(int i = 0;i<count;i++){
            answer[i] =(int)(n%10); //5, 4, 3, 2, 1
            n/=10; //1234, 123, 12, 1, 0.1(루프문 완료)
        }

        return Arrays.toString(answer);
    }

    /* 시간복잡도 개선 */
    public long[] refactorCodeOfMine(long n) {
        long[] arrNum = Stream.of(String.valueOf(n).split(""))
                .mapToLong(Long::parseLong)
                .toArray();

        long[] reverseList = new long[arrNum.length];

        for(int i = 0;i<arrNum.length;i++){
            reverseList[i] =(int)(n%10);
            n/=10;
        }
        return reverseList;
    }

    public static void main(String[] args) {
        ReverseArray reverseArray = new ReverseArray();
        System.out.println(reverseArray.refactorCodeOfMine(12345)); // expected: [5, 4, 3, 2, 1]
    }

}
