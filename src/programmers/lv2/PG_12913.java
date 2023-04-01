package programmers.lv2;

public class PG_12913 {
    int exam1(int[][] land) {
        final int length = land.length;

        for(int i=1; i<length; i++) {
            land[i][0] += max(land[i-1][1], land[i-1][2], land[i-1][3]);
            land[i][1] += max(land[i-1][0], land[i-1][2], land[i-1][3]);
            land[i][2] += max(land[i-1][1], land[i-1][3], land[i-1][0]);
            land[i][3] += max(land[i-1][1], land[i-1][2], land[i-1][0]);
        }

        return max(land[length-1]);
    }

    public int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    public int max(int[] arr) {
        int max = 0;
        for(int number : arr) {
            max = Math.max(max, number);
        }
        return max;
    }

    // Fail Efficiency Test
    // Top-down 방식
    public static final int N = 4;
    static int[][] cache;

    private static int move(int[][] land, int row, int col) {
        if (row == land.length - 1) return cache[row][col] = land[row][col]; // 기저 조건
        if (cache[row][col] != 0) return cache[row][col]; // 캐시

        for (int i = 0; i < N; i++) {
            if (i != col) {
                cache[row][col] = Math.max(cache[row][col], land[row][col] + move(land, row + 1, i));
            }
        }
        return cache[row][col];
    }

     int exam2(int[][] land) {
        cache = new int[land.length][N];
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            result = Math.max(result, move(land, 0, i));
        }
        return result;
    }

    public static void main(String[] args){
        PG_12913 pg_12913 = new PG_12913();
        // expected : 16
        System.out.println(pg_12913.exam2(new int[][]{{1,2,3,5},{5,6,7,8},{4,3,2,1}}));
        // expected : 20
        System.out.println(pg_12913.exam2(new int[][]{{4, 3, 2, 1}, {2, 2, 2, 1}, {6, 6, 6, 4}, {8, 7, 6, 5}}));
        // expected : 17
        System.out.println(pg_12913.exam2(new int[][]{{1, 2, 3, 5}, {5, 6, 8, 7}, {4, 3, 2, 1}}));
        // expected : 13
        System.out.println(pg_12913.exam2(new int[][]{{6, 5, 5, 5}, {3, 4, 3, 3}, {3, 2, 2, 2}}));
        // expected : 5
        System.out.println(pg_12913.exam2(new int[][]{{1, 1, 1, 2}, {1, 1, 1, 3},{1, 1, 1, 2}}));
        // expected : 4
        System.out.println(pg_12913.exam2(new int[][]{{1, 1, 1, 1}, {1, 1, 1, 2}, {1, 1, 1, 1}}));
        // expected : 6
        System.out.println(pg_12913.exam2(new int[][]{{1, 1, 1, 2}, {1, 1, 2, 1}, {1, 2, 1, 1}}));
        // expected : 5
        System.out.println(pg_12913.exam2(new int[][]{{5,5,5,5}}));
        // expected :4
        System.out.println(pg_12913.exam2(new int[][]{{1,2,3,4}}));
        // expected : 101
        System.out.println(pg_12913.exam2(new int[][]{{0,1,1,10},{0,1,1,100}}));
        // expected : 38
        System.out.println(pg_12913.exam2(new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}}));
        // expected : 21
        System.out.println(pg_12913.exam1(new int[][]{{1,4,3,2},{9,8,7,6},{10,4,2,1}}));
    }
}
