package programmers.lv3;

import java.util.*;

/*
 * @param info 각 노드에 있는 양 또는 늑대에 대한 정보가 담긴 배열 (0은 양, 1은 늑대를 의미합니다.)
 * @param edges 2진 트리의 각 노드들의 연결 관계를 담은 2차원 배열 [부모 노드 번호, 자식 노드 번호]
 * @return 모을 수 있는 양 최댓값
 * 당신이 모은 양의 수보다 늑대의 수가 같거나 더 많아지지 않도록 하면서 최대한 많은 수의 양을 모아서 루트 노드로 돌아오도록 합니다.
 - 풀이
    - 완전탐색
       - 노드번호로 순열 생성(DFS) -> 각 순열에 대해 가능한 모든 경우 탐색
    - 그래프 탐색에서 중요한 코드는 <탐색이 중단되는 return 코드>
    - 예제를 통해 모든 노드를 방문할 필요는 없다는 점 확인
    - 예제를 통해 순회 자식노드뿐만 아니라 형제 노드도 같은 탐색 순위임을 확인
       - 즉, 순열 생성은 DFS를 수행하고 있지만, 노드 탐색은 형제 노드와 자식 노드를 모두 방문하는 혼합적인 방식으로 수행
 */
public class PG_92343 {

    // ~97.89ms, 142MB
    private class TreeNode {
        int value;
        TreeNode parent;
        List<TreeNode> children;

        private TreeNode(int value) {
            this.value = value;
            this.parent = null;
            this.children = new ArrayList<>();
        }
    }

    private List<TreeNode> findSiblingsAndParent(TreeNode node) {
        List<TreeNode> siblingsAndParent = new ArrayList<>();

        // 형제 노드 조회
        if (node.parent != null) {
            TreeNode parent = node.parent;
            for (TreeNode child : parent.children) {
                if (child != node) {
                    siblingsAndParent.add(child);
                }
            }
        }

        // 부모 노드 조회
        if (node.parent != null) {
            siblingsAndParent.add(node.parent);
        }

        return siblingsAndParent;
    }

    private static TreeNode[] nodes;
    private static int[] info;
    private static int answer;

