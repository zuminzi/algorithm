package programmers.lv3;

import java.util.*;

/*
 * input : 이벤트 응모자 아이디 목록이 담긴 배열 user_id, 불량 사용자 아이디 목록이 담긴 배열 banned_id
 * output : 당첨에서 제외되어야 할 제재 아이디 목록은 몇가지 경우의 수가 가능한 지
 - 가리고자 하는 문자 하나에 '*' 문자 하나를 사용하였고 아이디 당 최소 하나 이상의 '*' 문자를 사용하였습니다.
 - Problem Point
   - 가능한 user_id 후보군(경우의 수) 만들 때, 조합이 아닌 순열로 접근
 - 코드 개선 이후 속도 차이
   - ~1487.85ms, 404MB -> ~134.49ms, 101MB
 */
public class PG_64064 {
    private static Set<String> combChecked;

    public int solution(String[] user_id, String[] banned_id) {
        combChecked = new HashSet<>();
        List<List<String>> candidates = new ArrayList<>();
        generateCandidates(user_id, banned_id, new ArrayList<>(), candidates);

        int count = 0;
        for (List<String> candidate : candidates) {
            if (isBanned(candidate, banned_id)) {
                // 3. candidate 요소 중에 순서만 다른 요소(즉, 동일하게 취급되어야 할 요소) 거르기
                // 정렬 후 combChecked로 중복 요소 제외
                Collections.sort(candidate);
                String str = String.join(" ", candidate);
                if (combChecked.add(str)) {
                    count++;
                }
            }
        }
        return count;
    }

    // 1. 조합 -> 순열 두 단계 거치지 말고, 바로 순열 알고리즘으로 경우의 수 구하기
    private void generateCandidates(String[] user_id, String[] banned_id, List<String> current, List<List<String>> candidates) {
        if (current.size() == banned_id.length) {
            candidates.add(new ArrayList<>(current));
            return;
        }

        for (String userId : user_id) {
            // 2. 제재 아이디 매치 여부를 line 37 라인 이후에 확인하는 대신 여기에서 확인하여 불필요한 수행 시간 줄이기
            if (!current.contains(userId) && matchesBanned(userId, banned_id[current.size()])) {
                current.add(userId);
                generateCandidates(user_id, banned_id, current, candidates);
                current.remove(current.size() - 1);
            }
        }
    }

    private boolean matchesBanned(String userId, String bannedId) {
        if (userId.length() != bannedId.length()) {
            return false;
        }

        for (int i = 0; i < userId.length(); i++) {
            if (bannedId.charAt(i) != '*' && bannedId.charAt(i) != userId.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBanned(List<String> candidate, String[] banned_id) {
        if (candidate.size() != banned_id.length) {
            return false;
        }

        for (int i = 0; i < candidate.size(); i++) {
            if (!matchesBanned(candidate.get(i), banned_id[i])) {
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