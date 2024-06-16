package programmers.lv2;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.PriorityQueue;

public class PG_155651 {
    // Greedy, Priority Queue
    // 시간 복잡도: O(n log n)
    public int solution(String[][] book_time) {
        int maxSize = 1;

        // O(n log n)
        Arrays.sort(book_time, (o1, o2) -> {
            LocalTime lt1 = LocalTime.parse(o1[0]);
            LocalTime lt2 = LocalTime.parse(o2[0]);

            if(!lt1.equals(lt2)) return lt1.compareTo(lt2);
            else return LocalTime.parse(o1[1]).compareTo(LocalTime.parse(o2[1]));
        });
        PriorityQueue<LocalTime> pq = new PriorityQueue<>();

        pq.add(LocalTime.parse(book_time[0][1])); // O(1)

        for(int idx = 1; idx < book_time.length; idx++){
            LocalTime nextBookedStart = LocalTime.parse(book_time[idx][0]);
            LocalTime nextBookedEnd = LocalTime.parse(book_time[idx][1]);

            if(pq.peek().plusMinutes(10).isAfter(nextBookedStart)) {
                maxSize++;
            } else {
                pq.poll(); // O(n log n) // 힙의 특성을 유지하기 위해 힙의 깊이에 비례하는 시간 소요 // 최악의 경우, book_time.length(), 즉 n만큼 소요되므로 (n * log n)
            }
            pq.add(nextBookedEnd); // O(n log n) // 힙의 특성을 유지하기 위해 힙의 깊이에 비례하는 시간 소요 // (n * log n)
        }
        return maxSize;
    }

    // Greedy, Priority Queue
    // 시간 복잡도: O(n log n)
    public int sol1(String[][] book_time) {

        int[][] bkt = new int[book_time.length][];

        for (int i = 0; i < book_time.length; i++) {
            bkt[i] = new int[] { parseTime(book_time[i][0]), parseTime(book_time[i][1]) + 10 };
        }

        Arrays.sort(bkt, (a, b) -> a[0] - b[0]);

        PriorityQueue<int[]> que = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        int ans = 0;

        for (int i = 0; i < bkt.length; i++) {
            while (!que.isEmpty() && que.peek()[1] <= bkt[i][0]) {
                que.poll();
            }
            que.add(bkt[i]);
            ans = Math.max(ans, que.size());
        }

        return ans;
    }

    protected int parseTime(String time) {

        String[] hhmm = time.split(":");
        int hour = Integer.parseInt(hhmm[0]), min = Integer.parseInt(hhmm[1]);
        return hour * 60 + min;

    }

    // 시간대 배열 생성하여 각 시간대(분 단위)마다 예약된 객실 수 기록
    // 시간 복잡도: O(N * 1440)
    public int sol2(String[][] book_time) {
        int answer = 0;
        int[] howManyBooksInTime = new int[1440]; // 24h * 60m
        for(String[] book : book_time){
            int start = toInt(book[0]);
            int end = Math.min(1439, toInt(book[1])+10);
            for(int i=start; i<end; i++){
                howManyBooksInTime[i]++;
            }
        }
        for(int i=0; i<1440; i++){
            answer = Math.max(answer,howManyBooksInTime[i]);
        }
        return answer;
    }

    public int toInt(String s){ // 분 단위로 변환
        String[] ss = s.split(":");
        return Integer.parseInt(ss[0])*60 + Integer.parseInt(ss[1]);
    }

    public static void main(String[] args) {
        PG_155651 pg_155651 = new PG_155651();
        // expected : 2
        System.out.println(pg_155651.solution(new String[][] {
                {"09:10", "10:11"}, {"10:20", "12:20"}
        }));
    }
}
