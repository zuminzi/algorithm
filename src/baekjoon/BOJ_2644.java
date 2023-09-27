package baekjoon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class BOJ_2644 {
    private static int ans, n, m;

    /**
     * 가계도(부모-자식 관계)를 기반으로 두 사람 간의 촌수 또는 친척 거리를 계산하기 위한 DFS 알고리즘
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int target1 =  Integer.parseInt(st.nextToken());
        int target2 = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        int[][] edges = new int[m][2];
        int startIdx1 = -1;
        int startIdx2 = -1;
        ans = -1;

        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            edges[i][0] = Integer.parseInt(st.nextToken());
            edges[i][1] = Integer.parseInt(st.nextToken());

            if(edges[i][1] == target1){
                startIdx1 = i;
            } else if(edges[i][1] == target2){
                startIdx2 = i;
            }
        }

        // bottom-up 방식으로 각각의 조상 관리
        List<Integer> ancestors1 = findAncestors(edges, startIdx1, target1, target2, 0, new ArrayList<>());
        List<Integer> ancestors2 = findAncestors(edges, startIdx2, target2, target1, 0, new ArrayList<>());

        int count = 0;
        if(ans == -1){
            Set<Integer> intersection = ancestors1.stream()
                    .distinct()
                    .filter(ancestors2::contains)
                    .collect(Collectors.toSet());

            if(!intersection.isEmpty()) {
                for (int a : ancestors1) {
                    count++; // 아래서부터 위로 촌수세기
                    if (ancestors2.contains(a)) {
                        count += ancestors2.indexOf(a) - 1; // 중복되는 부모 제외하고 위에서부터 아래로 촌수세기
                        break;
                    }
                }
            }
            ans = count == 0 ? ans : count;
        }

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static List<Integer> findAncestors(int[][] edges, int startIdx, int start, int other, int count, ArrayList<Integer> ancestors) {
        if(ancestors.isEmpty()){ // 리스트가 비어있으면
            ancestors.add(start); // 자기 자신부터 넣기
        }

        if(startIdx == -1){
            return ancestors;
        }

        if(edges[startIdx][0] == other){ // 현재 포인터의 자식이 찾는 상대방이면
            ans = count + 1;
            return ancestors;
        }

        int parent = edges[startIdx][0];
        ancestors.add(parent);

        for(int i=0; i<m; i++) {
            if(edges[i][1] == parent) { // 조부모면
                findAncestors(edges, i, start, other, count + 1, ancestors);
                break;
            }
        }
        return ancestors;
    }
}
