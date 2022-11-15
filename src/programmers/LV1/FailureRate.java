package programmers.LV1;

import java.util.*;

public class FailureRate {
    /*
    exam code point
    - stages 배열길이와 도달 플레이어 수 초기값 관계 (stages 배열 길이 == stage 1 도달 플레이어 수)
    - 다음 stage 도달 플레이어 수와 현재 stage 도달 플레이어 수 관계 ( 다음 도달 = 현재 도달 - 현재 실패)
     */
    public String codeOfMine(int N, int[] stages) {
        int[] answer = new int[N];
        HashMap<Integer, Double> map = new HashMap<>();
            for(int j=1; j<=N; j++) {
                // j++ 될 때마다 accessCnt, failureCnt, failureRate 초기화 필수!
                int accessCnt = 0;
                int failureCnt = 0;
                double failureRate = 0;

                for(int i=0; i<stages.length; i++) {
                    if(j <= stages[i]) {accessCnt++;}
                    if(j == stages[i]) {failureCnt++;}
                }
                // 나눌 수 없어서 NaN으로 처리된 경우(0 체크) 필수!
                if(failureCnt != 0 && accessCnt != 0){failureRate = (double) failureCnt/accessCnt;}
                map.put(j,failureRate);
            }

            // value 기준 내림차순 정렬
            List<Integer> listKeySet;
            listKeySet = new ArrayList<>(map.keySet());
            Collections.sort(listKeySet, (value1, value2) -> (map.get(value2).compareTo(map.get(value1))));

            // list to array
            int idx = 0;
            for(Integer key : listKeySet){
                answer[idx++] = key;
            }

        return Arrays.toString(answer);
    }

    public int[] exam1(int N, int[] lastStages) {
        int nPlayers = lastStages.length;
        int[] nStagePlayers = new int[N + 2];
        for (int stage : lastStages) {
            nStagePlayers[stage] += 1;
        }

        int remainingPlayers = nPlayers; // 1번 스테이지 도달 플레이어 수는 전체 플레이어이므로 remainingPlayers 초기값은 배열길이
        List<Stage> stages = new ArrayList<>();
        for (int id = 1 ; id <= N; id++) {
            double failure = (double) nStagePlayers[id] / remainingPlayers;
            remainingPlayers -= nStagePlayers[id];

            Stage s = new Stage(id, failure);
            stages.add(s); // stage를 하나의 객체로 관리하여 저장
        }
        Collections.sort(stages, Collections.reverseOrder());

        int[] answer = new int[N];
        for (int i = 0; i < N; i++) {
            answer[i] = stages.get(i).id;
        }
        return answer;
    }

    class Stage implements Comparable<Stage> {
        public int id;
        public double failure;

        public Stage(int id_, double failure_) {
            id = id_;
            failure = failure_;
        }

        @Override
        public int compareTo(Stage o) {
            if (failure < o.failure ) {
                return -1;
            }
            if (failure > o.failure ) {
                return 1;
            }
            return 0;
        }
    }

    public String exam2(int N, int[] stages) {
        int[] answer = new int[N];
        double[] tempArr = new double[N];
        int arrLength = stages.length;
        int idx = arrLength;
        double tempD = 0;
        int tempI = 0;
        for (int i = 0; i < arrLength; i++) {
            int stage = stages[i];

            // 스테이지 실패(해당 스테이지에 머물러있는) 플레이어 수
            // += 1로 count
            if (stage != N + 1) {
                answer[stage - 1] += 1;
            }
        }
        for (int i = 0; i < N; i++) {
            int personNum = answer[i];
            tempArr[i] = (double) personNum / idx; // tempArr 배열에는 실패율 저장
            idx -= personNum; // 다음 스테이지 도달 플레이어 수 = 이전 스테이지 도달 플레이어 수(idx) - 실패 플레이어 수(personNum)
            answer[i] = i + 1; // 최종적으로 answer 배열에는 stage number가 들어가야 하므로 stage number 저장
        }

        // 내림차순 정렬
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N - i; j++) {
                // 뒤 idx 실패율이 더 적으면
                if (tempArr[j - 1] < tempArr[j]) {
                    // 실패율 배열 tempArr 자리 교체
                    tempD = tempArr[j - 1];
                    tempArr[j - 1] = tempArr[j];
                    tempArr[j] = tempD;

                    // 스테이지 넘버 배열 answer 자리 교체
                    tempI = answer[j - 1];
                    answer[j - 1] = answer[j];
                    answer[j] = tempI;
                }
            }
        }
        return Arrays.toString(answer);
    }

    public int[] exam3(int N, int[] stages) {
        int[] answer = new int[N];
        float[] result = new float[N];

        for(int i = 0 ; i < N; i++) {
            float unclear = 0;
            float clear = 0;

            for(int stage : stages) {
                // stage는 1부터 시작하므로 idx(전체 stage level)와 현재 stage 관계식 -> (idx = stage - 1)
                if(stage == i+1) { // 현재 스테이지가 특정 스테이지 레벨과 같으면
                    unclear++;
                    clear++;
                } else if(stage > i+1) { // 현재 스테이지가 특정 스테이지 레벨보다 더 높으면
                    clear++;
                }
            }

            if(clear != 0)
                result[i] = unclear/clear;
            else
                result[i] = 0;
        }

        // value로 내림차순 정렬
        for(int i = 0; i < N ; i++) {
            float maxValue = result[0];
            int maxIndex = 0;

            for(int j = 0; j < N; j++) {
                if(maxValue < result[j]) {
                    maxIndex = j;
                    maxValue = result[j];
                }
            }
            result[maxIndex] = -1;
            answer[i] = maxIndex + 1;
        }

        return answer;
    }

    public static void main(String args[]) {
        FailureRate failureRate = new FailureRate();
        int N = 5;
        int[] stages = {2, 1, 2, 6, 2, 4, 3, 3};
        int N2 = 7;
        int[] stages2 = {1,3,2,5,5};
        System.out.println(failureRate.exam1(N,stages)); // expected: [3,4,2,1,5]
        //System.out.println(failureRate.exam2(N2,stages2)); // expected: [5, 3, 2, 1, 4, 6, 7]
    }
}
