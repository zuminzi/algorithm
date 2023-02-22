package programmers.LV2;

import java.util.*;

public class PG_42587 {
    //인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
    //2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
    //3. 그렇지 않으면 J를 인쇄합니다.
    // location의 위치에 있는 문서가 몇번째로 인쇄되는지
    // 대기목록들끼리는 계속 정렬 필요, 대기목록의 첫번째가 대기목록 중 중요도 Max가 아니라면 중요도 max가 나올 때까지 반복
    // priorities는 대기목록의 배열도 되므로, 다시 대기 목록에 들어갈 때 새로운 대기목록에 들어가는 것이 아닌 기존의 대기목록에 들어가야함
    public int solution(int[] priorities, int location) {
        int answer = -1;
        Queue<Integer> printList = new LinkedList<>();
        Queue<Integer> waitingList = new LinkedList<>();
        int[] cntArray = new int[priorities.length];
        //int max = 0;
        for(int i=0; i<priorities.length; i++){
            //max = Math.max(priorities[i], max)
            waitingList.add(priorities[i]);
        }
        int loopCnt = 0;

        while(!waitingList.isEmpty()){
            if(waitingList.isEmpty()){
                break;
            }
            boolean isLarger = true;
            for(Integer priority : waitingList){
                if(waitingList.peek() < priority){
                    int temp = waitingList.poll();
                    waitingList.add(temp);
                    isLarger = false;
                    break;
                }
            }

            loopCnt++;

            if(isLarger == true) {
                printList.add(waitingList.poll());
                cntArray[(loopCnt-1) % priorities.length] = printList.size();
            }
        }

        for(int i=0; i< cntArray.length; i++){
            if(i == location){
                answer = cntArray[i];
            }
        }
        return answer;
    }


        public static void main(String[] args){
        PG_42587 pg_42587 = new PG_42587();
        //System.out.println(pg_42587.solution(new int[]{2, 1, 3, 2}, 2)); // expected : 1
        //System.out.println(pg_42587.solution(new int[]{1, 1, 9, 1, 1, 1},0)); // expected : 5
        System.out.println(pg_42587.solution(new int[]{1, 1, 2, 3, 2, 1},0)); // expected : 5
        //System.out.println(pg_42587.solution(new int[]{2, 4, 8, 2, 9, 3, 3},2)); // expected : 2
        }
}
