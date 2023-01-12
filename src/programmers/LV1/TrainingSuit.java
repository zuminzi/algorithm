package programmers.LV1;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TrainingSuit {
    /** point
     * 그리디 알고리즘? 현재 상황에서 가장 좋은 것을 고르는 것이 최적해
     * 그리디 해법은 언제 사용?
        단순히 가장 좋아보이는 것을 반복적으로 선택해도 최적의 해를 구할 수 있는지 검토


     1) lost[i] +- 1 비교하기 전 정렬 **** 그리디 알고리즘은 정렬 필수 ****
        -> [정렬 안 할 시 임의문제] 정렬 안해주면 동일요소 비교 두 번째 루프문부터
           -1로 변환했던 값들을 동일한 값으로 인식하여 잘못 카운트
        -> [임의해결] 동일 요소 비교 후 두 배열을 각각 다른 값으로 변경(ex.lost[i]=-1; lost[j]=-10;
        -> [정렬 안 할 시 근본문제] 그리디 알고리즘이 먹히지 않음(즉 해법이 될 수 없음)
     2) switch/case문에서 case문은 상수(값)만 가능. 변수나 증감문으로 못 받음
     3) 배열 삭제하면 루프문에서 에러나는 경우 다수 ->루프문 중간에 리스트 삭제 시 점검
     */
    public int codeOfMine(int n, int[] lost, int[] reserve) {
        int borrow_cnt = 0;

        Arrays.sort(lost);
        Arrays.sort(reserve);

        for(int i=0; i< lost.length; i++) {
                for (int j = 0; j < reserve.length; j++) {
                    if (lost[i] == reserve[j]) {
                        borrow_cnt++;
                        lost[i] = -1;
                        reserve[j] = -1;
                    }
             }
            }
        for(int i=0; i< lost.length; i++){
            for(int j=0; j< reserve.length; j++) {
                // 두 리스트 내의 동일한 요소를 for문 안에 넣으면 해당 요소 차례가 오기도 전에 -1로 변환됨
                // 따라서 for문이 아니라 for문 돌리기 전에 두 리스트 간 동일 요소 처리 필요
                if (lost[i] + 1 == reserve[j]) {
                    borrow_cnt++;
                    lost[i] = -1;
                    reserve[j] = -1;
                } else if (lost[i] - 1 == reserve[j]) {
                    borrow_cnt++;
                    lost[i] = -1;
                    reserve[j] = -1;
             }
            }
        }

            return n - lost.length + borrow_cnt;
    }

    /** point
     - 리스트 삭제 시 일반 for문(향상된 for문포함)으로 삭제 시 인덱스 변동으로 오류발생

     1) iterator
     2) removeIf -> Collection 메서드
     3) List의 높은 Index에서 낮은 Index 방향으로 순회
     4) 탐색 후, 나중에 삭제
     */
    public int codeOfMine2(int n, int[] lost, int[] reserve) {
        List<Integer> lost_list = new ArrayList<>();
        List<Integer> reserve_list = new ArrayList<>();

        // array to list
        if (lost.length == reserve.length) {
            for (int i = 0; i < lost.length; i++) {
                lost_list.add(lost[i]);
                reserve_list.add(reserve[i]);
            }
        } else {
            for (int lost_num : lost) {
                lost_list.add(lost_num);
            }
            for (int reserve_num : reserve) {
                reserve_list.add(reserve_num);
            }
        }

        Collections.sort(reserve_list);
        Collections.sort(lost_list);

        lost_list.stream()
                .filter(l -> reserve_list.stream().anyMatch(Predicate.isEqual(l)))
                .collect(Collectors.toList())
                .forEach(r -> {
                    lost_list.remove(r);
                    reserve_list.remove(r);
                });


        Iterator<Integer> iterator = lost_list.iterator();

        while( iterator.hasNext()) {
            int target = iterator.next();

            Iterator<Integer> iterator2 = reserve_list.iterator();
            while (iterator2.hasNext()) {
                //System.out.println("reserve="+reserve_list+",lost="+lost_list);
                int comparator = iterator2.next();

                if (target + 1 == comparator) {
                    //System.out.println("comparator="+comparator+",target="+target);
                    iterator.remove();
                    iterator2.remove();
                    break; // break문을 안해주면 여분을 빌려주고 빌린 후에도(삭제) reserve iterator가 계속 돌아감
                } else if (target - 1 == comparator) {
                    iterator.remove();
                    iterator2.remove();
                    break;
                }
            }
        }



        return n - lost_list.size();
    }

        public static void main(String[] args){
        TrainingSuit trainingSuit = new TrainingSuit();
        int[] lost_1 = {1, 2, 5, 6, 10, 12, 13};
        int[] reserve_1 = {12, 11, 10, 9 , 8, 7,2,5,3,4};
        int[] lost_2 = {4,2};
        int[] reserve_2 = {3,5};
        int[] lost_3 = {3,5};
        int[] reserve_3 = {2,4};
        System.out.println(trainingSuit.codeOfMine(13 ,lost_1, reserve_1)); // expected : 11
        System.out.println(trainingSuit.codeOfMine(5 ,lost_2, reserve_2)); // expected : 5 // 정렬 안한 코드 반례
        System.out.println(trainingSuit.codeOfMine2(5, lost_3, reserve_3)); // expected: 5
    }
}

