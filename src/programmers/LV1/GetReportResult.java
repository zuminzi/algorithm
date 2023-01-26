package programmers.LV1;

import java.util.*;

public class GetReportResult {
    /** point
    1. 특정 String 찾을 때, contains() X equals ()
        - contains는 '일부' 문자열의 포함관계를 조건으로 할 때
     2. 배열 중복 제거 방법
     2-1. stream distinct
        - Object의 equals로 비교하므로 객체 자체가 같은지를 비교
        - Object 자체도 같은 주소값을 가지는 경우는 가능하지만 안의 속성을 비교할 때는 사용 불가
     2-2. Set.add가 되는지 조건문으로 확인
     */

    public String codeOfMine(String[] id_list, String[] report, int k) {
        Map<String, String> reportResultMap = new HashMap<>();
        int[] mails = new int[id_list.length];
        //Set<String> set = new HashSet<>(); // deduplication way - 2

        // deduplication way - 1
        // String Array -> Object Stream -> String Array
        report = Arrays.stream(report).distinct().toArray(String[]::new);


                for (String report_id : report) {
                    //if (set.add(report_id)) {
                    String reported = report_id.split(" ")[1];
                    String reporter = report_id.split(" ")[0];

                    if (reportResultMap.containsKey(reported)) {
                        reportResultMap.put(reported, reportResultMap.get(reported) + " " + reporter);
                    } else {
                        reportResultMap.put(reported, reporter);
                    }
                    String[] reporter_list = reportResultMap.get(reported).split(" ");
                    if (reporter_list.length == k) {
                        for (String tmp : reporter_list) {
                            for (int i = 0; i < id_list.length; i++) {
                                if (tmp.equals(id_list[i]))
                                    mails[i]++;
                            }
                        }
                    }
                    if (reporter_list.length > k) {
                        for (int i = 0; i < id_list.length; i++) {
                            if (reporter.equals(id_list[i]))
                                mails[i]++;
                        }
                    }
                //}
                }

        return Arrays.toString(mails);
    }

    public static void main(String[] args){
        GetReportResult getReportResult = new GetReportResult();

        System.out.println(getReportResult.codeOfMine(
                new String[]{"muzi", "frodo", "apeach", "neo"},
                new String[]{"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"},
                2)); // expected : [2,1,1,0]

        System.out.println(getReportResult.codeOfMine(
                new String[] {"con", "ryan"},
                new String[] {"ryan con", "ryan con", "ryan con", "ryan con"},
                        3)); // expected : [0,0]

        System.out.println(getReportResult.codeOfMine(
            new String[] {"muzi", "frodo", "apeach", "neo"},
            new String[] {"muzi frodo", "apeach frodo", "apeach neo", "muzi neo"},
            1)); // expected : [2,0,2,0]
}
}
