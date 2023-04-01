package programmers.lv1;

import java.util.*;

public class WordToInt {
    public int solution(String s) {
        String[] nums = {"zero","one","two","three","four","five","six","seven","eight","nine"};
        int idx = 0;
        String str = "";
        String prev = "";
        String next = "";

        if(Arrays.stream(nums).parallel().anyMatch(s::contains)){
            for(int i=0; i<nums.length; i++){
                while(s.contains(nums[i])) { // num[i]에 해당하는 문자열 *모두* replace-> if문(x) while문(o)
                    idx = s.indexOf(nums[i]);
                    prev = s.substring(0,idx);
                    next = s.substring(idx+nums[i].length(),s.length()-0);
                    str = String.valueOf(i);
                    s = prev + str + next;
                }
            }
        }
        return Integer.parseInt(s);
        }

    /* strArr 요소들이 각 index의 영단어라는 것을 이용 */
    public int exam(String s) {
        String[] strArr = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for(int i = 0; i < strArr.length; i++) {
            s = s.replaceAll(strArr[i], Integer.toString(i));
        }
        return Integer.parseInt(s);
    }

    public int exam2(String s) {
        String[][] mapArr = { {"0", "zero"},
                {"1", "one"},
                {"2", "two"},
                {"3", "three"},
                {"4", "four"},
                {"5", "five"},
                {"6", "six"},
                {"7", "seven"},
                {"8", "eight"},
                {"9", "nine"} };

        for(String[] map : mapArr){
            s = s.replace(map[1], map[0]);
        }

        int answer = Integer.parseInt(s);
        return answer;
    }

    public static void main(String[] args) {
        WordToInt wordToInt = new WordToInt();
        System.out.println(wordToInt.solution("one4seveneightseven")); // expected: 14787
    }
    }

