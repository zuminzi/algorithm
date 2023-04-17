package baekjoon;

import java.util.Scanner;

public class BOJ_1002 {
    // 류재명이 있을 수 있는 좌표의 수 == 두 원의 접점 개수
    // 원의 중심과 반지름 관계 이용
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int r1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            int r2 = sc.nextInt();
            // 두 원의 중심 사이의 거리
            double d = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
            // 두 원이 완전히 겹치는 경우
            if (d == 0 && r1 == r2) {
                System.out.println("-1");
            // 두 원이 만나지 않는 경우
            // 1. 두 원의 반지름 합보다 중심거리 더 긴 경우
            // 2. 한 원 안에 다른 원이 있으면서 접점이 없는 경우
            } else if (d > r1 + r2 || d < Math.abs(r1 - r2)) {
                System.out.println("0");
            // 두 원이 한 점에서 만나는 경우
            // 1.외접
            // 2.내접
            } else if (d == r1 + r2 || d == Math.abs(r1 - r2)) {
                System.out.println("1");
            // 이 외의 케이스는 두 원이 두 점에서 만나는 경우
            } else {
                System.out.println("2");
            }
        }
        sc.close();
    }
}
