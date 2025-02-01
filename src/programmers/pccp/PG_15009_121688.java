package programmers.pccp;

import java.util.PriorityQueue;

public class PG_15009_121688 {
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

    public static void main (String[] args){
        PG_15009_121688 pg_15009_121688 = new PG_15009_121688();
        System.out.println(pg_15009_121688.solution(new int[]{10,3,7,2}, 2));
    }
}
