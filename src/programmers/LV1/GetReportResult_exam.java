package programmers.LV1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GetReportResult_exam {
    public int[] exam1(String[] id_list, String[] report, int k) {
        // 중복 제거
        List<String> list = Arrays.stream(report).distinct().toList();

        // 피신고자, 신고 받은 횟수 map
        HashMap<String, Integer> count = new HashMap<>();
        for (String s : list) {
            String target = s.split(" ")[1];
            count.put(target, count.getOrDefault(target, 0) + 1);
        }

        return Arrays.stream(id_list).map(_user -> {
            // todo: final 지역변수 사용/미사용 케이스 차이 -> 미사용 시 다른 TC에서 실패
            final String user = _user;

            // user + " " 공백을 둔 이유 : 포함관계(ex.neo, neos)는 배제하기 위해
            // filter에서 startsWith을 사용한 이유 : reportList에는 신고자(즉 s.split(" ")[0])를 넣을 예정이므로
            // contains (X)
            // equals (X) -> list가 "신고자 피신고자"로 구성되어있기 때문에 equals()를 사용하려면 split처리가 필요
            List<String> reportList = list.stream().filter(s -> s.startsWith(user + " ")).collect(Collectors.toList());

            // 신고자가 신고한 사람 중 신고 받은 횟수(count.getOrDefault(s.split(" ")[1], 0))가 k 이상(>=k)인 사람 count
            return reportList.stream().filter(s -> count.getOrDefault(s.split(" ")[1], 0) >= k).count();
        }).mapToInt(Long::intValue).toArray();
    }

    public static void main(String[] args){
        GetReportResult_exam getReportResult_exam = new GetReportResult_exam();

        System.out.println(getReportResult_exam.exam1(
                new String[] {"muzi", "frodo", "apeach", "neo"},
                new String[] {"muzi frodo", "apeach frodo", "apeach neo", "muzi neo"},
                1)); // expected : [2,0,2,0]
    }
}
