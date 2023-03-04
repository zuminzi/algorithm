package programmers.LV2;


import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Map;
import java.util.TreeMap;

public class PG_42626 {
    // 모든 음식의 스코빌 지수가 K 이상 될 때까지 스코빌 지수가 가장 낮은 두 개의 음식을 반복하여 섞기
    // 섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식의 스코빌 지수 + (두 번째로 맵지 않은 음식의 스코빌 지수 * 2)
    // input : 원하는 스코빌 지수 K
    // output : 섞어야 하는 최소 횟수

    // 우선순위 큐(내부 요소는 힙으로 구성되어 이진트리 구조)
    // Accuracy Test ~5.86ms, 73.1MB, Efficiency Test ~1660.77ms, 123MB
    public int solution(int[] scoville, int K){
        int answer = 0;
        int max = 0;
        // 우선순위 큐 정렬기준 디폴트도 오름차순이긴 하지만, 숫자 기준 오름차순 (X) 문자기준 오름차순 (O)
        // ex. 9, 10, 9999가 있다면 우선순위 큐에는 9, 9999, 10으로 정렬 후 저장됨
        Queue<Integer> queue = new PriorityQueue<>((o1, o2)->o1-o2); // 오름차순 우선순위 큐

        for(int el : scoville){
            if(el < K) {
                queue.add(el);
            } else{
                max = Math.max(max, el);
            }
        }

        while(!queue.isEmpty()) {
            int firstKey = queue.remove();

            if (queue.isEmpty()) {
                if (firstKey < K) {
                    if (max == 0) {
                        answer = -1;
                        break;
                    }
                }
                answer++; // K이상 스코빌 지수와 합친 과정 카운트
                break;
            }

            int secondKey = queue.remove();
            int scovilleScale = firstKey + (secondKey * 2);

            if (scovilleScale < K) {
                queue.add(scovilleScale);
            } else {
                max = Math.max(max, scovilleScale);
            }
            answer++;
        }
        return answer;
    }


    // K보다 작은 값만 우선순위 큐에 넣어서 정렬하는 로직보다 효율성 good
    // Accuracy Test ~2.43ms, 75.6MB, Efficiency Test ~1391.00ms, 139MB
    public int exam(int[] scoville, int K) {
        PriorityQueue<Integer> pqScov = new PriorityQueue<>();
        for (int s: scoville) {
            pqScov.add(s);
        }

        int cnt = 0;
        while (pqScov.size() > 1 && pqScov.peek() < K) {
            pqScov.add(pqScov.remove() + pqScov.remove() * 2);
            cnt++;
        }

        return pqScov.peek() >= K ? cnt : -1;
    }

    // success Accuracy Test, fail Efficiency Test
    public int fail_sol(int[] scoville, int K) {
        int answer = 0;
        int max = 0;
        Map<Integer, Integer> map = new TreeMap<>();

        for(int el : scoville){
            if(el < K) {
                map.put(el, map.getOrDefault(el,0)+1);
            } else{
                max = Math.max(max, el);
            }
        }

        while(!map.isEmpty()) {
            if (map.isEmpty()) break;

            int firstKey = (int) map.keySet().toArray()[0];
            int secondKey = 0;
            if(map.get(firstKey) > 1){
                secondKey = firstKey;
            } else {
                if (map.size() == 1) {
                    if (firstKey < K) {
                        if (max == 0) {
                            answer = -1;
                            break;
                        }
                    }
                    answer++; // K이상 스코빌 지수와 합친 과정 카운트
                    break;
                }
                secondKey = (int) map.keySet().toArray()[1];
            }

            int scovilleScale = firstKey + (secondKey * 2);
            // Problem Point - K 이상인 경우에는 큰 값에 새로 반영 필요
            if (scovilleScale < K) {
                map.put(scovilleScale, map.getOrDefault(scovilleScale, 0) + 1);
            } else {
                max = Math.max(max, scovilleScale);
            }
            resize(firstKey, map);
            resize(secondKey, map);
            answer++;
        }
        return answer;
    }

    public void resize(int element, Map<Integer,Integer> map){
        if(map.get(element) > 1){
            map.put(element, map.get(element)-1);
        } else {
            map.remove(element);
        }
    }

    public static void main(String[] args){
        PG_42626 pg_42626 = new PG_42626();
        System.out.println(pg_42626.exam(new int[]{1, 2, 3, 9, 10, 12},7)); // expected : 2
        System.out.println(pg_42626.exam(new int[]{1, 2, 3, 9, 10, 12},1000000000)); // expected : -1
        System.out.println(pg_42626.exam(new int[]{1, 2, 3, 9, 10, 999999999},1000000000)); // expected : 5
        System.out.println(pg_42626.exam(new int[]{1, 1, 2, 6},24)); // expected : -1
        System.out.println(pg_42626.exam(new int[]{1, 1, 2},3)); // expected : 2
    }
}
