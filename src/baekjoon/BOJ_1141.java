package baekjoon;

import java.io.*;
import java.util.*;

public class BOJ_1141 {
    /**
     * 사전 순으로 정렬하고, 현재 문자열이 이전 문자열의 접두어가 아닌 문자열 개수 세어 최대 크기의 '접두사X 집합'을 찾는 그리디 알고리즘
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        String[] strs = new String[N];
        int ans = 0;

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            strs[i] = st.nextToken();
        }

        Arrays.sort(strs);

        int cnt = 1;
        for (int k = 1; k < N; k++) {
            if (strs[k].startsWith(strs[k - 1])) {
                continue;
            }
            cnt++;
        }

        ans = Math.max(ans, cnt);

        System.out.println(ans);
    }
}
