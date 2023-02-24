package programmers.LV2;


import java.util.*;

public class PG_42577 {
    // Accuracy Test ~4.54ms, 76MB, Efficiency Test ~303.91ms, 98MB
    public boolean codeOfMine(String[] phone_book) {
        boolean answer = true;

        Arrays.sort(phone_book);
        for(int i=0; i<phone_book.length-1; i++){
            // Problem Point
            // 사전순으로 정렬할 경우 -> i+1이 i의 접두어가 아니라면, 이후는 비교할 필요도 X
                if(phone_book[i+1].startsWith(phone_book[i])){
                    answer = false;
                    break;
                }
            }
        return answer;
    }

    // Accuracy Test success, Efficiency Test fail
    public boolean fail_sol(String[] phone_book) {
        boolean answer = true;
        // 길이 짧은 순 정렬
        Arrays.sort(phone_book, (String s1, String s2) -> s1.length() - s2.length());
        for(int i=0; i<phone_book.length; i++){
            for(int k=phone_book.length-1; k>i;k--){
                if(phone_book[k].startsWith(phone_book[i])){ // 접두어는 짧은 길이부터, 비교 대상은 긴 길이부터
                answer = false;
                break;
                }
            }
        }
        return answer;
    }

    public static void main(String[] args){
        PG_42577 pg_42577 = new PG_42577();
        System.out.println(pg_42577.codeOfMine(new String[]{"119", "97674223", "1195524421"})); // expected : false
        System.out.println(pg_42577.codeOfMine(new String[]{"123","456","789"})); // expected : true
        System.out.println(pg_42577.codeOfMine(new String[]{"12","123","1235","567","88"})); // expected : false
    }
}
