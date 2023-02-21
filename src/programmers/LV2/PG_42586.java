package programmers.LV2;


import java.util.*;

// Accuracy Test ~1.28ms, 74.6MB
// While문을 제거했더니
public class PG_42586 {
    // Problem Point 1 - 먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses
    public Collection<Integer> codeOfMine(int[] progresses, int[] speeds) {
        int[] cntArray = new int[progresses.length]; // 각 기능별 진도율이 100%되는 작업일수

        for(int i=0; i<progresses.length; i++){
            int cnt = 0;
            int target = progresses[i];
            while(target < 100){
                target += speeds[i];
                cnt++;
                if(target >= 100){
                    cntArray[i] = cnt;
                    break;
                }
            }
            // Solution 2 - While문 제거하고 바로 작업일수를 바로 연산했더니 수행시간은 더 오래걸많
//            double remain = (double)(100 - progresses[i])/speeds[i];
//            int days = (int) Math.ceil(remain/1*1); // 1의 자리 올림처리
//                cntArray[i] = days;
        }

        // Problem Point 2 - 앞 기능보다 작업일수가 적은 기능들은 더 큰 앞 기능 작업일수에 포함되므로
        // 배포순(배열 progresses 순)으로 return 하려면
        // (key 해시값이 주소값으로 저장되는) HashMap (X)
        // (레드 블랙 트리에 의해 자동 오름차순 정렬하여 저장하는) TreeMap (O)
        Map<Integer,Integer> answer = new TreeMap<>();
        int prev =cntArray[0];
        for(int i=0; i<cntArray.length; i++){
            if(i==0){
                answer.put(cntArray[i],1);
            } else {
                // 먼저 배포되는 기능보다 뒤에 기능 작업일이 더 적다면 바로 앞 기능 작업일에 포함
                int targetValue = cntArray[i] < prev ? prev : cntArray[i];

                answer.merge(targetValue, 1, (oldValue, newValue) -> oldValue + 1);
                prev = targetValue;
            }
        }
        return answer.values();
    }

    // Accuracy Test ~3.55ms, 73MB
    public int[] exam1(int[] progresses, int[] speeds) {
        // 작업의 개수는 100개 이하이므로 return 배열 크기를 최대값으로 설정(최대 작업일 = 100)
        int[] dayOfend = new int[100];
        int day = 0;
        for(int i=0; i<progresses.length; i++) {
            while(progresses[i] + (day*speeds[i]) < 100) {
                day++;
            }
            dayOfend[day]++;
        }
        return Arrays.stream(dayOfend).filter(i -> i!=0).toArray();
    }

    // Accuracy Test ~0.59ms, 73.9MB
    public int[] exam2(int[] progresses, int[] speeds) {
        Queue<Integer> q = new LinkedList<>();
        List<Integer> answerList = new ArrayList<>();

        for (int i = 0; i < speeds.length; i++) {
            double remain = (100 - progresses[i]) / (double) speeds[i];
            int date = (int) Math.ceil(remain);

            // 배포 순서는 뒤지만 더 일찍 끝나는 기능들은 앞에 기능 배포일에 포함되도록
            // prev 변수를 따로 생성하지 않고 queue.peek()로 이전 요소 가져오기
            if (!q.isEmpty() && q.peek() < date) {
                answerList.add(q.size());
                q.clear();
            }
            q.offer(date);
        }

        answerList.add(q.size());

        int[] answer = new int[answerList.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = answerList.get(i);
        }

        return answer;
    }

    public static void main(String[] args){
        PG_42586 pg_42586 = new PG_42586();
        System.out.println(pg_42586.exam2(new int[]{93, 30, 55}, new int[]{1, 30, 5})); // expected : [2,1]
        System.out.println(pg_42586.exam2(new int[]{95, 90, 99, 99, 80, 99}, new int[]{1, 1, 1, 1, 1, 1})); // expected : [1,3,2]
    }
}
