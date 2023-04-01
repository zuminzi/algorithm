package programmers.lv2;

import java.util.*;

public class PG_131127 {
    // ~94.42ms, 117MB
    public int codeOfMine(String[] want, int[] number, String[] discount) {
        int answer = 0;
        int total = 0;
        Map<String, Integer> wishList = new HashMap<>();
        Queue<String> check = new LinkedList<>();
        for(int i=0; i< want.length; i++){
            wishList.put(want[i], number[i]);
            total += number[i];
        }

        for (String item : discount) {
            if (wishList.containsKey(item)) {
                // Point 1
                // wishlist에서 수량 0개 됐다고 삭제하면 삭제한 이후에 중복되는 요소 체크 불가
                wishList.put(item, wishList.get(item) - 1);
                check.add(item);
            // Point 2 // 원하는 품목이 아니더라도 check 컬렉션에 넣어야 size == total? 비교 가능
            } else {
                check.add(item);
            }
            if (check.size() == total) {
                // Point 3
                // 원하는 품목 수량 정확한 개수 비교
                if(wishList.values().stream().allMatch(el -> el < 1)) answer++;
                String removed = check.poll();
                if(wishList.containsKey(removed)) wishList.put(removed, wishList.getOrDefault(removed,0) + 1);
            }
        }
        return answer;
    }

    public static void main(String[] args){
        PG_131127 pg_131127 = new PG_131127();
        // expected : 2
        System.out.println(pg_131127.codeOfMine(new String[]{"banana", "apple", "rice", "pork", "pot"},
                new int[]{3, 2, 2, 2, 1},
                new String[]{"chicken", "apple", "apple", "strawberry","banana", "rice", "apple", "pork", "banana", "pork", "rice", "pot", "banana", "apple", "banana"}));
        // expected : 3
        System.out.println(pg_131127.codeOfMine(new String[]{"banana", "apple", "rice", "pork", "pot"},
                new int[]{3, 2, 2, 2, 1},
                new String[]{"chicken", "apple", "apple", "banana", "rice", "apple", "pork", "banana", "pork", "rice", "pot", "banana", "apple", "banana"}));
        // expected : 0
        System.out.println(pg_131127.codeOfMine(new String[]{"apple"},
                new int[]{10},
                new String[]{"banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana"}));
    }
}