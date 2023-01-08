package programmers.LV1;

import java.util.HashMap;

public class PersonalityTypeTest_exam {
        public String solution(String[] survey, int[] choices) {
            String answer = "";
            char [][] type = {{'R', 'T'}, {'C', 'F'}, {'J', 'M'}, {'A', 'N'}};
            int [] score = {0, 3, 2, 1, 0, 1, 2, 3};
            HashMap<Character, Integer> point = new HashMap<Character, Integer>();

            // 점수 기록할 배열 초기화
            // HashMap의 key를 type[][] 배열로 초기화
            for (char[] t : type) {
                point.put(t[0], 0);
                point.put(t[1], 0);
            }

            // 점수 기록
            for (int idx = 0; idx < choices.length; idx++){
                if(choices[idx] > 4){
                    point.put(survey[idx].charAt(1), point.get(survey[idx].charAt(1)) + score[choices[idx]]);
                } else {
                    point.put(survey[idx].charAt(0), point.get(survey[idx].charAt(0)) + score[choices[idx]]);
                }
            }

            // 지표 별 점수 비교 후 유형 기입
            // 이미 char[i] 배열이 사전순으로 정렬됐기 때문에
            // type[0]의 점수가 크거나 '같을 경우에도' type[0] 반환
            for (char[] t : type) {
                answer += (point.get(t[1]) <= point.get(t[0])) ? t[0] : t[1];
            }

            return answer;
        }
    }
