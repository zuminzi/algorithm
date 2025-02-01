package programmers.pccp;

public class PG_15009_121689 {
    // 입장과 퇴장 시간에 따라 최대 수용 인원 수를 구하는 슬라이딩 윈도우 + 매 순간 최적의 선택을 하는 그리디
    public int solution(int[] menu, int[] order, int k) {
        int maxCapacity = 0;
        final int HEAD_COUNT = order.length;
        int prevEndTime = 0;
        for(int i=0; i<HEAD_COUNT; i++){
            int entryTime = i * k;
            int exitTime = Math.max(entryTime, prevEndTime) + menu[order[i]];
            int adjustedExitTime = exitTime - 1;         // 동일 시각에 입출이 발생하면 동시 수용에 해당하지 않음 (1초 차이로 겹치는 경우를 처리)
            int maxIndex = adjustedExitTime / k;
            int capacity = maxIndex - i + 1;
            int remain = HEAD_COUNT - i;
            maxCapacity = Math.max(maxCapacity, Math.min(capacity, remain));
            prevEndTime = exitTime;
        }
        return maxCapacity;
    }

    public static void main(String[] args) {
        PG_15009_121689 pg_15009_121689 = new PG_15009_121689();
        System.out.println(pg_15009_121689.solution(new int[]{5, 12, 30}, new int[]{1, 2, 0, 1}, 10));
        System.out.println(pg_15009_121689.solution(new int[]{5, 12, 30}, new int[]{2, 1, 0, 0, 0, 1, 0}, 10));
        System.out.println(pg_15009_121689.solution(new int[]{5}, new int[]{0, 0, 0, 0, 0}, 5));
    }
}
