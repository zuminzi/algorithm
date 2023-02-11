package programmers.LV2;

import java.util.HashSet;
import java.util.Set;

public class PG_12981 {
    public int[] codeOfMine(int n, String[] words) {
        int[] answer = new int[2];
        Set<String> deduplicationSet = new HashSet<>();
        String prev = "";
        for(int i=0; i<words.length; i++){
            String target = words[i];
            if(i>=1){
                if(target.startsWith(prev) == false || deduplicationSet.add(target) == false){
                    // byte, short, int, long은 정수타입, double은 실수타입
                    // 사칙연산 연산자나 피연산자 둘 중에 하나만 실수면 소수점 출력 가능
                    answer[0] = (i % n) + 1;
                    answer[1] = (i / n) + 1;
                    // Problem Point **
                    // 초기 시도 시 return answer; 이 아닌 break;만 해서 답이 계속 [0,0]으로만 출력됨
                    return answer;
                }
            }
            deduplicationSet.add(target);
            prev = target.split("")[target.length() - 1];
        }
        answer[0] = 0;
        answer[1] = 0;
        return answer;
    }

    public int[] codeOfMine_re(int n, String[] words) {
        int[] answer = new int[2];
        Set<String> logSet = new HashSet<>();
        // refactor Point - 1
        char prev = words[0].charAt(words[0].length() - 1);
        for(int i=0; i<words.length; i++){
            String target = words[i];
            if(i>=1){
                if(target.startsWith(String.valueOf(prev)) == false || logSet.add(target) == false){
                    answer[0] = (i % n) + 1;
                    answer[1] = (i / n) + 1;
                    return answer;
                }
            }
            logSet.add(target);
            // refactor Point - 2
            prev = target.charAt(target.length() - 1);
        }
        answer[0] = 0;
        answer[1] = 0;
        return answer;
    }

    public static void main(String[] args){
        PG_12981 pg_12981 = new PG_12981();
        // expected : [3,3]
        System.out.println(pg_12981.codeOfMine_re(3,
                new String[]{"tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank"}));
        // expected : [0,0]
        System.out.println(pg_12981.codeOfMine_re(5,
                new String[] {"hello", "observe", "effect", "take", "either", "recognize", "encourage", "ensure", "establish", "hang", "gather", "refer", "reference", "estimate", "executive"}));
        System.out.println(pg_12981.codeOfMine_re(2,
                new String[] {"hello", "one", "even", "never", "now", "world", "draw"}));
        // expected : [1,3]
    }
}
  /*
* codeOfMine Accuracy Test

테스트 1 〉	통과 (0.43ms, 72.8MB)
테스트 2 〉	통과 (1.14ms, 75.6MB)
테스트 3 〉	통과 (0.15ms, 77.7MB)
테스트 4 〉	통과 (0.62ms, 76.4MB)
테스트 5 〉	통과 (2.06ms, 79.6MB)
테스트 6 〉	통과 (0.41ms, 73.1MB)
테스트 7 〉	통과 (1.15ms, 68.9MB)
테스트 8 〉	통과 (0.23ms, 68.5MB)
테스트 9 〉	통과 (0.57ms, 75.4MB)
테스트 10 〉	통과 (1.88ms, 76.5MB)
테스트 11 〉	통과 (1.38ms, 78MB)
테스트 12 〉	통과 (0.89ms, 76.7MB)
테스트 13 〉	통과 (0.28ms, 76.9MB)
테스트 14 〉	통과 (0.38ms, 77MB)
테스트 15 〉	통과 (0.28ms, 77.7MB)
테스트 16 〉	통과 (0.42ms, 73.8MB)
테스트 17 〉	통과 (0.23ms, 67.6MB)
테스트 18 〉	통과 (0.35ms, 75.3MB)
테스트 19 〉	통과 (0.20ms, 72.7MB)
테스트 20 〉	통과 (2.13ms, 77.1MB)
     */
/*
* codeOfMine_re Accuracy Test
- refactor point 1) String -> char
    - String.startsWith() 사용 시 인자는 String type이어야 하므로 char -> String 변환 필요
- refactor point 2) split -> charAt

테스트 1 〉	통과 (0.10ms, 78.4MB)
테스트 2 〉	통과 (0.19ms, 75.6MB)
테스트 3 〉	통과 (0.06ms, 73.2MB)
테스트 4 〉	통과 (0.18ms, 77.4MB)
테스트 5 〉	통과 (0.15ms, 71.4MB)
테스트 6 〉	통과 (0.07ms, 88.6MB)
테스트 7 〉	통과 (0.14ms, 73.6MB)
테스트 8 〉	통과 (0.10ms, 73.7MB)
테스트 9 〉	통과 (0.07ms, 74.7MB)
테스트 10 〉	통과 (0.22ms, 78.9MB)
테스트 11 〉	통과 (0.08ms, 80.3MB)
테스트 12 〉	통과 (0.16ms, 72.8MB)
테스트 13 〉	통과 (0.05ms, 76.9MB)
테스트 14 〉	통과 (0.09ms, 79.6MB)
테스트 15 〉	통과 (0.09ms, 73MB)
테스트 16 〉	통과 (0.04ms, 79.4MB)
테스트 17 〉	통과 (0.24ms, 74.6MB)
테스트 18 〉	통과 (0.21ms, 70.9MB)
테스트 19 〉	통과 (0.05ms, 76.5MB)
테스트 20 〉	통과 (0.16ms, 71.8MB)
 */