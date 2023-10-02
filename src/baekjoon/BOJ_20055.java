package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_20055 {
    /**
     * 컨베이어 벨트와 로봇이 함께 동작하며 일정한 규칙에 따라 로봇을 이동시키고 내구도를 감소시키는 시뮬레이션 문제
     * Key Point 1 : 내구도가 0이 되는 구간 처리 (내구도 - 1 >= 0 인지 검사 후, 관련 로직 처리)
     * Key Point 2 : 첫 번째 요소를 가져오는(remove()) 반복문에서 조건식에 사용되는 자료구조 사이즈를 고정 변수로 선언 (remove()로 인해 자료구조 사이즈가 변하므로)
     * Key Point 3 : 조건문 빠짐 없이 처리 (만약 이동할 수 없다면 가만히 있는다.)
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] durability = new int[2 * N];
        int zeroCount = 0;
        int round = 0;
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < 2 * N; i++){
            if(i == N - 1) {
                durability[i] = -1;
            }
            durability[i] = Integer.parseInt(st.nextToken());
        }

        List<Integer> robotIdxList = new ArrayList<>(); // 백준 히든 TC에서는 LinkedList보다 ArrayList를 사용하는 게 효율적이었다.
        do {
            round++;

            // 1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
            int temp = -1;
            for(int i = 2*N - 1; i >= 0; i--){
                if(i != 0) {
                    if(i == 2*N - 1){
                        temp = durability[i];
                    }
                    durability[i] = durability[i - 1];
                } else {
                    durability[i] = temp;
                }
            }

            int loop = robotIdxList.size();
            for(int i=0; i<loop; i++){
                int idx = robotIdxList.remove(0);

                idx++;
                if(idx == 2*N){
                    idx = 0;
                }
                if (idx != N - 1) { // 내리는 위치가 아니면
                    robotIdxList.add(idx);
                }
            }

            // 2. 가장 먼저 벨트에 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다. 만약 이동할 수 없다면 가만히 있는다.
            boolean isDropPoint = false;
            loop = robotIdxList.size();
            for(int i=0; i<loop; i++) {
                int idx = robotIdxList.remove(0);

                idx++;
                if (idx == 2 * N) {
                    idx = 0;
                    isDropPoint = true;
                }

                // 3. 올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다.
                if (durability[idx] - 1 >= 0 && !robotIdxList.contains(idx)) {
                    durability[idx]--;
                    if (durability[idx] == 0) {
                        zeroCount++;
                    }
                } else {
                    idx = isDropPoint? (2*N) - 1 : idx - 1;
                }

                if (idx != N - 1) {
                    robotIdxList.add(idx);
                }
            }

            if(durability[0] - 1 >= 0) {
                durability[0]--;
                robotIdxList.add(0);

                if (durability[0] == 0) {
                    zeroCount++;
                }
            }

        } while (zeroCount < K); // 4. 내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다.

        System.out.println(round);
    }
}
