package programmers.lv2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PG_17680 {
    // 문제에서 0일 수 있는 조건 처리 잘 하기

    // ~57.99ms, ~126MB
    // LinkedList
    public int codeOfMine(int cacheSize, String[] cities) {
        int runTime = 0;
        LinkedList<String> cache = new LinkedList<>();
        for (String city : cities) {
            // Problem Point 1 - 대소문자 구분하지 않는 조건 처리 X
            String target = city.toUpperCase();
            if (cache.contains(target)) { // O(n)
                runTime += 1;
                cache.remove(target); // O(n)
            } else {
                runTime += 5;
                // Problem Point 2 - 리스트 size 0 예외 처리 X
                if (cache.size() != 0 &&
                        cache.size() >= cacheSize) {cache.removeFirst();} // O(1)
            }
            // Problem Point 2 - 리스트 size 0 예외 처리 X
            if(cacheSize != 0){ cache.add(target); } // O(1)
        }
        return runTime;
    }

    // ~38.91ms, ~125MB
    // ArrayList
    public int codeOfMine_re(int cacheSize, String[] cities) {
        int runTime = 0;
        List<String> cache = new ArrayList<>();
        for (String city : cities) {
            // Problem Point 1 - 대소문자 구분하지 않는 조건 처리 X
            String target = city.toUpperCase();
            if (cache.contains(target)) {
                runTime += 1;
                cache.remove(target);
            } else {
                runTime += 5;
                // Problem Point 2 - 리스트 size 0 예외 처리 X
                if (cache.size() != 0 &&
                        cache.size() >= cacheSize) {cache.remove(0);}
            }
            // Problem Point 2 - 리스트 size 0 예외 처리 X
            if(cacheSize != 0){ cache.add(target); }
        }
        return runTime;
    }

    public static void main(String[] args){
        PG_17680 pg_17680 = new PG_17680();
        System.out.println(pg_17680.codeOfMine(3,
                new String[] {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"}));
        // expected : 50
        System.out.println(pg_17680.codeOfMine(3,
                new String[]{"Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul"}));
        // expected : 21
        System.out.println(pg_17680.codeOfMine(2,
                new String[]{"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Seoul", "Rome", "Paris", "Jeju", "NewYork", "Rome"}));
        // expected : 60
        System.out.println(pg_17680.codeOfMine(5,
                new String[]{"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Seoul", "Rome", "Paris", "Jeju", "NewYork", "Rome"}));
        // expected : 52
        System.out.println(pg_17680.codeOfMine(2,
                new String[]{"Jeju", "Pangyo", "NewYork", "newyork"}));
        // expected : 16 // wrong
        System.out.println(pg_17680.codeOfMine(0,
                new String[]{"Jeju", "Pangyo", "Seoul", "NewYork", "LA"}));
        // expected : 25
        System.out.println(pg_17680.codeOfMine(0,
                new String[]{"LA","La"}));
        // expected : 10
    }
}
