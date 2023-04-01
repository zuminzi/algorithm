package programmers.lv2;

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
        Queue<Integer> bridgeQ = new LinkedList<>();
        Queue<Integer> waitingQ = new LinkedList<>();
        List<Integer> durationQ = new ArrayList<>(); // 다리위에서 각 트럭별 지속시간
        for(int i=0; i<truck_weights.length; i++){
            waitingQ.add(truck_weights[i]);
        }

        int weightSum = 0;
        while(!bridgeQ.isEmpty() || !waitingQ.isEmpty()){
            if(bridgeQ.isEmpty() && waitingQ.isEmpty()) break;

            // 지속시간의 첫 요소가 다리길이와 같다면(다리를 다 건넜다면) 통행완료 처리
            if(!durationQ.isEmpty() && durationQ.get(0) == bridge_length){
                weightSum -= bridgeQ.poll();
                durationQ.remove(0);
            }
            // 다리의 제한 무게와 제한 길이 range를 벗어나지 않는다면 다리 위에 새로운 트럭 추가
            if(!waitingQ.isEmpty() && weightSum+waitingQ.peek() <= weight && bridgeQ.size() <= bridge_length){
                bridgeQ.add(waitingQ.peek());
                weightSum += waitingQ.poll();
                durationQ.add(0);
            }
            // 전체 지속시간과 다리위의 트럭들 지속시간 증가 처리
            duration++;
            durationQ = durationQ.stream().map(el -> {return el+1;}).collect(Collectors.toList()); // durationQueue 각 요소 1씩 증가
        }
        return duration;
    }

    // ~29.35ms, 79.9MB
    // LinkedList에서 배열로 (무게, 지속시간) 관리 // list.get(index)[index], list.removeFirst()
    public int exam(int bridge_length, int weight, int[] truck_weights) {
        int answer = 1;
        int truckIdx = 0;
        int weightSum = truck_weights[0];
        LinkedList<int[]> bridge = new LinkedList<>();

        /* weight / time */
        bridge.offer(new int[]{truck_weights[truckIdx], 1}); // 배열 사이즈가 2인 요소 추가 // [해당 트럭의 무게, 지속시간]

        while (!bridge.isEmpty()) {
            if (bridge.get(0)[1] == bridge_length) { // 배열의 인덱스 [1]로 통행완료 체크
                int[] first = bridge.removeFirst();
                weightSum -= first[0];
            }

            for (int i = 0; i < bridge.size(); i++) {
                bridge.get(i)[1]++; // 다리 위의 지속시간 1씩 증가 처리
            }

            // truck_weights의 마지막 요소가 아니고
            // 요소를 추가한 bridge 사이즈가 다리 길이 제한을 넘지 않고
            // 다리 무게 제한을 넘지 않는다면
            // 트럭 새로 추가(트럭 인덱스++ 처리, 무게 추가, bridge 관리 리스트에 추가)
            if (truckIdx < truck_weights.length-1 && bridge.size() + 1 <= bridge_length && weightSum + truck_weights[truckIdx + 1] <= weight) {
                truckIdx++;
                weightSum += truck_weights[truckIdx];
                bridge.add(new int[]{truck_weights[truckIdx], 1});
            }

            answer++;
        }
        return answer;
    }

    // ~72.65ms, 105MB
    // 이동하는 트럭과 대기중인 트럭 각각 큐로 관리
    // 트럭은 Truck 객체로 (무게,이동거리) 속성 지니고 있음
    public int exam2(int bridgeLength, int weight, int[] truckWeights) {
        Queue<Truck> waitQ = new LinkedList<>();
        Queue<Truck> moveQ = new LinkedList<>();
        for (int t : truckWeights) {
            waitQ.offer(new Truck(t));
        }

        int answer = 0;
        int curWeight = 0;
        while (!waitQ.isEmpty() || !moveQ.isEmpty()) {
            answer++; // 초(sec) 증가문
            // 다리 위에 아무 트럭도 없으면 통행 시작 처리
            if (moveQ.isEmpty()) {
                Truck t = waitQ.poll();
                curWeight += t.weight;
                moveQ.offer(t);
                continue;
            }
            // 트럭 이동거리 추가(move default=1, move++)
            for (Truck t : moveQ) {
                t.moving();
            }
            // Queue의 peek() 트럭 이동거리가 bridgeLength를 넘는 순간 통행완료처리(같을 때는 아직 통행완료 X)
            if (moveQ.peek().move > bridgeLength) {
                Truck t = moveQ.poll();
                curWeight -= t.weight;
            }
            // 트럭 추가 조건 체크
            // 1. waitQ가 비어있는지 2.무게 제한
            if (!waitQ.isEmpty() && curWeight + waitQ.peek().weight <= weight) {
                Truck t = waitQ.poll();
                curWeight += t.weight;
                moveQ.offer(t);
            }
        }
        return answer;
    }

    class Truck {
        int weight;
        int move;
        public Truck(int weight) {
            this.weight = weight;
            this.move = 1;
        }
        public void moving() {
            move++;
        }
    }

    // ~37.60ms, 88MB
    // flag 변수로 방문처리 표시 // for문으로 순회 시 요소 제거 X, for문 순회 후 flag true 요소만 제거 O
    public int exam3(int bridge_length, int weight, int[] truck_weights) {
        int truckNumber = 0;
        int finshedCount = 0;
        int time = 0;
        List<Bridge> bridgeList = new ArrayList<>();

        while (truck_weights.length > finshedCount) {

            int currentBridgeWeight = 0;
            boolean isFlag = false;

            // bridge 위에 있는 트럭들 +1씩 이동 처리
            for(Bridge bridge : bridgeList) {
                bridge.position += 1;
                // 통행완료 했으면 flag true 처리
                if(bridge.position >= bridge_length) {
                    isFlag = true;
                    finshedCount++;
                }
                else currentBridgeWeight += bridge.weight;
            }

            // flag true면 첫번째 요소 제거
            if(isFlag) bridgeList.remove(0);

            if(truckNumber < truck_weights.length) { // 현재 truck 인덱스를 아래 if문과 합쳐서 검사하면 ~49.49ms, 85MB로 실행시간 증가
                // 위에 트럭 인덱스 다음에 다리 길이와 무게로 제한조건 체크
                if(bridge_length > bridgeList.size()
                        && weight >= currentBridgeWeight + truck_weights[truckNumber]) {
                    bridgeList.add(new Bridge(truck_weights[truckNumber])); // 다리 리스트에는 트럭 무게만 추가 후 position으로 이동 거리 관리
                    truckNumber++;
                }
            }
            time++;
        }

        return time;
    }

    static class Bridge {
        int weight;
        int position;
        Bridge(int weight) {
            this.weight = weight;
            this.position = 0;
        }
    }

    public static void main(String[] args){
        PG_42583 pg_42583 = new PG_42583();
        System.out.println(pg_42583.codeOfMine(2, 10, new int[]{7,4,5,6})); // expected : 8
        System.out.println(pg_42583.codeOfMine(100, 100, new int[]{10})); // expected : 101
        System.out.println(pg_42583.codeOfMine(100, 100, new int[]{10,10,10,10,10,10,10,10,10,10})); // expected : 110
        System.out.println(pg_42583.codeOfMine(2,2,new int[]{1,1})); // expected : 4
    }
}
