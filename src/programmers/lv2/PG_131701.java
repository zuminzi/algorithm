package programmers.lv2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PG_131701 {
    // Sliding Window
    // 첫 요소부터 움직이며 범위에 포함시키기
    // 하나를 빼면 하나를 더함
    // ~1790.25ms, 267MB
    public int codeOfMine(int[] elements) {
        HashSet<Integer> sum = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        for(int range = 1; range <= elements.length; range++){
            int arrIdx = 0;
            int loopCnt = 0;
            queue.clear();

            while (loopCnt < elements.length){
                while (queue.size() < range) {
                    if (arrIdx == elements.length) arrIdx = 0;
                    queue.add(elements[arrIdx++]);
                }
                sum.add( queue.stream().mapToInt(i -> i).sum() );
                queue.poll();
                loopCnt++;
            }
        }
        return sum.size();
    }

    // ~124.97ms, 112MB // BEST EFFICIENCY
    // startIdx(0부터 시작)
    // endIdx( 0 + 다음주기(각 length)부터 시작)
    public int exam1(int[] elements) {
        Set<Integer> set = new HashSet<>();

        for(int length = 0; length < elements.length; length++){
            int startIdx = 0;
            int endIdx = startIdx + length;
            int sum = 0;
            for(int i = 0; i <= endIdx; i++){
                sum += elements[i];
            }
            // 하나씩 삭제하고 하나씩 추가하는 작업을 do-while문 안에서 처리
            // do-while은 while문과는 다르게 무조건 1회는 실행
            do{
                set.add(sum);
                sum -= elements[startIdx++];
                if(endIdx == elements.length-1) endIdx = 0;
                else endIdx++;
                sum += elements[endIdx];
            }
            while(startIdx != elements.length);
        }

        return set.size();
    }

    // ~186.02ms, 122MB
    // 따로 제거하는 작업 없이 인덱스 0부터 수열 개수 횟수 채울 때까지 합 연산, 1부터 .., 2부터.. n까지
    public int exam2(int[] elements) {
        int max_sum = 0;
        // 모든 수열의 합은 따로 처리
        for (int i=0; i<elements.length; i++) {
            max_sum += elements[i];
        }
        HashSet<Integer> hashSet = new HashSet<>();
        int sum = 0;
        for (int j=0; j<elements.length; j++) {
            sum = elements[j];
            hashSet.add(sum);
            for (int i = j+1; i <= elements.length; i++) {
                if (i == elements.length) i = 0;
                sum += elements[i];
                if (sum > max_sum) break;
                else hashSet.add(sum);
            }
        }

        return hashSet.size();
    }

    public static void main(String[] args){
        PG_131701 pg_131701 = new PG_131701();
        System.out.println(pg_131701.codeOfMine(new int[]{7,9,1,1,4})); // expected : 18
    }
}
