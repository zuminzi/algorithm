package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_11000 {
    public static void main(String[] args) throws IOException {
        int availableRooms = 1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        PriorityQueue<Integer> activeQ = new PriorityQueue<>(); // 끝나는 시간 순으로 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() { // 시작 시간, 끝나는 시간 순으로 정렬
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0] != o2[0]){
                    return Integer.compare(o1[0], o2[0]);
                } else {
                    return Integer.compare(o1[1], o2[1]);
                }
            }
        });
        while (--N >= 0){
            st = new StringTokenizer(br.readLine());
            pq.add(new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }
        while (!pq.isEmpty()){
            if(!activeQ.isEmpty()) {
                // 이전 강의가 끝나는 시간보다 다음 강의가 시작하는 시작이 같거나 그 후면
                if (activeQ.peek() <= pq.peek()[0]) {
                    activeQ.poll();
                } else{
                    availableRooms++;
                }
            }
            activeQ.add(pq.poll()[1]);
        }
        System.out.println(availableRooms);
    }
}
