package programmers.LV2;

import java.util.*;

//LZW(Lempel-Ziv-Welch) 압축 알고리즘
// 압축 전의 정보를 완벽하게 복원 가능한 무손실 압축 알고리즘

public class PG_17684 {

    // ~4.20ms, 73.1MB
    public List<Integer> solution(String msg) {
        List<Integer> answer = new ArrayList<>();
        Stack<Character> waiting = new Stack<>();
        Map<String,Integer> dictionary = new HashMap<>();
        for(int i='A'; i<='Z'; i++){
            dictionary.put(String.valueOf((char)i), i-64);
        }
        for(int i=msg.length()-1; i>=0; i--){
            waiting.push(msg.charAt(i));
        }
        StringBuilder sb = new StringBuilder();

        while (!waiting.isEmpty()){
            sb.append(waiting.pop());
            while (dictionary.containsKey(sb.toString())){
                if(!waiting.isEmpty()){
                    char target = waiting.pop();
                    sb.append(target);
                    if(!dictionary.containsKey(sb.toString())){
                        waiting.push(target);
                        dictionary.put(sb.toString(), dictionary.keySet().size()+1);
                        sb.delete(sb.length() - 1,sb.length());
                        answer.add(dictionary.get(sb.toString()));
                        sb.setLength(0);
                        break;
                    }
                } else {
                    answer.add(dictionary.get(sb.toString()));
                    break;
                }
            }
        }
        return answer;
    }
    public static void main (String[] args){
        PG_17684 pg_17684 = new PG_17684();
        // expected : [11, 1, 27, 15]
        System.out.println(pg_17684.solution("KAKAO"));
        // expected : [20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34]
        System.out.println(pg_17684.solution("TOBEORNOTTOBEORTOBEORNOT"));
        // expected : [1, 2, 27, 29, 28, 31, 30]
        System.out.println(pg_17684.solution("ABABABABABABABAB"));
    }
}
