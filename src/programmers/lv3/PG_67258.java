package programmers.lv3;

import java.util.*;

/*
 * @param : 진열대 번호 순서대로 보석들의 이름이 저장된 배열 gems
 * @return: 모든 보석을 하나 이상 포함하는 가장 짧은 구간 (시작 진열대 번호, 끝 진열대 번호)를 담은 배열
 * 어피치는 쇼핑을 할 때면 매장 진열대의 특정 범위의 물건들을 모두 싹쓸이 구매하는 습관이 있습니다.
 * 진열된 모든 종류의 보석을 적어도 1개 이상 포함하는 가장 짧은 구간을 찾아서 구매
 * 만약 가장 짧은 구간이 여러 개라면 시작 진열대 번호가 가장 작은 구간을 return 합니다.
 - 슬라이딩 윈도우 알고리즘을 택하지 않은 이유 -> 부분 배열이 고정된 길이가 아님 (보석 종류만큼의 범위를 가질 수도 있고, 그것보다 클 수도 있음)
 - 따라서, 슬라이딩 윈도우(고정적인 부분 배열) 대신 투포인터(가변적인 부분 배열) 알고리즘 사용
 */
public class PG_67258 {
    public int[] solution(String[] gems) {
        Set<String> uniqueSet = new HashSet<>(Arrays.asList(gems));
        Set<String> checkedGems = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt((int[] arr) -> arr[1] - arr[0]).thenComparingInt(arr -> arr[0]));
        int left = 0;
        int right = 0;

        while (left + 1 < gems.length) {
            if (right < gems.length && checkedGems.size() < uniqueSet.size()) {
                q.add(gems[right]);
                checkedGems.add(gems[right]);
                right++;
            } else if (checkedGems.size() == uniqueSet.size()) {
                q.poll();
                if (!q.contains(gems[left])) {
                    checkedGems.remove(gems[left]);
                }
                left++;
                if(checkedGems.size() < uniqueSet.size()) {
                    // problem point 1
                    // 28~31 라인에서 left 포인터의 요소를 제거하여 사이즈 비교한 뒤
                    // 감소하였을 시 이전 요소 모든 종류의 보석을 포함하는 구간임을 확인하여
                    // Priority Queue에 넣거나 바로 return하는 코드이므로
                    // 아래 조건문에서 비교하는 q.size는 q.size가 아닌 q.size + 1
                    if (q.size() + 1 > uniqueSet.size()) {
                        pq.add(new int[]{left, right});
                    } else {
                        return new int[]{left, right};
                    }
                }
             // problem point 2
             // 라인 40을 거친 후 right >= gems.length 이지만 checkedGems.size() < uniqueSet.size()일 시 break
            } else {
                break;
            }
        }
        return pq.peek();
    }

    public static void main (String[] args) {
        PG_67258 pg_67258 = new PG_67258();
        // expected : [3, 7]
        System.out.println(pg_67258.solution(new String[]{"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"}));
        // expected : [1, 3]
        System.out.println(pg_67258.solution(new String[]{"AA", "AB", "AC", "AA", "AC"}));
        // expected : [1, 1]
        System.out.println(pg_67258.solution(new String[]{"XYZ", "XYZ", "XYZ"}));
        // expected : [1, 5]
        System.out.println(pg_67258.solution(new String[]{"ZZZ", "YYY", "NNNN", "YYY", "BBB"}));
        // expected : [3,5]
        System.out.println(pg_67258.solution(new String[]{"A", "B" ,"B", "C", "A", "B", "C", "A","B","C"}));
        // expected : [12,15]
        System.out.println(pg_67258.solution(new String[]{"A", "B", "B", "B", "C", "D", "D", "D", "D", "D", "D", "D", "B", "C", "A"}));
        // expected : [8,12]
        System.out.println(pg_67258.solution(new String[]{"4","1","2","5","2","2","2","1","2","3","4","5"}));
    }
}
