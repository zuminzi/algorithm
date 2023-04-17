package programmers.lv3;

import java.util.*;

// input: routes(진입지점, 진출지점)
// output: 모든 차량이 한 번은 단속용 카메라를 만나도록 카메라 설치 최솟값
public class PG_42884 {
/*
- Arrays.sort(arr2, new Comparator<int[]>() {
    @Override
    public int compare(int[] o1, int[] o2) {
        return o1[0]!=o2[0] ? o1[0]-o2[0] : o1[1]-o2[1]; // 첫번째 기준 오름차순 > 두번째 기준 오름차순  : {1,30}{2,10}{3,50}{4,20}{5,40}{6,10}{6,20}{6,30}{6,40}{6,50}
    }
});
- Arrays.sort(arr, Comparator.comparingInt((int[] o) -> o[0]).reversed()); // 첫번째 숫자 기준 내림차순 : {5,40}{4,20}{3,50}{2,10}{1,30}

 */
    // 먼저 out된 진출로에 카메라 설치하여 포함되는지 여부 확인
    public int solution(int[][] routes) {
        int answer = 0;
        for(int i=0; i<routes.length; i++){
            if(routes[i][0] > routes[i][1]){
                int temp = routes[i][1];
                routes[i][1] = routes[i][0];
                routes[i][0] = temp;
            }
        }
        // 다차원 배열 정렬
        // 두번째 열 기준 오름차순
        Arrays.sort(routes, Comparator.comparingInt((int[] o) -> o[1]));
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int i=0; i<routes.length;i++) {
            boolean isWithinRange = false;
            for (int out : pq) {
                if (out >= routes[i][0] && out <= routes[i][1]) {
                    isWithinRange = true;
                    break;
                }
            }
            if (pq.isEmpty() || !isWithinRange) {
                answer++;
                pq.add(routes[i][1]);
            }
        }
        return answer;
    }

    public static void main(String[] args){
        PG_42884 pg_42884 = new PG_42884();
        // expected : 2
        System.out.println(pg_42884.solution(new int[][]{{-20,-15}, {-14,-5}, {-18,-13}, {-5,-3}}));
        // expected: 3
        System.out.println(pg_42884.solution(new int[][]{{-20,-15}, {-14,-15}, {-18,-17}, {-5,-2},{-3,-1}}));
        // expected : 4
        System.out.println(pg_42884.solution(new int[][]{{-100,100},{50,170},{150,200},{-50,-10},{10,20},{30,40}}));
        // expected : 2
        System.out.println(pg_42884.solution(new int[][]{{0,1},{0,1},{2,2}}));
        // expected : 2
        System.out.println(pg_42884.solution(new int[][]{{0,0},{0,0},{2,2}}));
    }
}