package programmers.lv3;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * input : 퇴근까지 남은 N 시간과 각 일에 대한 작업량 works
 * output : 야근 피로도를 최소화한 값
 * 야근 피로도 = (야근을 시작한 시점에서 남은 일의 작업량)을 제곱하여 더한 값
 * 1시간 동안 작업량 1만큼을 처리
 */
public class PG_12927 {
    // Accuracy Test ~2.93ms, 71.2MB, Efficiency Test ~74.51ms, 67.7MB
    public long codeOfMine(int n, int[] works) {
        long answer = 0;
        Integer[] workArr = new Integer[works.length];
        for (int i = 0; i < workArr.length; i++) {
            workArr[i] = works[i];
        }
        Arrays.sort(workArr, Collections.reverseOrder());
        // Point - 1
        // 감소 작업 조건으로 n뿐만 아니라, 작업량이 0 이상인지 고려
        // => (workArr[workArr.length - 1] != 0) 조건문 추가
        while (n > 0 && workArr[workArr.length - 1] != 0) {
            for (int i = 0; i < workArr.length; i++) {
                // Point - 2
                // for문에서의 조건문 처리
                // for문 첫 실행을 제외하고는 n > 0을 검사하지 않기 때문에
                // => (n <=0) break; 추가
                if (n <= 0) break;
                // Point - 3
                // 마지막 요소는 따로 처리
                if (i == workArr.length - 1) {
                    if(workArr[i] >= workArr[0]) {
                        workArr[i] -= 1;
                        n--;
                        break;
                    }
                } else {
                    // Point - 4
                    // while문에서의 조건문 처리
                    // while문 첫 실행을 제외하고는 위에 있는 if (n<= 0) break;를 처리하지 못하기 때문에
                    // => (n > 0) 조건문 추가
                    while (workArr[i] >= workArr[i + 1] && n > 0) {
                        // Point - 5
                        // 작업량 감소 여부 기준은 작업량이 제일 많은 요소보다 크거나 같은지
                        if (i != 0 && workArr[i] < workArr[0]) break;
                        workArr[i] -= 1;
                        n--;
                    }
                }
            }
        }
        for (int i = 0; i < workArr.length; i++) {
            answer += Math.pow(workArr[i], 2);
        }
        return answer;
    }

    // Accuracy Test ~3.46ms, 74.6MB, Efficiency Test ~143.96ms, 68.5MB
    // 우선순위 큐(Max Heap) 이용
    public long exam1(int n, int[] works) {
        long answer = 0;
        // n >= sum인 경우 따로 바로 return
        if (n >= Arrays.stream(works).sum()) return 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < works.length; i++) {
            pq.add(works[i]);
        }

        while (n > 0) {
            int num = pq.poll();
            num--;
            n--;
            pq.add(num);
        }
        for (Integer p : pq) {
            answer += Math.pow(p, 2);
        }

        return answer;
    }

    // Accuracy Test ~7.80ms, 74.2MB, Efficiency Test ~136.06ms, 67.8MB
    // 남은 작업량을 음수로 처리하여 0일 때까지 감소 작업
    public long exam2(int n, int[] works) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int w : works) pq.add(-w);
        while (n-- > 0 && pq.peek() < 0) pq.add(1 + pq.poll());
        return pq.stream().mapToLong(i -> (long) i * i).sum(); // 야근피로도 계산
    }

    public static void main (String[] args) {
        PG_12927 pg_12927 = new PG_12927();
        // expected : 12
//        System.out.println(pg_12927.codeOfMine(4, new int[]{4,3,3}));
//        // expected : 6
//        System.out.println(pg_12927.codeOfMine(1, new int[]{2,1,2}));
//        // expected : 0
//        System.out.println(pg_12927.codeOfMine(3, new int[]{1,2}));
        // expected : 75
        System.out.println(pg_12927.exam2(7, new int[]{10,5,7}));
        // ** 작업량 감소 기준이 작업량이 제일 많은 요소가 아닐 때 반례 **
        // e.g. workArr[i] < workArr[0]? break (O) workArr[i] < workArr[i-1]? break (X)
        // expected : 580
//        System.out.println(pg_12927.codeOfMine(99, new int[]{2,15,22,55,55}));
    }
}
