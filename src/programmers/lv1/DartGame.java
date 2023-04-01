package programmers.lv1;

import java.util.ArrayList;
import java.util.List;

public class DartGame {
    /** 정규식
     . -> 어떤 문자 한 개
     * -> 0개 이상
     [] -> or
     ^ -> 제외
     ** EX
     - System.out.println("ssS".matches("[a-z][a-z][a-zA-Z]")); // true
     - System.out.println("ssS".matches("[a-z][a-z][A-Z]")); // true
     - System.out.println("ssS".matches("[a-z]")); // false
     */
    public int solution(String dartResult) {
        int answer = 0;
        List<Integer> scoreList = new ArrayList<>();

        String[] numArray = dartResult.split("");

        for(int i=1; i<numArray.length;i++){
            if(numArray[i].matches("[0-9]")==false){ // Character.isDigit()
                int targetScore = 0;
                String target = numArray[i];

                if(target.equals("S")||target.equals("D")||target.equals("T")) { // 문자열 비교는 equals(), == 는 주소 비교
                    if (i >= 2 && (numArray[i-2] + numArray[i-1]).equals("10")) {
                        targetScore = 10;
                    } else {
                        targetScore = Integer.parseInt(numArray[i-1]);
                    }
                }

                if(target.equals("S")) {scoreList.add(targetScore);}
                else if(target.equals("D")){scoreList.add((int) Math.pow(targetScore,2));}
                else if(target.equals("T")){scoreList.add((int) Math.pow(targetScore,3));}

                if(target.equals("*")) {
                    if (i < 4) {
                        int tmp = scoreList.get(scoreList.size() - 1) * 2;
                        scoreList.remove(scoreList.size() - 1);
                        scoreList.add(tmp);
                    } else {
                        int tmp = scoreList.get(scoreList.size()-2)*2;
                        int tmp2 = scoreList.get(scoreList.size()-1)*2;
                        scoreList.removeIf(el->scoreList.indexOf(el) >= scoreList.size()-2);
                        scoreList.add(tmp);
                        scoreList.add(tmp2);
                        }
                    }

                if(target.equals("#")){
                    int tmp = scoreList.get(scoreList.size() - 1) * (-1);
                    scoreList.remove(scoreList.size() - 1);
                    scoreList.add(tmp);
                }

            }
        }
            for(int score : scoreList){
                answer += score;
            }
        return answer;
    }

    public static void main(String[] args){ // 경계값 테스트(최소, 최대, 미만, 초과)
        DartGame dartGame = new DartGame();
        System.out.println(dartGame.solution("10T*10T*10T*")); // expected : 10000
        System.out.println(dartGame.solution("1S2D*3T")); // expected : 37
        System.out.println(dartGame.solution("1D2S#10S")); // expected : 9
        System.out.println(dartGame.solution("1D2S0T")); // expected : 3
        System.out.println(dartGame.solution("1S*2T*3S")); // expected : 23
        System.out.println(dartGame.solution("1D#2S*3S")); // expected : 5
        System.out.println(dartGame.solution("1T2D3D#")); // expected : -4
        System.out.println(dartGame.solution("1D2S3T*")); // expected : 59
    }
}
