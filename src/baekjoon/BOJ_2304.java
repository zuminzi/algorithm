package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2304 {
    /**
     * x축을 기준으로 정렬 후에, 제일 높은 기둥을 기준으로 왼쪽은 증가하고 오른쪽은 감소하는 비율을 갖도록 총면적 계산
        - 비가 올 때 물이 고이지 않도록 지붕의 어떤 부분도 오목하게 들어간 부분이 없어야 한다.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int answer = 0;
        int[][] area = new int[n][2]; // {위치, 높이}

        for(int i=0; i<n; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            area[i][0] = Integer.parseInt(st.nextToken());
            area[i][1] = Integer.parseInt(st.nextToken());
        }

        // 위치 순으로 정렬
        Arrays.sort(area, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });

        int maxLength = area[0][1];
        int maxIdx = 0;
        for(int i=1; i< area.length; i++){
            if(area[i][1] >= maxLength){
                int w = area[i][0] - area[maxIdx][0];
                int h = maxLength;
                answer += w * h;

                maxLength = area[i][1];
                maxIdx = i;
            }
        }

        maxLength = area[area.length-1][1];
        int loop = maxIdx;
        maxIdx = area.length-1;
        for(int i= area.length-2; i>= loop; i--){
            if(area[i][1] >= maxLength){
                int w = area[maxIdx][0] - area[i][0];
                int h = maxLength;
                answer += w * h;

                maxLength = area[i][1];
                maxIdx = i;
            }
        }

        answer += maxLength;

        System.out.println(answer);
    }
}
