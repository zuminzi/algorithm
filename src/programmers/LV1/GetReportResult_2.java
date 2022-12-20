package programmers.LV1;

import java.util.*;

public class GetReportResult_2 {

    class ReportResult {
        String reporter;
        String reported;

        public ReportResult(String reporter, String reported) {
            this.reporter = reporter;
            this.reported = reported;
        }

        public String getReporter() {
            return reporter;
        }

        public String getReported() {
            return reported;
        }

    }

    public String solution(String[] id_list, String[] report, int k) {

        List<ReportResult> reportResults = new ArrayList<>();
        int[] report_cnt = new int[id_list.length]; // 피신고인의 신고 받은 횟수
        int[] mails = new int[id_list.length]; // 신고인이 메일 받은 횟수

        Set<String> set = new HashSet<>();
        for(String id : report){
            if(set.add(id)) {
                reportResults.add(new ReportResult(id.split(" ")[0], id.split(" ")[1]));
            }
        }

        for(int i=0; i< id_list.length; i++){
            for(ReportResult reportResult : reportResults){
                if(reportResult.getReported().equals(id_list[i])){
                    report_cnt[i]++;
                }

            }
        }

        for(int i=0; i<report_cnt.length; i++){
            if(report_cnt[i] >= k){
                for(ReportResult reportResult: reportResults){
                    if(id_list[i].equals(reportResult.getReported())){
                        mails[Arrays.asList(id_list).indexOf(reportResult.getReporter())]++;
                    }
                }
            }
        }

        return Arrays.toString(mails);
    }

    public static void main(String[] args){
        GetReportResult_2 getReportResult_2 = new GetReportResult_2();

        System.out.println(getReportResult_2.solution(
                new String[]{"muzi", "frodo", "apeach", "neo"},
                new String[]{"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"},
                2)); // expected : [2,1,1,0]

        System.out.println(getReportResult_2.solution(
                new String[] {"con", "ryan"},
                new String[] {"ryan con", "ryan con", "ryan con", "ryan con"},
                3)); // expected : [0,0]

        System.out.println(getReportResult_2.solution(
                new String[] {"muzi", "frodo", "apeach", "neo"},
                new String[] {"muzi frodo", "apeach frodo", "apeach neo", "muzi neo"},
                1)); // expected : [2,0,2,0]
    }
}