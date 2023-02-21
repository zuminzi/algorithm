package programmers.LV2;


import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

// Accuracy Test ~1.28ms, 74.6MB
public class PG_42586 {
    public Collection<Integer> solution(int[] progresses, int[] speeds) {
        int[] cntArray = new int[progresses.length];

        for(int i=0; i<progresses.length; i++){
            int target = progresses[i];
            while(target < 100){
                target += speeds[i];
                cntArray[i]++;
                if(target >= 100){
                    break;
                }
            }
        }

        Map<Integer,Integer> answer = new TreeMap<>();
        int prev =cntArray[0];
        for(int i=0; i<cntArray.length; i++){
            if(i==0){
                answer.put(cntArray[i],1);
            } else {
                int targetValue = cntArray[i] < prev ? prev : cntArray[i];
                answer.merge(targetValue, 1, (oldValue, newValue) -> oldValue + 1);
                prev = targetValue;
            }
        }
        return answer.values();
    }

    public static void main(String[] args){
        PG_42586 pg_42586 = new PG_42586();
        System.out.println(pg_42586.solution(new int[]{93, 30, 55}, new int[]{1, 30, 5})); // expected : [2,1]
        System.out.println(pg_42586.solution(new int[]{95, 90, 99, 99, 80, 99}, new int[]{1, 1, 1, 1, 1, 1})); // expected : [1,3,2]
    }
}
