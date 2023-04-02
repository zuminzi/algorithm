package programmers.lv3;

import java.util.*;

public class PG_43163 {
    // ~0.63ms, 75.6MB
    // BFS
    class Conversion {
        String word;
        int total;
        Conversion(String word, int total) {
            this.word = word;
            this.total = total;
        }
    }

    private static int ans;
    public int codeOfMine(String begin, String target, String[] words) {
        ans = 0;
        List<String> wordList = new ArrayList<>(List.of(words));

        if (wordList.contains(target) == false) return 0;
        convertToTarget(begin, wordList, target);
        return ans;
    }

    private void convertToTarget(String str, List<String> wordList, String target) {
        Queue<Conversion> queue = new LinkedList<>();
        queue.offer(new Conversion(str, ans));
        wordList.remove(str); // 방문 체크 // 메서드 처음 실행 시에만

        while (!queue.isEmpty()) {
            Conversion cur = queue.poll();
            str = cur.word;
            int total = cur.total;
            if(str.equals(target)) {
                // 횟수 적은 순으로 queue.poll()되므로 Math.min() 비교 필요 X
                ans = total;
                return;
            }
            for (int i = 0; i < wordList.size(); i++) {
                String comparator = wordList.get(i);
                if (comparator.equals(str)) continue;
                int check = 0;
                int k = 0;
                for (; k < str.length(); k++) {
                    if (str.charAt(k) == comparator.charAt(k)) {
                        check++;
                    }
                }
                if (check >= str.length() - 1) {
                    total++;
                    queue.offer(new Conversion(comparator, total));
                    wordList.remove(comparator); // 방문 체크
                }
            }
        }
    }

    public static void main(String[] args){
        PG_43163 pg_43163 = new PG_43163();
        // expected : 4
        System.out.println(pg_43163.codeOfMine("hit","cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"}));
        // expected : 0
        System.out.println(pg_43163.codeOfMine("hit","cog", new String[]{"hot", "dot", "dog", "lot", "log"}));
    }
}
