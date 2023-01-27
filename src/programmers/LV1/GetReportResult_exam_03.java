package programmers.LV1;

import java.util.*;

/* 중복 제거 자료구조 필요 시 Set으로 관리하기 -> Map<String, Set<String>> */
public class GetReportResult_exam_03 {
    public int[] solution(String[] id_list, String[] report, int k) {
        // key: 신고당한놈, value: 몇명한테 당했는지
        Map<String, Set<String>> map = new HashMap<>();

        for (String rep : report) {
            String[] arr = rep.split(" ");
            Set<String> set = map.getOrDefault(arr[1], new HashSet<>());
            set.add(arr[0]);
            map.put(arr[1], set);
        }

        // key: 알림받을 놈, value: 몇번 알림받을지
        Map<String, Integer> countMap = new LinkedHashMap<>();

        for (String id : id_list) { // 초기화
            countMap.put(id, 0);
        }

        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            // 따로 신고받은 횟수 관리할 필요 없이 *신고한 놈들 명단 size*로 조건문 구현
            if (entry.getValue().size() >= k) { // 정지당할놈
                for (String value : entry.getValue()) {
                    countMap.put(value, countMap.getOrDefault(value, 0) + 1);
                }
            }
        }

        // countMap의 values만 Array로 변환하여 바로 결과 리턴
        return countMap.values().stream().mapToInt(Integer::intValue).toArray();
    }
}
/* Accuracy Test : show efficient performance

테스트 1 〉	통과 (3.31ms, 82MB)
테스트 2 〉	통과 (2.36ms, 75.5MB)
테스트 3 〉	통과 (185.91ms, 160MB)
테스트 4 〉	통과 (3.62ms, 76.9MB)
테스트 5 〉	통과 (2.99ms, 70.9MB)
테스트 6 〉	통과 (8.95ms, 87.5MB)
테스트 7 〉	통과 (9.36ms, 93.2MB)
테스트 8 〉	통과 (14.37ms, 96.7MB)
테스트 9 〉	통과 (188.62ms, 129MB)
테스트 10 〉	통과 (100.96ms, 126MB)
테스트 11 〉	통과 (149.00ms, 184MB)
테스트 12 〉	통과 (5.05ms, 82.9MB)
테스트 13 〉	통과 (3.57ms, 79.8MB)
테스트 14 〉	통과 (109.30ms, 127MB)
테스트 15 〉	통과 (132.24ms, 169MB)
테스트 16 〉	통과 (3.50ms, 76MB)
테스트 17 〉	통과 (3.72ms, 77.1MB)
테스트 18 〉	통과 (6.07ms, 78MB)
테스트 19 〉	통과 (6.91ms, 87.7MB)
테스트 20 〉	통과 (97.15ms, 129MB)
테스트 21 〉	통과 (149.45ms, 177MB)
테스트 22 〉	통과 (3.04ms, 78.9MB)
테스트 23 〉	통과 (2.06ms, 72.3MB)
테스트 24 〉	통과 (2.97ms, 80.4MB)
*/
