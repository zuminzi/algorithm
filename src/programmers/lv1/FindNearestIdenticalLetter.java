package programmers.lv1;

import java.util.HashMap;

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

    public int[] exam(String s) {
        int[] answer = new int[s.length()];
        HashMap<Character,Integer> map = new HashMap<>();
        // 루프문 돌리면서 바로바로 answer[i]에 넣으면
        // key로 구분하는 자료구조 map을 이용하여 가장 가까운 '이전의' 동일 요소 저장하여 비교 가능
        for(int i=0; i<s.length();i++){
            char ch = s.charAt(i);
            answer[i] = i-map.getOrDefault(ch,i+1); // defaultValue가 자신일 경우 -1을 리턴하기 위해 +1 처리
            map.put(ch,i);
        }
        return answer;
    }

    public static void main(String[] args){
        FindNearestIdenticalLetter findNearestIdenticalLetter = new FindNearestIdenticalLetter();
        System.out.println(findNearestIdenticalLetter.codeOfMine("banana"));
    }
}