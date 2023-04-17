package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// output : 최소 환승 횟수
public class BOJ_2021 {
    public static class Node {
        int curLine;
        int curStation;
        int transCount;

        public Node(int curLine, int curStation, int transCount) {
            this.curLine = curLine;
            this.curStation = curStation;
            this.transCount = transCount;
        }
    }

    static int N, M;
    static boolean[] visitedLine;
    static boolean[] visitedStation;
    static ArrayList<Integer>[] stations; // List 타입의 배열
    static ArrayList<Integer>[] lines;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 역 개수
        M = Integer.parseInt(st.nextToken()); // 노선 개수

        visitedLine = new boolean[M + 1];
        visitedStation = new boolean[N + 1];
        stations = new ArrayList[N + 1];
        lines = new ArrayList[N + 1];
        for (int i = 1; i < N + 1; i++) {
            stations[i] = new ArrayList<>();
            lines[i] = new ArrayList<>();
        }

        // 역 별로 포함된 노선 리스트(stations), 노선 별 포함하는 역 리스트(lines) 각각 관리
        for (int i = 1; i <= M; i++) {
            String[] s = br.readLine().split(" ");
            for (String station : s) {
                int statN = Integer.parseInt(station);
                if (statN == -1) break;
                stations[statN].add(i);
                lines[i].add(statN);
            }
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        int answer = go(start, end);
        System.out.println(answer);
    }

    private static int go(int start, int end) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.transCount));
        visitedStation[start] = true;
        for (int line : stations[start]) {
            pq.add(new Node(line, start, 0));
            visitedLine[line] = true;
        }

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (now.curStation == end) {
                return now.transCount;
            }

            for (int nextStation : lines[now.curLine]) { // 해당 노선의 역 목록 탐색// BFS

                if (!visitedStation[nextStation]) {
                    visitedStation[nextStation] = true;
                    pq.add(new Node(now.curLine, nextStation, now.transCount));

                    for (int nextLine : stations[nextStation]) { // 해당 역의 노선 목록 탐색 // DFS
                        if (!visitedLine[nextLine]) {
                            visitedLine[nextLine] = true;
                            pq.add(new Node(nextLine, nextStation, now.transCount + 1));
                        }
                    }
                }
            }
        }
        return -1;
    }
}
