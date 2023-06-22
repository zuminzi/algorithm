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
