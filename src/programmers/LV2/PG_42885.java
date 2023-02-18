package programmers.LV2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class PG_42885 {
        // success Accuracy and Efficiency Test, but over time to solve
        public int success_sol(int[] people, int limit) {
            int boatCnt = 0;
            Deque<Integer> deque = new ArrayDeque<>();

            Arrays.sort(people);
            for (int i = 0; i < people.length; i++) {
                deque.add(people[i]);
            }

            // Efficiency Test Point
            while (deque.isEmpty() == false) {
                if (deque.isEmpty()) break;
                if (deque.size() == 1) {
                    deque.poll();
                    boatCnt++;
                    break;
                }

                // 전위 포인터로 뽑은 요소는 무조건 혼자라도 탑승(boatCnt++)
                int first = deque.pollFirst();
                boatCnt++;

                // 후위 포인터로 뽑은 요소는 같이 탈 수 있으면 같이 탑승, 아니면 홀로 탑승(boatCnt++)
                int last = deque.pollLast();
                if (first + last <= limit) {
                    continue;
                } else {
                    boatCnt++;
                    while (first + last > limit) {
                        if (deque.isEmpty()) break;

                        last = deque.pollLast();

                        if (first + last <= limit) {
                            break;
                        } else {
                            boatCnt++;
                        }
                    }
                }
            }
            return boatCnt + deque.size();
        }

    // success Accuracy Test, fail Efficiency Test
    public int fail_sol_2(int[] people, int limit) {
        int boatCnt = 0;

        Arrays.sort(people);

        for(int i=0; i< people.length; i++){
            if(people[i] == -1){continue;}

            for(int k= people.length - 1; k>i; k--){
                if(people[k] == -1){continue;}
                else {
                    if (people[k] <= limit - people[i]) {
                        if(i==0 & k == i+1){
                            return people.length - 1;
                        }
                        people[i] = -1;
                        people[k] = -1;
                        boatCnt++;
                        break;
                    }
                }
            }
            if(people[i] != -1) {
                // Efficiency Test Point - 1
                // 가벼운 사람부터 줄 세운 후 맨 앞 사람이 짝 지을 사람이 없으면, 모든 케이스에서 짝은 나올 수 없다
                if(i==0){return people.length;}

                people[i] = -1;
                boatCnt++;
            }
        }
        return boatCnt;
    }


    public static void main(String[] args){
        PG_42885 pg_42885 = new PG_42885();
        System.out.println(pg_42885.success_sol(new int[]{70, 50, 80, 50}, 100)); // expected : 3
        System.out.println(pg_42885.success_sol(new int[]{70, 80, 50}, 100)); // expected : 3
        System.out.println(pg_42885.success_sol(new int[]{40, 50, 60, 90}, 100)); // expected : 3
        System.out.println(pg_42885.success_sol(new int[]{40,50,60,150,160}, 200)); // expected : 3
        System.out.println(pg_42885.success_sol(new int[]{100,500,500,900,950}, 1000)); // expected : 3
        System.out.println(pg_42885.success_sol(new int[]{40}, 40)); // expected : 1
        System.out.println(pg_42885.success_sol(new int[]{40, 55, 55, 60, 60, 60, 70, 80}, 100)); // expected : 7
    }
}
