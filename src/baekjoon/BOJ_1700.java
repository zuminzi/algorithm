package baekjoon;

import java.io.*;
import java.util.*;


public class BOJ_1700 {
    /**
     * 멀티탭 스케줄링 문제를 해결하는 알고리즘.
     * 주어진 구멍의 개수 N과 전기용품의 사용 순서를 바탕으로 최소한의 콘센트 뽑기 횟수를 계산합니다.
     * 이 코드는 https://steady-coding.tistory.com/55 에서 참고했습니다.
     */
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken()); // 전기용품 사용순서 개수

        int[] order = new int[K];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            order[i] = Integer.parseInt(st.nextToken());
        }

        boolean[] use = new boolean[101];
        int put = 0;
        int ans = 0;
        for (int curr = 0; curr < K; curr++) {
            int temp = order[curr];

            if (!use[temp]) { // 콘센트가 꽂혀있지 않은 경우
                if (put < N) { // 콘센트를 꽂을 공간이 있는 경우
                    use[temp] = true;
                    put++;
                } else { // 콘센트를 꽂을 공간이 없는 경우
                    ArrayList<Integer> reuseList = new ArrayList<>();
                    for (int j = curr; j < K; j++) { // 현재 꽂혀 있는 콘센트가 나중에도 사용되는지 확인.
                        if(use[order[j]]) {
                            if (!reuseList.contains(order[j])) {
                                reuseList.add(order[j]);
                            }
                        }
                    }

                    if (reuseList.size() != N) { // 나중에도 사용되는 콘센트가 구멍의 개수보다 작을 경우
                        for (int j = 0; j < use.length; j++) {
                            if (use[j] && !reuseList.contains(j)) { // 나중에 사용되지 않는 콘센트를 제거.
                                use[j] = false;
                                break;
                            }
                        }
                    } else { // ** 현재 꽂혀 있는 모든 콘센트가 나중에도 사용될 경우
                        int remove = reuseList.get(reuseList.size() - 1); // ** 가장 마지막에 사용될 콘센트를 제거.
                        use[remove] = false;
                    }

                    use[temp] = true;
                    ans++;
                }
            }
        }

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 예제로 주어진 TC만 통과
    // 반례 : (정답 : 2)
    // 3 13
    // 2 3 4 2 3 4 1 5 5 5 2 3 2
    // 슬라이딩 윈도우에서 다음 윈도우 영역뿐만 아니라, 그 이후도 고려 필요.
    private void fail() throws IOException {
        int minPlugRemovals = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int plugCapacity = Integer.parseInt(st.nextToken());
        int totalPowerUsage = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        int[] usage = new int[totalPowerUsage];
        for (int i = 0; i < usage.length; i++) {
            usage[i] = Integer.parseInt(st.nextToken());
        }

        int unchanged = 0;
        Queue<Integer> capacity = new LinkedList<>();
        for(int i = 0; i< usage.length; i++){
            if(capacity.size() < plugCapacity) {
                capacity.offer(usage[i]);
            } else {
                if (capacity.contains(usage[i])) continue;

                Set<Integer> plugged = new HashSet<>(capacity);
                Set<Integer> intersection = new HashSet<>();
                int loopCount = i + plugCapacity >= usage.length ? usage.length : i + plugCapacity;

                for(int j = i; j < loopCount; j++){
                    intersection.add(usage[j]);
                }

                intersection.retainAll(plugged);

                if(intersection.size() == plugged.size()) {
                    i += intersection.size() - 1;
                    continue;
                }
                if(!intersection.isEmpty()) {
                    for (int j = 0; j < capacity.size(); j++) {
                        if (intersection.contains(capacity.peek())) {
                            capacity.add(capacity.poll()); // 다음 윈도우에 있으면 뒤로 보내기
                        }
                    }
                }

                int k = i;
                while (capacity.size() >= plugCapacity) {
                    for (;k < loopCount && k < usage.length; k++) {
                        if (usage[i] == capacity.peek()) {
                            capacity.add(capacity.poll());
                            unchanged++;
                            if (k != loopCount - 1) i++;
                            continue; // 루프문에서 break 문을 사용하여 루프를 빠져나오면, 해당 루프 내에 있는 증감문 등의 코드는 실행되지 않는다.
                        }
                        if (k == loopCount - 1) {
                            if (unchanged < plugCapacity) {
                                capacity.poll();
                                minPlugRemovals++;
                            }
                        }
                    }
                }
                capacity.add(usage[i]);
            }
        }
        System.out.println(minPlugRemovals);
    }
}

