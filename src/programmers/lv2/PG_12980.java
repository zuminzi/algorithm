package programmers.lv2;

public class PG_12980 {
    // 순간이동(현재 온 거리*2) 시 건전지 사용량 x
    // k칸 점프 시 k만큼의 건전지 사용량 o
    // output = 건전지 사용량 최솟값

    // 1(10), 2(100), 4(100), 8(1000), 16(10000) .. 이진수들은 동일한 건전지 사용량 지님
    // Integer.toBinaryString() -> 주어진 정수에서 true bit 값 찾는 메서드
    public int solution(int n) {
        System.out.println(Integer.toBinaryString(n));
        int countOne = Integer.bitCount(n);

        return countOne;
    }
    public static void main(String[] args) {
        PG_12980 pg_12980 = new PG_12980();
        System.out.println(pg_12980.solution(5)); // expected : 2
        System.out.println(pg_12980.solution(6)); // expected : 2
        System.out.println(pg_12980.solution(5000)); // expected : 5
    }
}
