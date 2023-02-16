package concept.datastructure;

import java.util.*;

public class ListPerformanceTest {
    public static void main(String[] args){
        final int testDataSize = 500;
        final int leftLimit = 97; // letter 'a'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 5;
        Random random = new Random();
        ArrayList arList = new ArrayList<>();
        LinkedList lkList = new LinkedList();

        for(int i=0; i<testDataSize; i++){
            String ran = random.ints(leftLimit,rightLimit + 1)
                    .filter(k -> (k <= 57 || k >= 65) && (k <= 90 || k >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            arList.add(ran);
            lkList.add(ran);
        }

        System.out.println("=====search by value======");
        System.out.println("ArrayList = " + test_search_by_value(random,testDataSize, leftLimit, rightLimit, targetStringLength, arList));
        System.out.println("LinkedList = " + test_search_by_value(random,testDataSize, leftLimit, rightLimit, targetStringLength, lkList));

        System.out.println("\n=====get by index======");
        System.out.println("ArrayList = " + test_search_by_index(random,testDataSize, leftLimit, rightLimit, targetStringLength, arList));
        System.out.println("LinkedList = " + test_search_by_index(random,testDataSize, leftLimit, rightLimit, targetStringLength, lkList));
    }

    private static long test_search_by_value(Random random, int testDataSize, int leftLimit, int rightLimit, int targetStringLength, List list){
        long start = System.currentTimeMillis();
        for(int i = 0; i < testDataSize; i++) {
            String ran = random.ints(leftLimit,rightLimit + 1)
                    .filter(k -> (k <= 57 || k >= 65) && (k <= 90 || k >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            list.indexOf(ran);
        }
        long end = System.currentTimeMillis();

        return end - start;
    }
    private static long test_search_by_index(Random random, int testDataSize, int leftLimit, int rightLimit, int targetStringLength, List list){
        long start = System.currentTimeMillis();
        for(int i = 0; i < testDataSize; i++) {
            int ran = random.nextInt(testDataSize);

            list.get(ran);
        }
        long end = System.currentTimeMillis();

        return end - start;
    }
}
