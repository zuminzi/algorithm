package programmers.LV1;

import java.util.*;

public class GetReportResult_2 {
    /*
    ** 속도 비교
    * 인자값이 늘어날 수록 GetReportResult_1이 속도 면에서 효율적

    * todo: GetReportResult_2 속도 개선 -> for문 줄이기

    ** GetReportResult_1

테스트 1 〉	통과 (9.85ms, 75.5MB)
테스트 2 〉	통과 (10.09ms, 81.3MB)
테스트 3 〉	통과 (1396.59ms, 477MB)
테스트 4 〉	통과 (12.78ms, 79.2MB)
테스트 5 〉	통과 (10.77ms, 91.6MB)
테스트 6 〉	통과 (19.00ms, 93.6MB)
테스트 7 〉	통과 (27.72ms, 95.5MB)
테스트 8 〉	통과 (26.19ms, 108MB)
테스트 9 〉	통과 (684.26ms, 416MB)
테스트 10 〉	통과 (551.70ms, 423MB)
테스트 11 〉	통과 (1246.22ms, 522MB)
테스트 12 〉	통과 (14.83ms, 79.6MB)
테스트 13 〉	통과 (15.34ms, 85.8MB)
테스트 14 〉	통과 (385.20ms, 226MB)
테스트 15 〉	통과 (452.71ms, 391MB)
테스트 16 〉	통과 (12.71ms, 75.5MB)
테스트 17 〉	통과 (10.79ms, 79.5MB)
테스트 18 〉	통과 (13.80ms, 86.1MB)
테스트 19 〉	통과 (16.91ms, 85.4MB)
테스트 20 〉	통과 (445.50ms, 232MB)
테스트 21 〉	통과 (708.48ms, 384MB)
테스트 22 〉	통과 (1.34ms, 73.3MB)
테스트 23 〉	통과 (2.03ms, 77.5MB)
테스트 24 〉	통과 (1.30ms, 73.3MB)

     ** GetReportResult_2
테스트 1 〉	통과 (0.36ms, 75.2MB)
테스트 2 〉	통과 (0.85ms, 78.5MB)
테스트 3 〉	통과 (9856.22ms, 190MB)
테스트 4 〉	통과 (1.02ms, 81MB)
테스트 5 〉	통과 (0.92ms, 78.9MB)
테스트 6 〉	통과 (13.07ms, 93.5MB)
테스트 7 〉	통과 (15.12ms, 88.9MB)
테스트 8 〉	통과 (17.18ms, 98.6MB)
테스트 9 〉	통과 (1030.35ms, 154MB)
테스트 10 〉	통과 (1006.16ms, 133MB)
테스트 11 〉	통과 (5932.54ms, 187MB)
테스트 12 〉	통과 (9.75ms, 84.3MB)
테스트 13 〉	통과 (7.46ms, 84.5MB)
테스트 14 〉	통과 (1929.99ms, 126MB)
테스트 15 〉	통과 (2149.31ms, 165MB)
테스트 16 〉	통과 (3.45ms, 77MB)
테스트 17 〉	통과 (6.91ms, 76.8MB)
테스트 18 〉	통과 (11.97ms, 73.9MB)
테스트 19 〉	통과 (16.40ms, 85.4MB)
테스트 20 〉	통과 (1602.87ms, 141MB)
테스트 21 〉	통과 (3450.98ms, 162MB)
테스트 22 〉	통과 (0.38ms, 76MB)
테스트 23 〉	통과 (0.31ms, 75.2MB)
테스트 24 〉	통과 (0.39ms, 75MB)
     * */

    class ReportResult {
        String reported;
        List<String> reporterList; // 선언

        public ReportResult(String reported) {
            this.reported = reported;
            reporterList = new ArrayList<>(); // 새로운 객체 만들어질 때마다 reportList 리스트 생성
        }

        public List<String> getReportList() {
            return reporterList;
        }

    }

    public String codeOfMine(String[] id_list, String[] report, int k) {

        List<ReportResult> reportResults = new ArrayList<>();
        int[] mails = new int[id_list.length]; // 신고인이 메일 받은 횟수
        Map<String,Integer> idIndx = new HashMap<>(); // id로 조회용

        for(int i=0; i<id_list.length; i++) {
            idIndx.put(id_list[i], i);
            // ReportResult의 reported(피신고자) 멤버변수는 reporterList(신고자 목록) 멤버변수와 따로 생성
            // 그래야 피신고자별로 리스트 추가 가능
            reportResults.add(new ReportResult(id_list[i]));
        }

        Set<String> set = new HashSet<>();
        for(String id : report){
            if(set.add(id)) {
                reportResults.get(idIndx.get(id.split(" ")[1]))
                        .getReportList()
                        .add(id.split(" ")[0]);
            }
        }

        for(ReportResult reportResult : reportResults){
            if(reportResult.getReportList().size() >= k){
                for(String reporter : reportResult.getReportList()) {
                    mails[idIndx.get(reporter)]++;
                }
            }
        }

        return Arrays.toString(mails);
    }

    public static void main(String[] args){
        GetReportResult_2 getReportResult_2 = new GetReportResult_2();

        System.out.println(getReportResult_2.codeOfMine(
                new String[]{"muzi", "frodo", "apeach", "neo"},
                new String[]{"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"},
                2)); // expected : [2,1,1,0]

        System.out.println(getReportResult_2.codeOfMine(
                new String[] {"con", "ryan"},
                new String[] {"ryan con", "ryan con", "ryan con", "ryan con"},
                3)); // expected : [0,0]

        System.out.println(getReportResult_2.codeOfMine(
                new String[] {"muzi", "frodo", "apeach", "neo"},
                new String[] {"muzi frodo", "apeach frodo", "apeach neo", "muzi neo"},
                1)); // expected : [2,0,2,0]
    }
}