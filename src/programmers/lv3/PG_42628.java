package programmers.lv3;

import java.util.*;

public class PG_42628 {
    // PriorityQueue.remove(Object o)로 특정 요소 삭제 가능
    public int[] codeOfMine(String[] operations) {
        int[] answer = new int[2];
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int totalIdx = 0;
        for(String operation : operations){
            if (operation.startsWith("I")) {
                int target = Integer.parseInt(operation.replace("I ",""));
                maxHeap.add(target);
                minHeap.add(target);
                totalIdx++;
            } else if(operation.startsWith("D -1")){
                if(totalIdx > 0) {
                    minHeap.poll();
                    // maxHeap과 minHeap에서 삭제 요소를 각 각 처리하지 않고
                    // 나머지 Heap에도 반영할 수 있음 // PriorityQueue.remove();
                    // maxHeap.remove(minHeap.poll());
                    totalIdx--;
                }
            } else {
                if(totalIdx > 0) {
                    maxHeap.poll();
                    totalIdx--;
                }
            }
        }
        if(totalIdx > 0) {
            // maxHeap과 minHeap에 둘 다 포함된 요소만 남아있는 요소이므로
            // 두 자료구조에 남아있는지 확인
            if(minHeap.contains(maxHeap.peek())) answer[0] = maxHeap.poll();
            else {

                while (!minHeap.contains(maxHeap.peek())) {
                    // Problem Point
                    // answer[0] = maxHeap.poll(); 하면 안되는 이유
                    // 겹치는 요소 직전에 poll() 하면 그 다음 while 조건문에서 peek()는 겹치는 요소이기 때문에
                    // while 실행문(answer[0] = maxHeap.poll();)을 실행하지 않음
                    // 즉 겹치는 요소는 while 조건 통과 못하기 때문에
                    // answer[0]에는 겹치는 요소 직전에 저장되고 끝남
                    // peek()와 poll() 잘 계산하여 사용하기 **
                    maxHeap.poll();
                }
                answer[0] = maxHeap.peek();
            }

            if(maxHeap.contains(minHeap.peek())) answer[1] = minHeap.poll();
            else {
                while (!maxHeap.contains(minHeap.peek())) {
                    minHeap.poll();
                }
                answer[1] = minHeap.peek();
            }
        }
        return answer;
    }

    public int[] exam(String[] arguments) {
        int[] answer = {0,0};

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        PriorityQueue<Integer> reverse_pq = new PriorityQueue<>(Collections.reverseOrder());

        for(int i=0; i<arguments.length; i++) {
            String temp[] = arguments[i].split(" ");
            switch(temp[0]) {
                case "I" :
                    pq.add(Integer.parseInt(temp[1]));
                    reverse_pq.add(Integer.parseInt(temp[1]));
                    break;
                case "D" :
                    if(pq.size() > 0) {
                        if(Integer.parseInt(temp[1]) == 1) {
                            // 최댓값 삭제
                            int max = reverse_pq.poll();
                            pq.remove(max);
                        } else {
                            // 최솟값 삭제
                            int min = pq.poll();
                            reverse_pq.remove(min);
                        }
                    }
                    break;
            }
        }

        if(pq.size() >= 2) {
            answer[0] = reverse_pq.poll();
            answer[1] = pq.poll();
        }

        System.out.println(answer[0] + ":" + answer[1]);

        return answer;
    }

    public static void main(String[] args){
        PG_42628 pg_42628 = new PG_42628();
        // expected : [0,0]
        System.out.println(pg_42628.codeOfMine(new String[]{"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"}));
        // expected : [333,-45]
        System.out.println(pg_42628.codeOfMine(new String[]{"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"}));
        // expected : [6,5]
        System.out.println(pg_42628.codeOfMine(new String[]{"I 4", "I 3", "I 2", "I 1", "D 1", "D 1", "D -1", "D -1", "I 5", "I 6"}));
    }
}
