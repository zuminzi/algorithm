package programmers.LV2;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

// output : 모든 트럭이 다리를 건너기 위한 최솟값 초
// 다리에는 트럭이 최대 bridge_length대 올라갈 수 있음, weight 이하까지 무게 가능
// 다리에 완전히 오르지 않은 트럭의 무게는 제외
// 정해진 순으로만
// 다리를 지나기까지 bridge_length초 걸림
public class PG_42583 {
    // ~168.79ms, 197MB
    public long codeOfMine(int bridge_length, int weight, int[] truck_weights) {
        long duration =  0;
        Queue<Integer> bridgeQueue = new LinkedList<>();
        Queue<Integer> waitingQueue = new LinkedList<>();
        List<Integer> peekDuration = new ArrayList<>(); // 다리위에서 각 트럭별 지속시간
        for(int i=0; i<truck_weights.length; i++){
            waitingQueue.add(truck_weights[i]);
        }

        int weightSum = 0;
        while(!bridgeQueue.isEmpty() || !waitingQueue.isEmpty()){
            if(bridgeQueue.isEmpty() && waitingQueue.isEmpty()) break;
            if(!peekDuration.isEmpty() && peekDuration.get(0) == bridge_length){
                weightSum -= bridgeQueue.poll();
                peekDuration.remove(0);
            }
            if(!waitingQueue.isEmpty() && weightSum+waitingQueue.peek() <= weight && bridgeQueue.size() <= bridge_length){
                bridgeQueue.add(waitingQueue.peek());
                weightSum += waitingQueue.poll();
                peekDuration.add(0);
            }
            duration++;
            peekDuration = peekDuration.stream().map(el -> {return el+1;}).collect(Collectors.toList()); // peekDuration 각 요소 1씩 증가
        }
        return duration;
    }

    public long fail_sol(int bridge_length, int weight, int[] truck_weights) {
        int weightLimit = weight;
        long duration =  10000;
        int truckCnt = 0;
        BigDecimal perDistance = BigDecimal.ONE.divide(BigDecimal.valueOf((double)bridge_length)); // == (1/bridge_length)
        Queue<BigDecimal> lengthQueue = new LinkedList<>();
        Queue<Integer> weightQueue = new LinkedList<>();
        duration--; // 시작 초 // 1초 뒤부터 시작
        for(; duration>=0 ; duration--){
            // 삭제하면서 for문 돌리면 뒷요소 반영 x
            // 다리 위의 트럭들 이동거리 증가
            for (int i=0; i<lengthQueue.size(); i++){
                lengthQueue.add(lengthQueue.poll().add(perDistance));
            }
            // 다리 통행완료된 트럭(element>1) 제거
            if (!lengthQueue.isEmpty() && lengthQueue.peek().doubleValue() > 1) {
                lengthQueue.poll();
                weightLimit += weightQueue.poll();
            }
            // 마지막 트럭까지 통행 완료된 뒤에 break
            if(truckCnt == truck_weights.length && lengthQueue.isEmpty()){
                break;
            }
            // 다리 위에 새로운 트럭 추가
            if(truckCnt != truck_weights.length && weightLimit >= truck_weights[truckCnt]){
                lengthQueue.add(perDistance);
                weightQueue.add(truck_weights[truckCnt]);
                weightLimit -= truck_weights[truckCnt];
                truckCnt++;
            }
        }
        return 10000 - duration;
    }

    public long fail_sol_2(int bridge_length, int weight, int[] truck_weights) {
        int weightLimit = weight;
        long duration =  1;
        int truckCnt = 0;
        BigDecimal perDistance = BigDecimal.ONE.divide(BigDecimal.valueOf((double)bridge_length));
        Queue<BigDecimal> lengthQueue = new LinkedList<>();
        Queue<Integer> weightQueue = new LinkedList<>();
        while(truckCnt <= truck_weights.length){
            for (int i=0; i<lengthQueue.size(); i++){
                lengthQueue.add(lengthQueue.poll().add(perDistance));
            }
            if (!lengthQueue.isEmpty() && lengthQueue.peek().doubleValue() > 1) {
                lengthQueue.poll();
                weightLimit += weightQueue.poll();
            }
            if(truckCnt == truck_weights.length && lengthQueue.isEmpty()){
                break;
            }
            if(truckCnt != truck_weights.length && weightLimit >= truck_weights[truckCnt]){
                lengthQueue.add(perDistance);
                weightQueue.add(truck_weights[truckCnt]);
                weightLimit -= truck_weights[truckCnt];
                truckCnt++;
            }
            duration++;
        }
        return duration;
    }

    public static void main(String[] args){
        PG_42583 pg_42583 = new PG_42583();
        System.out.println(pg_42583.codeOfMine(2, 10, new int[]{7,4,5,6})); // expected : 8
        System.out.println(pg_42583.codeOfMine(100, 100, new int[]{10})); // expected : 101
        System.out.println(pg_42583.codeOfMine(100, 100, new int[]{10,10,10,10,10,10,10,10,10,10})); // expected : 110
        System.out.println(pg_42583.codeOfMine(2,2,new int[]{1,1})); // expected : 4
    }
}
