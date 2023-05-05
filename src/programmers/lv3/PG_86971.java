package programmers.lv3;

import java.util.*;

/**
 * input : 송전탑의 개수 n, 전선 정보 wires
 * output : 두 전력망이 가지고 있는 송전탑의 개수의 차이(절댓값)
 - 트리형태(순환 구조를 갖지 않는 그래프)
 - 전선들 중 하나를 끊어서 두 전력망이 갖는 송전탑의 개수 최대한 비슷하게 맞추고자 합니다.
 */
public class PG_86971 {
    // ~ 14.03ms, 79.9MB
    private static Map<Integer, List<Integer>> map; // 인접 노드 맵
    private static PriorityQueue<Integer> pq;
    private int n;
    public int solution(int n, int[][] wires) {
        this.n = n;
        map = new HashMap<>();
        pq = new PriorityQueue<>(); // ASC
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            map.put(i + 1, new ArrayList<>());
            arr[i] = i + 1;
        }
        for (int i = 0; i < wires.length; i++) {
            List<Integer> list = map.get(wires[i][0]);
            list.add(wires[i][1]);
            map.put(wires[i][0], list);

            list = map.get(wires[i][1]);
            list.add(wires[i][0]);
            map.put(wires[i][1], list);
        }

        getSubsetOfSize2(arr, new boolean[arr.length], 0, 2);

        return pq.poll();
    }

    // 원소의 개수가 2인 부분집합 구하기 (조합, 백트래킹)
    private void getSubsetOfSize2(int[] arr, boolean[] visited, int start, int r) {
        if (r == 0) {
            calcDiff(arr, visited);
            return;
        }

        for (int i = start; i < visited.length; i++) {
            visited[i] = true;
            getSubsetOfSize2(arr, visited, i + 1, r - 1);
            visited[i] = false;
        }
    }

    // 각 전력망의 송전탑 개수 차이 계산
    private void calcDiff(int[] arr, boolean[] visited) {
        int[] keyArr = new int[2];
        boolean isFirst = true;
        for (int i = 0; i < visited.length; i++) {
            if (visited[i]) {
                if (isFirst) {
                    keyArr[0] = arr[i];
                    isFirst = false;
                } else {
                    keyArr[1] = arr[i];
                }
            }
        }
        if (map.get(keyArr[0]).contains(keyArr[1]) && map.get(keyArr[1]).contains(keyArr[0])) {
            boolean[] visited_clone = visited.clone();
            // 하나의 서브 트리 크기(자손 노드 개수) 계산하면 다른 노드의 서브 트리 크기 계산 가능
            int childNum1 = calcSubTreeSize(visited_clone, keyArr[0]);
            int childNum2 = n - childNum1 - 2;
            pq.add(Math.abs(childNum1 - childNum2));
        }
    }

    private int calcSubTreeSize(boolean[] visited, int key) {
        Queue<Integer> q = new LinkedList<>();
        int count = 0;
        q.add(key);

        while (!q.isEmpty()) {
            List<Integer> list = map.get(q.poll());
            for (int i = 0; i < list.size(); i++) {
                int child = list.get(i);
                if (!visited[child - 1]) {
                    visited[child - 1] = true;
                    q.add(child);
                    count++;
                }
            }
            if (q.isEmpty()) return count;
        }
        throw new IllegalArgumentException();
    }

    public static void main (String[] args){
        PG_86971 pg_86971 = new PG_86971();
        // expected : 3
        System.out.println(pg_86971.solution(9, new int[][]{{1,3},{2,3},{3,4},{4,5},{4,6},{4,7},{7,8},{7,9}}));
        // expected : 0
        System.out.println(pg_86971.solution(4, new int[][]{{1,2},{2,3},{3,4}}));
        // expected : 1
        System.out.println(pg_86971.solution(7, new int[][]{{1,2},{2,7},{3,7},{3,4},{4,5},{6,7}}));
    }
}
