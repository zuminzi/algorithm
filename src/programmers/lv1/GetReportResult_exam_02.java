package programmers.lv1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/* HashMap으로 조회 성능 개선, 각 User 객체의 reportList,reportedList로 관리 */
public class GetReportResult_exam_02 {
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];
        ArrayList<User> users = new ArrayList<>();
        HashMap<String,Integer> suspendedList = new HashMap<>(); //<이름>
        HashMap<String,Integer> idIdx = new HashMap<String,Integer>(); // <이름, 해당 이름의 User 클래스 idx>
        int idx = 0;

        // 검색속도 빠른 HashMap으로 idIdx 생성
        for(String name : id_list) {
            idIdx.put(name,idx++);
            // 이 때 id별 모든 User 객체도 생성
            users.add(new User(name));
        }

        // 신고자와 피신고자 둘 다 개별적으로 관리하는 (신고/피신고)리스트에 추가
        // 신고자.reportList.add(피신고자);
        // 피신고자.reportedList.add(신고자)
        for(String re : report){
            String[] str = re.split(" ");
            //suspendedCount.put(str[0], suspendedCount.getOrDefault(str[0],0)+1);
            users.get( idIdx.get(str[0])).reportList.add(str[1]);
            users.get( idIdx.get(str[1])).reportedList.add(str[0]);
        }

        // k 횟수 이상 신고받아서 중단된 명단 초기화
        for(User user : users){
            if(user.reportedList.size() >= k)
                suspendedList.put(user.name,1);
        }

        // 각 User가 신고한 사람이 중단 명단에 있으면, answer cnt(mailing cnt)++;
        for(User user : users){
            for(String nameReport : user.reportList){
                if(suspendedList.get(nameReport) != null){
                    answer[idIdx.get(user.name)]++;
                }

            }
        }

        return answer;
    }
}

class User{
    String name;
    HashSet<String> reportList;
    HashSet<String> reportedList;
    public User(String name){
        this.name = name;
        reportList = new HashSet<>();
        reportedList = new HashSet<>();
    }
}
/* Accuracy Test : show efficient performance

테스트 1 〉	통과 (0.45ms, 81.6MB)
테스트 2 〉	통과 (0.58ms, 76.5MB)
테스트 3 〉	통과 (215.38ms, 150MB)
테스트 4 〉	통과 (0.65ms, 82.4MB)
테스트 5 〉	통과 (0.73ms, 73.5MB)
테스트 6 〉	통과 (4.21ms, 81.9MB)
테스트 7 〉	통과 (6.50ms, 83.9MB)
테스트 8 〉	통과 (18.87ms, 101MB)
테스트 9 〉	통과 (107.11ms, 125MB)
테스트 10 〉	통과 (115.81ms, 147MB)
테스트 11 〉	통과 (190.78ms, 166MB)
테스트 12 〉	통과 (1.64ms, 74.1MB)
테스트 13 〉	통과 (1.67ms, 76.7MB)
테스트 14 〉	통과 (117.78ms, 146MB)
테스트 15 〉	통과 (175.53ms, 160MB)
테스트 16 〉	통과 (1.16ms, 79.1MB)
테스트 17 〉	통과 (1.59ms, 74MB)
테스트 18 〉	통과 (2.14ms, 84.9MB)
테스트 19 〉	통과 (4.35ms, 80MB)
테스트 20 〉	통과 (117.57ms, 128MB)
테스트 21 〉	통과 (205.92ms, 164MB)
테스트 22 〉	통과 (0.46ms, 72.6MB)
테스트 23 〉	통과 (0.41ms, 77MB)
테스트 24 〉	통과 (0.37ms, 73.8MB)
*/
