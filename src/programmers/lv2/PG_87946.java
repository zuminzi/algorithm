package programmers.lv2;

// input : dungeons[][0]은 최소피로도, dungeons[][1]은 소모피로도
// output : 최대 던전 수
public class PG_87946 {
    // ~4.47ms, 72.2MB
    static int maxCnt;
    public int codeOfMine_refactor(int k, int[][] dungeons) {
        maxCnt = 0;
        boolean[] isSelected = new boolean[dungeons.length];
        //dfs(0, dungeons, isSelected, k,0);
        dfs_refactor(0, dungeons, isSelected, k);

        return maxCnt;
    }

    private static void dfs(int index, int[][] arr, boolean[] isSelected, int min, int index2) {
        if(index > 0) {
            if (arr[index2][0] > min || arr[index2][1] > min) {
                return;
            } else {
                min -= arr[index2][1];
                maxCnt = Math.max(index, maxCnt); // index initial value = 0, range = 1~3
            }
        }

        for (int i = 0; i < arr.length; i++) {
            if (isSelected[i]) continue;
            isSelected[i] = true;
            dfs(index + 1, arr, isSelected, min, i);
            isSelected[i] = false;
        }
    }

    private static void dfs_refactor(int index, int[][] arr, boolean[] isSelected, int min) {
        for (int i = 0; i < arr.length; i++) {
            if (!isSelected[i] ) {
                if(arr[i][0] <= min ) {
                    isSelected[i] = true;
                    //min -= arr[i][1];
                    dfs_refactor(index + 1, arr, isSelected, min - arr[i][1]); // min -= arr[i][1] (X)
                    isSelected[i] = false;
                }
            }
        }
        maxCnt = Math.max(index, maxCnt);
    }

    public static boolean check[];
    public static int ans = 0;

    public int exam1(int k, int[][] dungeons) {
        check = new boolean[dungeons.length];

        dfs(k, dungeons, 0);

        return ans;
    }
    public static void dfs(int tired, int[][] dungeons, int cnt){
        for(int i=0; i<dungeons.length; i++){
            if(!check[i] && dungeons[i][0]<=tired){ // 방문과 문제 조건을 한번에 검사
                check[i] = true;
                dfs(tired-dungeons[i][1], dungeons, cnt+1); // tired(최소피로도) 감소문을 재귀로 바로 반영
                check[i] = false;
            }
        }
        // 한 번의 그래프 끝난 후 max 비교 작업
        ans = Math.max(ans, cnt);
    }

    // ~2.80ms, 74.9MB
    public int exam2(int k, int[][] dungeons) {
        int answer = -1;
        return dfs(k, dungeons);
    }

    // visited 대신 최소 필요피로도 k를 9999로 설정하여(k 최댓값 5000보다 큰 5001로도 Ok) 방문처리
    int dfs(int k, int[][] dungeons) {
        int cnt = 0;
        for(int[] d : dungeons) {
            int a = d[0], b = d[1];
            if(a <= k) {
                d[0] = 9999;
                cnt = Math.max(1 + dfs(k - b, dungeons), cnt);
                // 경로 탐색 후 방문 처리 원상 복귀 // visited[i] = false와 마찬가지
                d[0] = a;
            }
        }
        return cnt;
    }

    public static void main(String[] args){
        PG_87946 pg_87946 = new PG_87946();
        System.out.println(pg_87946.exam2(80, new int[][]{{80,20},{50,40},{30,10}}));
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

