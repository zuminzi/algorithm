package programmers.lv3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PG_17676 {
    final int OFFSET_MILLS = 1; // 보정 밀리초 // 0.001s
    final int OFFSET_ONE_SEC = 1000 - OFFSET_MILLS; // 보정 1초 // 1s - 0.001s

    /**
     * 구간별 처리량을 계산하고 초당 최대 처리량을 찾는 문제
        - 초당 최대 처리량: 응답 완료여부에 상관없이 임의시간부터 1초(1000밀리초)간 처리하는 최대 건수
        - 처리시간은 소수점 셋째자리까지 기록 (65 line)
     * 1) 각 로그 라인에서 종료시간을 추출하여 시작시간 계산 후 자료구조에 저장
     * 2) 각 구간의 시작과 끝을 기준으로 최대 처리량 계산
     * Double -> long 변환: Math.round()
     */
    public int solution(String[] lines) {
        int maxThroughput = 0;
        List<LocalDateTime[]> list = new ArrayList<>();

        for(String line:lines){
            calculateStartTime(list, line);
        }

        maxThroughput = getMaxThroughput(maxThroughput, list);

        return maxThroughput;
    }

    private int getMaxThroughput(int maxThroughput, List<LocalDateTime[]> list) {
        LocalDateTime intervalStart = list.get(0)[0];
        for(int i = 0; i< list.size(); i++){
            int throughput = 0;

            LocalDateTime intervalEnd = intervalStart.plusNanos(OFFSET_ONE_SEC * 1_000_000);

            for(int j = 0; j< list.size(); j++){
                LocalDateTime[] target = list.get(j);
                LocalDateTime start = target[0];
                LocalDateTime end = target[1];

                // 예제에 의해 같은 경우도 고려해야 함을 확인
                if((intervalEnd.isEqual(start) || intervalEnd.isAfter(start))
                        && (intervalStart.equals(end) || intervalStart.isBefore(end))) {
                    throughput++;
                }
            }
            maxThroughput = Math.max(maxThroughput, throughput);
            intervalStart = list.get(i)[1];
        }
        return maxThroughput;
    }

    private void calculateStartTime(List<LocalDateTime[]> list, String line) {
        String[] strs = line.split("\s");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String strEndDate = strs[0] + "\s" + strs[1];
        LocalDateTime endDate = LocalDateTime.parse(strEndDate, formatter);

        // 소수점이 필요하기 때문에 실행 초를 처음 담는 타입은 Double
        long executionMills = Math.round(Double.parseDouble(strs[2].split("s")[0]) * 1_000);
        long executionNano = executionMills * 1_000_000;
        long offsetNano = OFFSET_MILLS * 1_000_000;

        LocalDateTime startTime = endDate.minusNanos(executionNano).plusNanos(offsetNano);

        list.add(new LocalDateTime[]{startTime, endDate});
    }

    public static void main(String[] args) {
        PG_17676 pg_17676 = new PG_17676();
        // expected : 1
        System.out.println(pg_17676.solution(new String[]{"2016-09-15 00:00:00.000 3s"}));
        // expected : 1
        System.out.println(pg_17676.solution(new String[]{
                "2016-09-15 01:00:04.001 2.0s",
                "2016-09-15 01:00:07.000 2s"})
        );
        // expected : 2
        System.out.println(pg_17676.solution(new String[]{
                "2016-09-15 01:00:04.002 2.0s",
                "2016-09-15 01:00:07.000 2s"})
        );
        // expected : 7
        System.out.println(pg_17676.solution(new String[]{
                "2016-09-15 20:59:57.421 0.351s",
                "2016-09-15 20:59:58.233 1.181s",
                "2016-09-15 20:59:58.299 0.8s",
                "2016-09-15 20:59:58.688 1.041s",
                "2016-09-15 20:59:59.591 1.412s",
                "2016-09-15 21:00:00.464 1.466s",
                "2016-09-15 21:00:00.741 1.581s",
                "2016-09-15 21:00:00.748 2.31s",
                "2016-09-15 21:00:00.966 0.381s",
                "2016-09-15 21:00:02.066 2.62s"})
        );
    }
}