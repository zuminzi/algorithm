package baekjoon;

import java.io.*;
import java.util.*;

public class BOJ_8979 {

    public static void main(String[] args) throws IOException {
        int ans = 1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int total = Integer.parseInt(st.nextToken());
        int targetNation = Integer.parseInt(st.nextToken());

        int[][] nations = new int[total+1][4];
        for(int i=1; i<nations.length; i++){
            st = new StringTokenizer(br.readLine());
            nations[i][0] = Integer.parseInt(st.nextToken()); // 국가번호
            nations[i][1] = Integer.parseInt(st.nextToken());
            nations[i][2] = Integer.parseInt(st.nextToken());
            nations[i][3] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(nations, (o1, o2) -> {
            if(o1[1] != o2[1]) return Integer.compare(o2[1], o1[1]);
            else if (o1[2] != o2[2]) return Integer.compare(o2[2], o1[2]);
            else return Integer.compare(o2[3], o1[3]);
        });


        for (int i = 0; i < nations.length; i++) {
            int rank = i + 1;
            if (targetNation == nations[0][0]) {
                ans = rank;
                break;
            }
            rank = adjustRankForDuplicates(nations, i, rank);
            if (nations[i][0] == targetNation) {
                ans = rank;
                break;
            }
        }
        bw.write(ans + "\n");
        bw.close();
        br.close();
    }

    private static int adjustRankForDuplicates(int[][] nations, int i, int rank) {
        for(int k = i -1; k >= 0; k--){
            if (containsDuplicateRanks(nations, i, k)) rank--;
            else break;
        }
        return rank;
    }

    private static boolean containsDuplicateRanks(int[][] nations, int curr, int prev){
        return nations[curr][1] == nations[prev][1] && nations[curr][2] == nations[prev][2] && nations[curr][3] == nations[prev][3];
    }
}