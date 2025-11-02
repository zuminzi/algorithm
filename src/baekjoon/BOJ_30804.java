package baekjoon;

import java.io.*;
import java.util.*;

public class BOJ_30804 {
    // 슬라이딩 윈도우 + 투포인터로 빈도수 체크
    // 앞 또는 뒤에서 제거 가능하지만, 연속된 구간만 고려하면 한쪽 방향으로만 이동해도 최대 길이 탐색 가능
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        int answer = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        Map<Integer, Integer> frequency = new HashMap<>(); // 종류가 적을 때는 Map 대신 배열을 사용하면 더 빠름
        int[] fruits = new int[N];
        int left = 0;
        for(int right=0; right<N; right++){
            int target = Integer.parseInt(st.nextToken());
            fruits[right] = target;
            frequency.put(target, frequency.getOrDefault(target, 0) + 1);

            // 포인트 1 - 과일 종류가 2개 이하가 되기까지 반복문 처리(일회성 처리 x)
            // 포인트 2 - left가 N-1에 도달하면 종료
            while (frequency.size() > 2 && left + 1 < N) {
                if ((frequency.get(fruits[left]) - 1) == 0) {
                    frequency.remove(fruits[left]);
                } else {
                    frequency.put(fruits[left], frequency.get(fruits[left]) - 1);
                }
                // frequency.computeIfPresent(fruits[left], (k, v) -> (v == 1) ? null : v - 1);
                left++;
            }
                answer = Math.max(answer, right - left + 1);
        }
        bw.write(String.valueOf(answer));
        bw.flush();
    }
}
