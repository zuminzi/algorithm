package baekjoon;

import java.time.LocalDate;
import java.util.Scanner;

public class BOJ_1924 {
    private static String day[] = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int y = 2007;
        int m = sc.nextInt();
        int d = sc.nextInt();

        LocalDate localDate = LocalDate.of(y, m, d);
        int days = localDate.getDayOfYear();
        int daysSinceNewYear = 1;
        int diff =  days - daysSinceNewYear;

        System.out.println(day[diff % 7]);
    }
}
