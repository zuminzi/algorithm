package programmers.lv3;

import java.util.*;

/*
 * input : 이벤트 응모자 아이디 목록이 담긴 배열 user_id, 불량 사용자 아이디 목록이 담긴 배열 banned_id
 * output : 당첨에서 제외되어야 할 제재 아이디 목록은 몇가지 경우의 수가 가능한 지
 - 가리고자 하는 문자 하나에 '*' 문자 하나를 사용하였고 아이디 당 최소 하나 이상의 '*' 문자를 사용하였습니다.
 - Problem Point
   - 가능한 user_id 후보군(경우의 수) 만들 때, 조합이 아닌 순열로 접근
 */
public class PG_64064 {
    private static String[] banned_id;
    private static int point;
    private static Set<String> combChecked;
    public int solution(String[] user_id, String[] banned_id) {
        this.banned_id = banned_id;
        point = 0;
        combChecked = new HashSet<>();
        comb(user_id, banned_id.length, new boolean[user_id.length],0);
        return point;
    }

    private void comb(String[] arr, int r, boolean[] visited, int depth) {
        if(depth == r){
            checkIfBanned(arr, visited);
            return;
        }

        for(int i=0; i<arr.length; i++){
            if(!visited[i]) {
                visited[i] = true;
                comb(arr, r, visited, depth + 1);
                visited[i] = false;
            }
        }
    }

    private void checkIfBanned(String[] arr, boolean[] visited) {
        List<String> bannedList = new ArrayList<>();
        boolean[] used = new boolean[banned_id.length];
        int count = 0;
        for(int i=0; i<arr.length; i++){
            if(visited[i]){
                bannedList.add(arr[i]);
                for(int k=0; k<banned_id.length; k++){
                    if(!used[k] && isBanned(arr[i], banned_id[k])){
                        used[k] = true;
                        count++;
                        break;
                    }
                }
            }
        }
        if(count == banned_id.length){
            Collections.sort(bannedList);
            String str = String.join(" ", bannedList);
            if(combChecked.add(str)) {
                point++;
            }
        } else {
            perm(bannedList.toArray(new String[0]), new String[bannedList.size()], new boolean[bannedList.size()], 0);
        }
    }

     private void perm(String[] arr, String[] output, boolean[] visited, int depth) {
        if (depth == arr.length) {
            checkIfBanned(output);
            return;
        }

        for (int i=0; i<arr.length; i++) {
            if (visited[i] != true) {
                visited[i] = true;
                output[depth] = arr[i];
                perm(arr, output, visited, depth + 1);
                visited[i] = false;;
            }
        }
    }

    private void checkIfBanned(String[] output) {
        List<String> bannedList = new ArrayList<>();
        boolean[] used = new boolean[banned_id.length];
        int count = 0;
        for(int i=0; i<output.length; i++){
                for(int k=0; k<banned_id.length; k++){
                    if(!used[k] && isBanned(output[i], banned_id[k])){
                        used[k] = true;
                        count++;
                        bannedList.add(output[i]);
                        break;
                    }
                }
            }
        if(count == banned_id.length){
            Collections.sort(bannedList);
            String str = String.join(" ", bannedList);
            if(combChecked.add(str)) {
                point++;
            }
        }
    }

    private boolean isBanned(String userId, String bannedId) {
        if (userId.length() != bannedId.length()) {
            return false;
        }
        for (int i = 0; i < bannedId.length(); i++) {
            if (bannedId.charAt(i) != '*' && bannedId.charAt(i) != userId.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        PG_64064 pg_64064 = new PG_64064();
        // expected : 2
        System.out.println(pg_64064.solution(new String[]{"frodo", "fradi", "crodo", "abc123", "frodoc"}, new String[]{"fr*d*", "abc1**"}));
        // expected : 2
        System.out.println(pg_64064.solution(new String[]{"frodo", "fradi", "crodo", "abc123", "frodoc"}, new String[]{"*rodo", "*rodo", "******"}));
        // expected : 3
        System.out.println(pg_64064.solution(new String[]{"frodo", "fradi", "crodo", "abc123", "frodoc"}, new String[]{"fr*d*", "*rodo", "******", "******"}));
    }
}