    public int solution(int[] info, int[][] edges) {
        this.info = info;
        answer = 0;

        int n = info.length; // 노드 개수
        this.nodes = new TreeNode[n + 1]; // 노드를 인덱스로 접근하기 위한 배열 생성

        for (int i = 0; i < n; i++) {
            nodes[i] = new TreeNode(i);
        }

        for (int[] edge : edges) {
            int parentIndex = edge[0];
            int childIndex = edge[1];

            TreeNode parent = nodes[parentIndex];
            TreeNode child = nodes[childIndex];

            parent.children.add(child);
            child.parent = parent;
        }

        int[] arr = new int[info.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        perm(arr, new int[info.length], new boolean[info.length], 0, 0, 0);
        return answer;
    }

    private void perm(int[] arr, int[] output, boolean[] visited, int depth, int sheep, int wolf) {
        // 탐색 가능한 노드인지 확인
        if (wolf < sheep) {
            List<Integer> ancestor = new ArrayList<>();
            // problem point
            // arr과 visited 배열을 활용하지 않고, output 배열을 확인해야 순회한 순서대로 확인 가능
            for (int i = 0; i < output.length; i++) {
                if (i != 0 && output[i] == 0) break;
                boolean navigable = false;
                for (int k = 0; k < ancestor.size(); k++) {
                    TreeNode targetNode = nodes[output[i]];
                    List<TreeNode> siblingsAndParent = findSiblingsAndParent(targetNode);

                    if (siblingsAndParent.contains(nodes[ancestor.get(k)])) {
                        navigable = true;
                        break;
                    }
                }
                if (i != 0 && !navigable) return;
                ancestor.add(output[i]);
            }
            answer = Math.max(answer, sheep);
        }

        if (sheep != 0 && wolf >= sheep || depth > arr.length) return;

        for (int i = 0; i < arr.length; i++) {
            if (depth == 0 && arr[i] != 0) continue;
            if (visited[i] != true) {
                visited[i] = true;
                output[depth] = arr[i];
                boolean isSheep = info[output[depth]] == 0 ? true : false;
                if (isSheep) {
                    sheep++;
                } else {
                    wolf++;
                }
                perm(arr, output, visited, depth + 1, sheep, wolf);
                output[depth] = 0;
                visited[i] = false;
                if (isSheep) {
                    sheep--;
                } else {
                    wolf--;
                }
            }
        }
    }

    // ~8.10ms, 90.8MB
    List<List<Integer>> adj;
    int[] ginfo;
    int tsheep, twolf;

    public int exam1(int[] info, int[][] edges) {

        ginfo = info;
        int n = info.length;
        adj = new ArrayList<>(n);
        for(int i=0; i<n; ++i) {
            adj.add(new LinkedList<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
        }

        DFS(0, 0, 0, new ArrayList<>());

        return tsheep;
    }

    public void DFS(int num, int sheep, int wolf, List<Integer> nodes) {
        int cs = sheep + (ginfo[num] ^ 1);
        int cw = wolf + ginfo[num];

        if(cs > cw) {
            if(tsheep < cs) {
                tsheep = cs;
                twolf = cw;
            }
            nodes.addAll(adj.get(num));
            for (int i=0; i<nodes.size(); ++i) {
                int node = nodes.remove(i);
                dfs(node, cs, cw, nodes);
                nodes.add(i, node);
            }
            nodes.removeAll(adj.get(num));
        }
    }
    // ~5.76ms, 75.4MB
    // 해당 IDX의 자식은 누가 있는지
    static ArrayList<Integer>[] childs;
    static int[] Info;
    static int maxSheepCnt = 0;

    public static int exam2(int[] info, int[][] edges) {
        Info = info;
        childs = new ArrayList[info.length];
        for (int[] l : edges) {
            int parent = l[0];
            int child = l[1];
            if (childs[parent] == null) {
                childs[parent] = new ArrayList<>();
            }
            childs[parent].add(child);
        }

        List<Integer> list = new ArrayList<>();
        list.add(0);
        dfs(0, 0, 0, list);
        return maxSheepCnt;
    }

    private static void dfs(int idx, int sheepCnt, int wolfCnt, List<Integer> nextPos) {
        // 늑대/양 수, 양의 최대값 최신화
        if (Info[idx] == 0) sheepCnt++;
        else wolfCnt++;

        if (wolfCnt >= sheepCnt) return;
        maxSheepCnt = Math.max(sheepCnt, maxSheepCnt);

        // 다음 탐색 위치 갱신
        List<Integer> list = new ArrayList<>();
        list.addAll(nextPos);
        // 다음 탐색 목록중 현재 위치제외
        list.remove(Integer.valueOf(idx));
        if (childs[idx] != null) {
            for (int child : childs[idx]) {
                list.add(child);
            }
        }

        // 갈수 있는 모든 Node Dfs
        for (int next : list) {
            dfs(next, sheepCnt, wolfCnt, list);
        }
    }

    // ~2.71ms, 66.8MB
    public static List<Integer> sheeps;
    public static int[] infos;
    public static int[] visited;
    public static int[][] pubEdges;
    public int exam3(int[] info, int[][] edges) {
        int answer = 0;
        infos = info;
        visited = new int[info.length];
        sheeps = new ArrayList<>();
        pubEdges = edges;

        visited[0] = 1;
        dfs(1,0);

        int maxnum = 0;
        for(int num : sheeps){
            if(maxnum < num) maxnum = num;
        }

        return maxnum;
    }

    public void dfs(int sheep, int wolf){
        if(sheep <= wolf){
            return;
        } else {
            sheeps.add(sheep);
        }

        for(int[] edge : pubEdges){
            if(visited[edge[0]] == 1 && visited[edge[1]] == 0) {
                visited[edge[1]] = 1;
                if(infos[edge[1]] == 0){
                    dfs(sheep+1,wolf);
                } else {
                    dfs(sheep,wolf+1);
                }
                visited[edge[1]] = 0;
            }
        }
    }

    // ~6.22ms, 79.6MB
    int ans = 0;
    public int exam4(int[] info, int[][] edges) {
        dfs(0, new boolean[info.length], 0, 0, info, edges);
        return ans;
    }

    private void dfs(int idx, boolean[] visited, int sheep, int wolf, int[] info, int[][] edges) {
        visited[idx] = true;

        if(info[idx] == 0) {
            sheep++;
            ans = Math.max(sheep, ans);
        } else {
            wolf++;
        }

        if(sheep <= wolf) {
            return;
        }

        for (int[] edge : edges) {
            if(visited[edge[0]] && !visited[edge[1]]) {
                boolean[] newVisited = visited.clone();
                dfs(edge[1], newVisited, sheep, wolf, info, edges);
            }
        }
    }

    public static void main (String[] args) {
        PG_92343 pg_92343 = new PG_92343();
        // expected : 5
        System.out.println(pg_92343.solution(new int[]{0,0,1,1,1,0,1,0,1,0,1,1},
                                new int[][]{{0,1},{1,2},{1,4},{0,8},{8,7},{9,10},{9,11},{4,3},{6,5},{4,6},{8,9}}));
        // expected : 5
        System.out.println(pg_92343.solution(new int[]{0,1,0,1,1,0,1,0,0,1,0},
                                new int[][]{{0,1},{0,2},{1,3},{1,4},{2,5},{2,6},{3,7},{4,8},{6,9},{9,10}}));
    }
}
