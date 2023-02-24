package programmers.LV2;

import java.util.LinkedList;
import java.util.Queue;

public class PG_43165 {

    // Exam 1 - DFS
    /*
    if numbers.length == 5
    (+) -> (+) -> (+) -> (+) -> (+) // -> (-) -> (-) -> (-) -> (-) -> (-)
     */
    // Accuracy Test ~7.69ms, 80.2MB
    static int result = 0;
    public int exam1(int[] numbers, int target) {
        dfs(0, numbers, target, 0);
        return result;
    }

    private static void dfs(int index, int[] num, int target, int sum) {
        if(index == num.length) {
            if(target == sum) {
                result++;
            }
            return;
        }
        // (target == num)이 될 때까지 재귀함수 호출
        dfs(index + 1, num, target, sum + num[index]);
        // +로 (target == num)이 되지 않았다면 - 연산하는 분기(branch) 생성
        dfs(index + 1, num, target, sum - num[index]);
    }


    // Exam 2 - BFS
    /*
    if numbers.length == 5
    (+) -> (+) -> (+)
               -> (-)
        -> (-) -> (+) ..
               -> (-)

    (-) -> (+) -> (+)
               -> (-)
        -> (-) -> (+) ..
               -> (-)
     */
    // Accuracy Test ~300.49ms, 180MB
    class Pair {
        int current;
        int index;

        Pair(int current, int index) {
            this.current = current;
            this.index = index;
        }
    }

    public int exam2(int[] numbers, int target) {
        int answer = 0;

        Queue<Pair> queue = new LinkedList<Pair>();
        queue.offer(new Pair(numbers[0], 0));
        queue.offer(new Pair(-numbers[0], 0));

        while (!queue.isEmpty()) { // 제거하는 루프문이므로 for문(X) while문(O)
            Pair p = queue.poll();

            if (p.index == numbers.length-1) { // 마지막 인덱스라면
                if (p.current == target) {
                    answer += 1;
                }
                continue;
            }

            // 다음 인접 노드 방문
            int c1 = p.current + numbers[p.index+1];
            int c2 = p.current - numbers[p.index+1];

            queue.add(new Pair(c1, p.index+1));
            queue.add(new Pair(c2, p.index+1));
        }

        return answer;
    }

    public static void main(String[] args){
        PG_43165 pg_43165 = new PG_43165();
        System.out.println(pg_43165.exam2(new int[]{1,1,1,1,1},3)); // expected : 5
        System.out.println(pg_43165.exam2(new int[]{4,1,2,1},4)); // expected : 2
    }
}
