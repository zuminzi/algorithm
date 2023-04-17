package baekjoon;

import java.util.*;

// output : 최소 환승 횟수
public class BOJ_2021 {
    // time out
    private static int start;
    private static int end;
    private static int[][] stations;
    private static int min;

    public static void main(String[] args) {
//        int totalStation = 10;
//        int totalLine = 3;
//        stations = new int[][]{{1, 2, 3, 4, 5}, {9, 7, 10}, {7, 6, 3, 8}};
//        start = 1;
//        end = 10;
//        int[] arr = new int[]{0, 1, 2};
        Scanner sc = new Scanner(System.in);
        int totalStation = sc.nextInt();
        int totalLane = sc.nextInt();
        int[] arr = new int[totalLane];
        stations = new int[totalLane][];

        for(int i=0; i<totalLane; i++) {
            Queue<Integer> q = new LinkedList();
            while(true) {
                int num = sc.nextInt();
                if (num != -1) {
                    q.add(num);
                } else {
                    break;
                }
            }
            stations[i] = new int[q.size()];
            int k =0;
            while(!q.isEmpty()) {
                stations[i][k++] = q.poll();
            }
            arr[i] = i;
        }
        start = sc.nextInt();
        end = sc.nextInt();
        min = Integer.MAX_VALUE;
        perm(arr, new boolean[totalLane], new int[totalLane], 0);
        if (min == Integer.MAX_VALUE) min = -1;
        System.out.println(min);
    }

    private static void perm(int[] arr, boolean[] visited, int[] output, int depth) {
        if (depth == visited.length) {
            move(output);
            return;
        }

        for (int i = 0; i < visited.length; i++) {
            if (visited[i] != true) {
                visited[i] = true;
                output[depth] = arr[i];
//                if(depth == 0){
//                    if (!Arrays.stream(stations[output[0]]).anyMatch(station -> station == start)) {
//                        visited[i] = false;
//                        continue;
//                    }
//                }
                perm(arr, visited, output, depth + 1);
                visited[i] = false;
            }
        }
    }

    static class Pair {
        int curRow; // 현재 stations row
        int transfer; // 환승 횟수
        int next; // next row

        Pair(int curRow, int transfer, int next) {
            this.curRow = curRow;
            this.transfer = transfer;
            this.next = next;
        }
    }

    private static void move(int[] output) {
        if (!Arrays.stream(stations[output[0]]).anyMatch(station -> station == start)) {
            return;
        }
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(output[0], 0, 1));

        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            int row = p.curRow;
            int transfer = p.transfer;
            int next = p.next;

            if (Arrays.stream(stations[row]).anyMatch(station -> station == end)) {
                min = Math.min(transfer, min);
                return;
            }
            if (next == output.length) {
                return;
            }

            int nextRow = output[next];
            for (int col = 0; col < stations[row].length; col++) {
                for (int k = 0; k < stations[nextRow].length; k++) {
                    if (stations[nextRow][k] == stations[row][col]) {
                        transfer++;
                        queue.offer(new Pair(nextRow, transfer, next + 1));
                        break;
                    }
                }
            }
        }
    }
}
