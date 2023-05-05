package programmers.lv3;

import java.util.*;

/**
 * input : 송전탑의 개수 n, 전선 정보 wires
 * output : 두 전력망이 가지고 있는 송전탑의 개수의 차이(절댓값)
 - 트리형태(순환 구조를 갖지 않는 그래프)
 - 전선들 중 하나를 끊어서 두 전력망이 갖는 송전탑의 개수 최대한 비슷하게 맞추고자 합니다.
 */
public class PG_86971 {
    // improved solution
    // ~6.71ms, 81.5MB
    private static Map<Integer, List<Integer>> adjacencyMap;
    private static int treeSize;
    public int sol(int n, int[][] wires) {
        this.treeSize = n;
        adjacencyMap = new HashMap<>();
        int minDiff = Integer.MAX_VALUE;

        // 초기화
        for (int i = 0; i < wires.length; i++) {
            adjacencyMap.putIfAbsent(wires[i][0], new ArrayList<>());
            adjacencyMap.putIfAbsent(wires[i][1], new ArrayList<>());

            List<Integer> list = adjacencyMap.get(wires[i][0]);
            list.add(wires[i][1]);
            adjacencyMap.put(wires[i][0], list);

            list = adjacencyMap.get(wires[i][1]);
            list.add(wires[i][0]);
            adjacencyMap.put(wires[i][1], list);
        }

        // 인접노드 서브트리 크기 차이 계산
        // 따로 요소가 2인 부분집합을 구할 필요 없이 wires(edges) 배열 이용
        for(int i=0; i<wires.length; i++) {
            int v1 = wires[i][0];
            int v2 = wires[i][1];
            int[] arr = {v1, v2};
            boolean[] visited = new boolean[n];
            visited[v1-1] = true;
            visited[v2-1] = true;
            minDiff = Math.min(minDiff, calcSubTreeSizeAndGetDiff(arr, visited));
        }
        return minDiff;
    }

    private int calcSubTreeSizeAndGetDiff(int[] arr, boolean[] visited) {
        Queue<Integer> q = new LinkedList<>();
        int count = 0;
        q.add(arr[0]);

        while (!q.isEmpty()) {
            List<Integer> list = adjacencyMap.get(q.poll());
            for (int i = 0; i < list.size(); i++) {
                int child = list.get(i);
                if (!visited[child - 1]) {
                    visited[child - 1] = true;
                    q.add(child);
                    count++;
                }
            }
            if (q.isEmpty()) {
                int subTreeSize = count;
                int otherSubTreeSize = treeSize - subTreeSize - 2;
                return Math.abs(subTreeSize - otherSubTreeSize);
            }
        }
        throw new IllegalArgumentException();
    }

    // existing solution
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
            // Point - clone해서 사용하지 않고 visited 배열을 바로 사용하면 기존 visited 배열에 영향을 줌
            // calcSubTreeSize 메서드에서 visited 처리를 추가로 하고 있으므로
            boolean[] visited_clone = visited.clone();
            // 하나의 서브 트리 크기(자손 노드 개수) 계산하면 다른 노드의 서브 트리 크기 계산 가능
            int subTreeSize = calcSubTreeSize(visited_clone, keyArr[0]);
            int otherSubTreeSize = n - subTreeSize - 2;
            pq.add(Math.abs(subTreeSize - otherSubTreeSize));
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

    // exam1
    // ~0.88ms, 81.4MB
    // 전선 하나를 끊는다는 의미를 트리에서 부모 노드로 이동하지 않는다라는 의미로 생각
    // 노드의 총합은 n이기 때문에 자식 노드 child를 루트로 가진 트리의 개수를 안다면 문제에서 요구한 네트워크의 차이를 구할 수 있다.
    // 리프노드에서부터 시작해서 root까지 각 서브 트리의 요소들의 개수를 구하면 dp를 이용할 수 있다.
    // 왜냐하면 부모 트리 요소들의 합은 1 + 자식 트리들의 요소들의 합이기 때문이다.
    // 상위 계층(root에 가까운)의 연결이 끊어짐으로 하위 계층(Leaf에 가까운)의 서브 트리 요소들의 개수는 영향을 받지 않는다.
    public int exam1(int n, int[][] wires) {
        init(wires);
        min = n;
        allTreeCount = n;

        int count = treeCount(wires[0][0]);
        if(allTreeCount != count){
            throw new RuntimeException("로직이 이상한 것");
        }

        return min;
    }

    public void init(int[][] wires){
        for (int[] wire : wires) {
            examMap.putIfAbsent(wire[0], new HashSet<>());
            examMap.putIfAbsent(wire[1], new HashSet<>());
            examMap.get(wire[0]).add(wire[1]);
            examMap.get(wire[1]).add(wire[0]);
        }
    }
    Map<Integer, Set<Integer>> examMap = new HashMap<>();
    Set<Integer> visit = new HashSet<>();

    int allTreeCount;
    int min;
    public int treeCount(int root){
        visit.add(root);

        int count = 1;

        for (Integer childNode : examMap.get(root)) {
            if(visit.contains(childNode)) continue;
            count += treeCount(childNode); // 단말노드를 만날 때까지 재귀호출
        }
        int otherTreeCount = allTreeCount - count;
        min = Math.min(min, Math.abs(count - otherTreeCount));

        return count;
    }

    // exam2
    // ~2.21ms, 76MB
    // Map<Integer,List<Integer>> 대신 List 타입 배열 List<Integer>[] 사용 **
    static ArrayList<Integer>[] list;
    static int minDiff;
    static boolean[] visited;

    static void dfs(int n, int v1, int v2) {
        // list[1]부터 시작해서 인접 노드 그래프 순회
        for(int num : list[n]) {
            if((n==v1 && num==v2) || (n==v2 && num==v1)) continue; // 끊어내려는 인접 요소일 경우 제외하고 카운트
            if(!visited[num]) {
                visited[num] = true;
                dfs(num,v1,v2);
            }
        }
    }

    public int exam2(int n, int[][] wires) {
        list = new ArrayList[n+1];
        minDiff = Integer.MAX_VALUE/2;
        for(int i=1; i<=n; i++) {
            list[i] = new ArrayList<>();
        }

        for(int i=0; i<wires.length; i++) {
            int v1 = wires[i][0];
            int v2 = wires[i][1];
            list[v1].add(v2);
            list[v2].add(v1);
        }

        for(int i=0; i<wires.length; i++) {
            int v1 = wires[i][0];
            int v2 = wires[i][1];
            visited = new boolean[n+1];
            visited[1] = true;

            dfs(1,v1,v2);

            int cnt = 0;
            // 방문 노드 검사하여 서브 트리 크기 계산
            for(int j=1; j<visited.length; j++) {
                if(visited[j]) cnt++;
            }
            minDiff = Math.min(minDiff, Math.abs(n-(2*cnt)));
        }
        return minDiff;
    }

    public static void main (String[] args){
        PG_86971 pg_86971 = new PG_86971();
        // expected : 3
        System.out.println(pg_86971.exam1(9, new int[][]{{1,3},{2,3},{3,4},{4,5},{4,6},{4,7},{7,8},{7,9}}));
        // expected : 0
        System.out.println(pg_86971.exam1(4, new int[][]{{1,2},{2,3},{3,4}}));
        // expected : 1
        System.out.println(pg_86971.exam1(7, new int[][]{{1,2},{2,7},{3,7},{3,4},{4,5},{6,7}}));
    }
}
