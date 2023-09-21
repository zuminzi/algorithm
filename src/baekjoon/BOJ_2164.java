package baekjoon;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2164 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        Queue<Integer> q = new LinkedList<>();

        for(int i=1; i<=N; i++){
            q.add(i);
        }

        while (q.size() > 1){
            q.poll();
            if (q.size() > 1) {
                q.add(q.poll());
            }
        }

        bw.write(q.peek() + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}
