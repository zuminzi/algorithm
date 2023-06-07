package programmers.lv3;

import java.util.*;

/*
 * @param jobs 각 작업에 대해 [작업이 요청되는 시점, 작업의 소요시간]을 담은 2차원 배열
 * @return 작업의 요청부터 종료까지 걸린 시간의 평균을 가장 줄이는 방법으로 처리한 평균
 * 문제에 명시되어 있지 않지만, 요청이 동시에 들어오는 경우도 고려해야 함
 - ~24.94ms, 95.7MB
 */
public class PG_42627 {

    public int solution(int[][] jobs) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for(int i=0; i<jobs.length; i++) {
            map.put(jobs[i][0], new PriorityQueue<>());
        }
        for(int i=0; i<jobs.length; i++){
            PriorityQueue<Integer> pq = map.get(jobs[i][0]);
            pq.add(jobs[i][1]);
            map.put(jobs[i][0], pq);
        }

        int currT = 0;
        int waitingT = 0;
        int runningT = 0;
        int turnAroundT = 0; // 반환시간 = 대기시간 + 실행시간
        while (!map.isEmpty()) {
            // entrySet 사용하지 않은 이유 -> entrySet을 사용할 경우 value가 무조건 Object 타입으로 반환되기 때문
            int[] minRunningT = {-1, 1001};
            for (Integer key : map.keySet()) {
                if (key <= currT) {
                    runningT = map.get(key).peek();
                    if (runningT <= minRunningT[1]) {
                        minRunningT[0] = key;
                        minRunningT[1] = runningT;
                    }
                }
            }
            if (minRunningT[0] != -1) {
                PriorityQueue<Integer> pq = map.get(minRunningT[0]);
                pq.poll();
                if (pq.isEmpty()) {
                    map.remove(minRunningT[0]);
                } else {
                    map.put(minRunningT[0], pq);
                }

                waitingT = currT - minRunningT[0];
                turnAroundT += waitingT + minRunningT[1];
                currT += minRunningT[1];
                // problem point - 작업 종료 후, 해당 시간을 기준으로 요청 작업 없을 시 다음 요청 작업으로 넘어가도록 처리 (currT 조정)
            } else {
                List<Integer> keys = new ArrayList<>(map.keySet());
                Collections.sort(keys);
                currT = keys.get(0);
            }
        }
        return turnAroundT / jobs.length;
    }

    // ~2.52ms, 79MB
    // 배열 jobs를 문제 기준에 따라 정렬 후, 인덱스를 활용하여 작업 조회하기
    // solution과 Worst Case 수행시간이 차이나는 이유는 위 아이디어를 활용하여 line 54-56 작업을 안했기 때문일 것
    public int exam(int[][] jobs) {
        Arrays.sort(jobs, new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                if(o1[0] <= o2[0]){ // 요청 시점을 기준으로 ASC
                    return -1;
                }
                return 1;
            }
        });

        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                if(o1[1] < o2[1]){ // 실행 시간을 기준으로 ASC
                    return -1;
                }
                return 1;
            }
        });

        int time = 0;
        int index = 0;
        float answer = 0;

        while(true){
            while(index < jobs.length && jobs[index][0] <= time){
                queue.offer(jobs[index]);
                index ++;
            }
            if(queue.size() == 0){
                time = jobs[index][0];
                continue;
            }
            int[] job = queue.poll();
            time += job[1];
            answer += time - job[0];
            if(index == jobs.length && queue.size() == 0){
                break;
            }
        }

        answer /= jobs.length;
        return (int)answer;
    }

    public static void main (String[] args) {
        PG_42627 pg_42627 = new PG_42627();
        System.out.println(pg_42627.exam(new int[][]{{0,3}, {1,9}, {2,6}})); // expected : 9
        System.out.println(pg_42627.exam(new int[][]{{0,3}, {4,9}, {5,6}})); // expected : 8 // 요청시간 공백
        System.out.println(pg_42627.exam(new int[][]{{0,1}, {0,9}, {1,2}})); // expected : 5 // 동시 요청
    }
}
