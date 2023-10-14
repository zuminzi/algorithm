package programmers.lv2;

// 2명의 신입사원이 같이 공부하면 서로의 능력을 흡수하여 두 신입사원의 능력치는 공부 전 두 사람의 능력치의 합이 됩니다
// 모든 신입사원들의 능력치의 합을 최소화

import java.util.PriorityQueue;

public class PG_121688 {
    /**
     * 모든 신입사원의 능력치 합을 최소화하기 위해 우선순위 큐(PriorityQueue)를 활용한 알고리즘
     */
    public int solution(int[] ability, int number) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i=0; i<ability.length; i++){
            pq.add(ability[i]);
        }

        while(number-- > 0){
            int first = pq.poll();
            int second = pq.poll();
            for(int i=0; i<2; i++) {
                pq.add(first + second);
            }
        }

        return pq.stream().reduce((i1, i2) -> i1+i2).get();
    }
}
