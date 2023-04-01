package programmers.lv2;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class PG_42885 {
        // success Accuracy and Efficiency Test, but over time to solve
        // Accuracy Test ~5.26ms, 84.1MB, Efficiency Test ~25.52ms, 57.6MB
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

    // Accuracy Test ~1.76ms, 77.5MB, Efficiency Test ~9.52ms, 56MB
    public int exam1(int[] people, int limit) {
        Arrays.sort(people);

        int i = 0;
        int j = people.length - 1;

        for (; i < j; --j) {
            if (people[i] + people[j] <= limit)
                ++i;
        }
        // 전체 - 같이 타는 사람/2 (i는 전위 포인터)
        return people.length - i;
    }

    // Accuracy Test ~1.72ms, 73.2MB, Efficiency Test ~11.28ms, 56.5MB
    public int exam2(int[] people, int limit) {
        Arrays.sort(people);

        int i = people.length - 1;
        int j = 0;

        while (i > j) {
            if (people[i] + people[j] <= limit) {
                i--;
                j++;
            }
            else {
                i--;
            }
        }

        return people.length - j;
    }

    // Accuracy Test ~3.72ms, 88MB, Efficiency Test ~23.86ms, 54.7MB
    /* 기존에 있던
                if(!deque.isEmpty()) {
        if(deque.peekLast() > Math.ceil((double) limit / 2.0)) {
            break;
        }
    }
    코드를 제거하니 효율성 증가
     */
    public int exam3_refactor(int[] people, int limit) {
        Arrays.sort(people);
        int count = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = people.length-1; i >= 0; i--) {
            deque.add(people[i]);
        }

        while(!deque.isEmpty()) {
            int sum = deque.peekFirst() + deque.peekLast();
            if(sum > limit) {
                deque.pollFirst();
                count++;
            } else {
                deque.pollFirst();
                deque.pollLast();
                count++;
            }
        }

        if(!deque.isEmpty()) {
            count += deque.size();
        }
        return count;
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
