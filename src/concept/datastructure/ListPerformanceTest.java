package concept.datastructure;

import java.util.*;

public class ListPerformanceTest {
    public static void main(String[] args){
        final int testDataSize = 5000;
        final int leftLimit = 97; // letter 'a'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 5;
        Random random = new Random();
        ArrayList<String> arList = new ArrayList<>();
        LinkedList<String> lkList = new LinkedList();

        for(int i=0; i<testDataSize; i++){
            String ran = random.ints(leftLimit,rightLimit + 1)
                    .filter(k -> (k <= 57 || k >= 65) && (k <= 90 || k >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            arList.add(ran);
            lkList.add(ran);
        }

//        System.out.println("=====search by value======");
//        System.out.println("ArrayList = " + test_search_by_value(random,testDataSize, leftLimit, rightLimit, targetStringLength, arList));
//        System.out.println("LinkedList = " + test_search_by_value(random,testDataSize, leftLimit, rightLimit, targetStringLength, lkList));
//
//        System.out.println("\n=====get by index======");
//        System.out.println("ArrayList = " + test_search_by_index(random,testDataSize, leftLimit, rightLimit, targetStringLength, arList));
//        System.out.println("LinkedList = " + test_search_by_index(random,testDataSize, leftLimit, rightLimit, targetStringLength, lkList));

        System.out.println("=====contains() 메서드와 (루프문 + break문) 수행시간 비교하기======");
        System.out.println("contains, loop+break = " + Arrays.toString(compare_contains_with_loop(random,testDataSize, leftLimit, rightLimit, targetStringLength, arList)));}

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

    private static long[] compare_contains_with_loop(Random random, int testDataSize, int leftLimit, int rightLimit, int targetStringLength, ArrayList<String> list){
        long[] answer = new long[2];
        long contains_start = 0;
        long contains_end = 0;
        long loop_start = 0;
        long loop_end = 0;

        for(int i = 0; i < testDataSize; i++) {
            // list size 안에서 랜덤숫자 생성
            int ran = random.nextInt(list.size());
            String target = list.get(ran);

            contains_start += System.currentTimeMillis(); // contains() 수행시간 측정 시작
            list.contains(target);
            contains_end += System.currentTimeMillis(); // contains() 수행시간 측정 끝

            answer[0] += contains_end - contains_start;

            loop_start += System.currentTimeMillis(); // loop 수행시간 측정 시작
            for(String element : list){
                if(element.equals(target)) {
                    break;
                }
            }
            loop_end += System.currentTimeMillis(); // loop 수행시간 측정 끝

            answer[1] += loop_end - loop_start;
        }
        answer[0] = answer[0]/ testDataSize;
        answer[1] = answer[1]/ testDataSize;

        return answer;
    }

}
