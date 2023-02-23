package programmers.LV2;

import java.util.*;

public class PG_42587 {
    // 실패원인 : LoopCnt 잘못 설계, 자료구조(waitingList) 순회하면서 삭제하므로 (모든 요소 루프 반복 횟수 % 요소 개수)가 제대로 반영되지 않음

    // 대기목록들끼리 계속 정렬 필요 // 대기목록의 첫 번째 요소가 대기목록 중 중요도 Max가 아니라면 중요도 Max가 나올 때까지 반복
    // 비교 후 다시 대기목록에 들어갈 때 새로운 대기목록에 추가 (X) 기존의 대기목록에 추가 (O) (뒤에 추가되는 대기목록까지 반영하여 비교 필요)

        static class  paper {
            int pr;
            int loc;

            paper(int pr, int loc) {
                this.pr = pr;
                this.loc = loc;
            }
        }

        // Solution 1 - Paper 객체 생성하여 priority와 각 location 같이 저장하여 관리
        public int exam1(int[] priorities, int location) {
            int answer = 0;
            int cnt = 0;

            Stack<paper> stack = new Stack<paper>();
            ArrayList<Integer> list = new ArrayList<>();

            for(int i=0; i<priorities.length; i++) {
                paper pr = new paper(priorities[i], i);
                stack.add(pr);

                list.add(priorities[i]);
            }

            list.sort(Comparator.reverseOrder());

            int maxPri = list.get(0);

            while(!stack.empty()) {
                paper first = stack.firstElement();
                stack.remove(0);

                if(maxPri == first.pr) {
                    list.remove(0);
                    if(!stack.empty()) maxPri = list.get(0);
                    cnt ++;

                    if(first.loc == location) {
                        answer = cnt;
                    }
                }
                else {
                    stack.add(first);
                }
            }

            return answer;
        }

    // Solution 2 - 1) location 조정, 2) 메서드의 return문에 보낼 요소가 없을 때, return null이나 0 대신 예외 발생시키기
    // throws -> 예외 떠넘기기(미리 안내), throw -> 예외 강제 발생
    public int exam2(int[] priorities, int location) {
        List<Integer> list = new ArrayList<>();
        for (int priority : priorities) {
            list.add(priority);
        }

        int turn = 1;
        while (!list.isEmpty()) { // list remove와 for문을 함께 쓰면 Error 발생 -> while문으로
            final Integer j = list.get(0);
            if (list.stream().anyMatch(v -> j < v)) {
                list.add(list.remove(0));
            } else {
                if (location == 0) { // 대기목록 list 요소 중 max이고 현재 target일 때
                    return turn;
                }
                list.remove(0);
                turn++;
            }

            if (location > 0) {
                location--; // target이 comparator와 비교했을 때 더 크든 작든 list.remove()를 진행하므로 주어진 location의 idx도 같이 --;

            // location이 0일 때 location에 해당하는 수가 가장 큰 수라면, return turn;으로 바로 리턴했을 것
            // 그러나 리턴안하고 여기 왔다는 건 리스트의 맨 마지막 요소로 추가됐다는 것
            } else {
                location = list.size() - 1;
            }
        }

        throw new IllegalArgumentException();
    }

        public static void main(String[] args){
        PG_42587 pg_42587 = new PG_42587();
        //System.out.println(pg_42587.exam2(new int[]{2, 1, 3, 2}, 2)); // expected : 1
        //System.out.println(pg_42587.exam2(new int[]{1, 1, 9, 1, 1, 1},0)); // expected : 5
        System.out.println(pg_42587.exam2(new int[]{1, 1, 2, 3, 2, 1},0)); // expected : 5
        //System.out.println(pg_42587.exam2(new int[]{2, 4, 8, 2, 9, 3, 3},2)); // expected : 2
        }
}
