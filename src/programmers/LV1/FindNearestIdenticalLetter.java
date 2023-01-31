package programmers.LV1;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class FindNearestIdenticalLetter {
    public int[] codeOfMine(String s) {
        int[] answer = new int[s.length()];
        answer[0] = -1;
        for (int i = s.length()-1; i > 0; i--) {
            for (int k = i - 1; k >= 0; k--) {
                if (s.charAt(i) == s.charAt(k)) {
                    answer[i] = Math.abs(i - k);
                    break;
                } else {
                    answer[i] = -1;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args){
        FindNearestIdenticalLetter findNearestIdenticalLetter = new FindNearestIdenticalLetter();
        System.out.println(findNearestIdenticalLetter.codeOfMine("banana"));
    }
}