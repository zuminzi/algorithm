package programmers.LV2;

import java.util.Arrays;
import java.util.Comparator;

// input : dungeons[][0]은 최소피로도, dungeons[][1]은 소모피로도
// 최소피로도가 기준값 K보다 크거나 같다면 무조건 앞에서 비교하는 것이 좋다고 생각하여 설계 // 뒤로 가면 기준 충족이 아예 안되므로? -> 확인 필요
// 만일 최소피로도가 같은 경우에는 소모피로도가 작은 순으로 비교하는 것이 좋다고 생각하여 설계 // 최대한 많은 dungeon을 탐색할 가능성이 높아짐? -> 확인 필요
public class PG_87946 {
    // ~5.87ms, 73.6MB
    static int maxCnt;
    public int solution(int k, int[][] dungeons) {
        maxCnt = 0;
        Arrays.sort(dungeons, (o1,o2) -> {
            if(o1[0] == o2[0]){
                return Integer.compare(o1[1],o2[1]); // 오름차순 정렬
            } else {
                return Integer.compare(o2[0], o1[0]); // 내림차순 정렬
            }
        });
        // index를 -1로 설정한 이유
        // 처음 index가 0일 때 isSelected true 기록을 안 한 채로 min(최소필요도)에서 마이너스 계산을 하지 않도록
        boolean[] isSelected = new boolean[dungeons.length];
        dfs(-1, dungeons, isSelected, k, 0);

        return maxCnt;
    }

    private static void dfs(int index, int[][] arr, boolean[] isSelected, int min, int index2) {
        if(index >= 0) {
            if (arr[index2][0] > min || arr[index2][1] > min) {
                return;
            } else {
                min -= arr[index2][1];

                int trueCnt = 0;
                for(int k=0; k<isSelected.length; k++){
                    if(isSelected[k]){
                        trueCnt++;
                    }
                }
                maxCnt = Math.max(trueCnt, maxCnt);
            }
        }

        for (int i = 0; i < arr.length; i++) {
            if(maxCnt == arr.length) break;
            if (isSelected[i]) continue;
            isSelected[i] = true;
            dfs(index + 1, arr, isSelected, min, i);
            isSelected[i] = false;
        }
    }

    public static void main(String[] args){
        PG_87946 pg_87946 = new PG_87946();
        System.out.println(pg_87946.solution(80, new int[][]{{80,20},{50,40},{30,10}}));
    }
}