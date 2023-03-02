package programmers.LV2;

// input : dungeons[][0]은 최소피로도, dungeons[][1]은 소모피로도
// output : 최대 던전 수
public class PG_87946 {
    // ~5.87ms, 73.6MB
    static int maxCnt;
    public int codeOfMine_refactor(int k, int[][] dungeons) {
        maxCnt = 0;
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
        System.out.println(pg_87946.codeOfMine_refactor(80, new int[][]{{80,20},{50,40},{30,10}}));
    }
}



/*
 * 첫번째 요소 내림차순 정렬 후 탐색 시
 - Arrays.sort(dungeons, Comparator.comparingInt(o1 -> o1[0]));

테스트 1 〉	통과 (2.07ms, 69.8MB)
테스트 2 〉	통과 (2.16ms, 76.3MB)
테스트 3 〉	통과 (4.52ms, 80MB)
테스트 4 〉	통과 (3.18ms, 81.5MB)
테스트 5 〉	통과 (2.88ms, 73.9MB)
테스트 6 〉	통과 (4.14ms, 80.2MB)
테스트 7 〉	통과 (6.02ms, 77.1MB)
테스트 8 〉	통과 (10.52ms, 81MB)
테스트 9 〉	통과 (2.09ms, 74.2MB)
테스트 10 〉	통과 (2.93ms, 71.4MB)
테스트 11 〉	통과 (2.08ms, 78.1MB)
테스트 12 〉	통과 (3.84ms, 72.8MB)
테스트 13 〉	통과 (5.28ms, 76.9MB)
테스트 14 〉	통과 (3.73ms, 69.8MB)
테스트 15 〉	통과 (2.49ms, 75.3MB)
테스트 16 〉	통과 (2.40ms, 74.1MB)
테스트 17 〉	통과 (3.83ms, 88.5MB)
테스트 18 〉	통과 (2.21ms, 73.7MB)
테스트 19 〉	통과 (2.43ms, 76.3MB)
 */
/*
 * 첫번째 요소 내림차순 정렬, 두번째 요소 오름차순 정렬 후 탐색 시
 -       Arrays.sort(dungeons, (o1,o2) -> {
            if(o1[0] == o2[0]){
                return Integer.compare(o1[1],o2[1]);
            } else {
                return Integer.compare(o2[0], o1[0]);
            }
        });

테스트 1 〉	통과 (0.67ms, 76.8MB)
테스트 2 〉	통과 (0.63ms, 71.8MB)
테스트 3 〉	통과 (0.73ms, 78.5MB)
테스트 4 〉	통과 (0.76ms, 72.8MB)
테스트 5 〉	통과 (0.93ms, 68.2MB)
테스트 6 〉	통과 (1.29ms, 75.7MB)
테스트 7 〉	통과 (2.95ms, 75.7MB)
테스트 8 〉	통과 (5.87ms, 73.6MB)
테스트 9 〉	통과 (0.62ms, 74.5MB)
테스트 10 〉	통과 (1.26ms, 72.9MB)
테스트 11 〉	통과 (1.17ms, 66.9MB)
테스트 12 〉	통과 (2.19ms, 84.5MB)
테스트 13 〉	통과 (1.01ms, 77.9MB)
테스트 14 〉	통과 (0.84ms, 70.1MB)
테스트 15 〉	통과 (0.62ms, 83.6MB)
테스트 16 〉	통과 (0.65ms, 76MB)
테스트 17 〉	통과 (0.81ms, 80.2MB)
테스트 18 〉	통과 (0.49ms, 80.8MB)
테스트 19 〉	통과 (0.45ms, 78.4MB)
 */
/*
 * 정렬 안하고 탐색 시 [BEST]

테스트 1 〉	통과 (0.04ms, 75.3MB)
테스트 2 〉	통과 (0.06ms, 76MB)
테스트 3 〉	통과 (0.06ms, 78.8MB)
테스트 4 〉	통과 (0.04ms, 73MB)
테스트 5 〉	통과 (0.63ms, 70.8MB)
테스트 6 〉	통과 (1.44ms, 73.4MB)
테스트 7 〉	통과 (3.84ms, 79.6MB)
테스트 8 〉	통과 (5.89ms, 75.2MB)
테스트 9 〉	통과 (0.03ms, 75.4MB)
테스트 10 〉	통과 (0.65ms, 78.3MB)
테스트 11 〉	통과 (0.03ms, 75.1MB)
테스트 12 〉	통과 (0.75ms, 72.5MB)
테스트 13 〉	통과 (0.29ms, 73.2MB)
테스트 14 〉	통과 (0.17ms, 78.5MB)
테스트 15 〉	통과 (0.06ms, 87MB)
테스트 16 〉	통과 (0.10ms, 73.7MB)
테스트 17 〉	통과 (0.18ms, 75.1MB)
테스트 18 〉	통과 (0.06ms, 70.5MB)
테스트 19 〉	통과 (0.07ms, 74.8MB)
 */

