package concept.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class LocalDateTimeTest {
    public static void main(String[] args) {
        // LocalDateTime 객체 생성
        int year = 2023;
        int month = 10;
        int day = 21;
        int hour = 15;
        int minute = 16;
        LocalDateTime obj = LocalDateTime.of(year, month, day, hour, minute);
        System.out.println(obj); // 2023-10-21T15:16

        // 패턴 적용
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = obj.format(formatter);
        System.out.println(str); // 2023-10-21 15:16:00

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd a HH:mm:ss");
        String str2 = obj.format(formatter2);
        System.out.println(str2); // 2023-10-21 오후 15:16:00

        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm:ss");
        String str3 = obj.format(formatter3);
        System.out.println(str3); // 2023-10-21 오후 03:16:00

        // Str -> LocalDateTime 변환
        LocalDateTime obj2 = LocalDateTime.parse(str, formatter);
        System.out.println(obj2); // 2023-10-21T15:16

        // ChronoField 특정 필드 가져오기
        System.out.println(obj2.get(ChronoField.DAY_OF_WEEK)); // 6 // 요일(1~7)
        System.out.println(obj2.get(ChronoField.DAY_OF_YEAR)); // 294 // 연도를 기준으로 일자 계산
        System.out.println(obj2.get(ChronoField.DAY_OF_MONTH)); // 21 // 달을 기준으로 일자 계산
        System.out.println(obj2.get(ChronoField.HOUR_OF_DAY)); // 15 // 하루를 기준으로 시간 계산
        System.out.println(obj2.get(ChronoField.MINUTE_OF_DAY)); // 916(15*60 + 16) // 하루를 기준으로 분 계산

        // LocalDateTime 메서드 활용하여 특정 필드 가져오기
        System.out.println(obj2.getDayOfWeek()); // SATURDAY
        System.out.println(obj2.getDayOfYear()); // 294
        System.out.println(obj2.getDayOfMonth()); // 21
        System.out.println(obj2.getHour()); // 15
        System.out.println(obj2.getMinute()); // 16
        System.out.println(obj2.getMinute() + (obj2.getHour() * 60)); // 916 // MINUTE_OF_DAY

        // 날짜 및 시간 산수계산하기
        System.out.println(obj2.plusDays(1));
        System.out.println(obj2.minusDays(1));
        System.out.println(obj2.plusHours(1));
        System.out.println(obj2.minusHours(1));
        System.out.println(obj2.plusMinutes(10));
        System.out.println(obj2.minusMinutes(10));

        // 날짜 및 시간 비교하기
        System.out.println(obj.isBefore(obj2)); // false // obj이 obj2보다 이전 날짜인가?
        System.out.println(obj.isAfter(obj2)); // false // obj이 obj2보다 이후 날짜인가?
        System.out.println(obj.isEqual(obj2)); // true // obj이 obj2와 같은 날짜 및 시간인가?
    }
}
