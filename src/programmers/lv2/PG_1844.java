package programmers.lv2;

import java.util.LinkedList;
import java.util.Queue;

public class PG_1844 {
    // Accuracy Test ~1.02ms, 77.4MB, Efficiency Test ~13.04ms, 72.6MB
    class Pair {
        int total;
        int y;
        int x;
        Pair(int total, int y, int x) {
            this.total = total;
            this.y = y;
            this.x = x;
        }
    }

    public int solution(int[][] maps) {
        int answer = 999;
        int yLen = maps.length;
        int xLen = maps[0].length;
        // Problem Point - maps의 행 또는 열의 길이가 1인 경우에 length-2를 하면 인덱스 오류 발생
        // 이 경우를 고려하여 return -1 반환 로직 조건 추가
        if(yLen == 1 && maps[0][xLen-2] == 0){
                return answer;
        } else if(xLen == 1 && maps[yLen-2][0] == 0){
                return answer;
        } else if(yLen != 1 && xLen != 1 && maps[yLen - 2][xLen - 1] == 0 && maps[yLen - 1][xLen - 2] == 0){
                return answer;
        } else {
                Queue<Pair> queue = new LinkedList<>();
                queue.offer(new Pair(0, 0, 0));

                while (!queue.isEmpty()) {
                    Pair p = queue.poll();
                    if(maps[p.y][p.x] == 0) continue;
                    // 방문체크는 각 정점에서
                    // 다음 노드에 미리 방문체크하면 분기문에서 문제가 생김
                    // 동시에 분기가 되는 것이 아니라, 선후관계로 분기가 됨
                    maps[p.y][p.x] = 0;
                    if (p.x == xLen-1 && p.y == yLen - 1) { // 상대 진영 도착 // 인덱스로 확인
                        // Problem Point - answer 초기값을 0으로 설정 후, Math.max()로 answer min 값이 아닌 max 값 갱신
                        answer = Math.min(answer, p.total+1);// +1은 시작칸 포함
                        break;
                    }
                    // 다음 인접 노드 방문
                    if (p.y < yLen - 1 ) {
                        queue.add(new Pair(p.total + 1, p.y + 1, p.x));
                    }
                    if ( p.x < xLen - 1) {
                        queue.add(new Pair(p.total + 1, p.y, p.x + 1));
                    }
                    // Problem Point - up, left뿐만 아니라 down, right도 모든 경우에 탐색
                    if (p.y > 0) {
                        queue.add(new Pair(p.total + 1, p.y - 1, p.x));
                    }
                    if (p.x > 0) {
                        queue.add(new Pair(p.total + 1, p.y, p.x - 1));
                    }
                }
                answer = answer == 999 ? -1 : answer;
        }
        return answer;
    }

    public static void main(String[] args){
        PG_1844 pg_1844 = new PG_1844();
        System.out.println(pg_1844.solution(new int[][]
                {{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,1},{0,0,0,0,1}})); // expected : 11
        System.out.println(pg_1844.solution(new int[][]{{1, 1, 1, 1, 1}})); // expected : 5 // maps가 가로 혹은 세로로 일자 형태로 되어있을 때 인덱싱 에러 발생하는지 체크
        System.out.println(pg_1844.solution(new int[][]{{1}, {1}, {1}, {1}, {1}})); // expected : 5 // maps가 가로 혹은 세로로 일자 형태로 되어있을 때 인덱싱 에러 발생하는지 체크
        System.out.println(pg_1844.solution(new int[][]{{1,0,0,0},{1,0,0,0},{1,0,1,1},{1,0,1,1}})); // expected : -1
        System.out.println(pg_1844.solution(new int[][]{{1,1,1,1,1},{1,1,1,1,1},{0,0,0,0,1}})); // expected : 7
        System.out.println(pg_1844.solution(new int[][]{{1,0,0,0,0},{1,1,0,0,0},{1,1,1,1,1},{1,0,1,1,1}})); // expected : 8
    }
}