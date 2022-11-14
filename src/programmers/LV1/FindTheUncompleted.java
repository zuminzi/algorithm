package programmers.LV1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindTheUncompleted {
    /** Todo
    * 루프문 반복 횟수 최소한으로 줄이기 (효율 up)
    * 1) 찾으면 끝나는 루프문은 break; 적극 활용
    * 2) 특정 인덱스끼리만 비교하면 되는 문제는 전체 길이로 루프문 돌리지 않도록 주의
    */
    public String codeOfMine(String[] participant, String[] completion) {
        String answer = "";

        Arrays.sort(participant);
        Arrays.sort(completion);

        for(int i=participant.length-1; i>=0; i--) {
            // completion.length = participant.length - 1 이므로
            // participant[0](==첫번째 요소)일 때만 completion[0]과 비교
            if (i == 0) {
                if (completion[0].equals(participant[0])) {
                    completion[0] = completion[0] + "checked";
                    participant[0] = participant[0] + "checked";
                }

            // participant[participant.length-1](==마지막 요소)일 때만 completion[i-1]과 비교
            } else if (i == participant.length - 1) {
                        if (completion[i-1].equals(participant[i])) {
                            completion[i-1] = completion[i-1] + "checked";
                            participant[i] = participant[i] + "checked";
                        }

            // 나머지 경우에는 participant[i]와 completion[i] 또는 completion[i-1]과 비교
            } else {
                    for (int j = i; j > i - 2; j--) {
                        if (completion[j].equals(participant[i])) {
                            completion[j] = completion[j] + "checked";
                            participant[i] = participant[i] + "checked";
                            break;
                        }
                    }
                }
            }

        for(String temp: participant){
            if(!temp.contains("checked")) {
                answer = temp;
                break;
            }
        }
        return answer;
    }

    /** getOrDefault(Object key, V DefaultValue)

     찾는 키가 존재한다면 찾는 키의 값을 반환하고 없다면 기본 값을 반환
     */
    public String exam1(String[] participant, String[] completion) {
        String answer = "";
        HashMap<String, Integer> hm = new HashMap<>();

        // 기존에 값이 없으면 {participant_name,1}로 저장, 값이 있으면 {participant_name, 기존값+1}로 저장
        for (String player : participant) {hm.put(player, hm.getOrDefault(player, 0) + 1);}
        //System.out.println("hm 1차="+hm); // hm 1차={ana=1, mislav=2, stanko=1} // 중복인 mislav는 2로 저장

        // {completion_name,기존값 -1} 형태로 중복 player들만 value 값이 덮어써짐
        for (String player : completion) {hm.put(player, hm.get(player) - 1);}
        //System.out.println("hm 2차="+hm); // hm 2차={ana=0, mislav=1, stanko=0}

        // 합해서 0이 되는 key만(participant input 횟수와 completion input 횟수가 동일) answer에 저장
        for (String key : hm.keySet()) {
            if (hm.get(key) != 0){
                answer = key;
                break;
            }
        }
        return answer;
    }

    public String exam2(String[] participant, String[] completion) {
        // 정렬
        Arrays.sort(participant);
        Arrays.sort(completion);

        int i;
        // participant[i]와 completion[i]가 일치하지 않으면 participant에 중복이 아닌 값이 있다는 것이므로
        // i=0부터 크기가 더 작은 배열인 completion.length까지 루프문 반복
        for ( i=0; i<completion.length; i++){
            if (!participant[i].equals(completion[i])){
                return participant[i];
            }
        }
        System.out.println(i); // 마지막 루프문에서 i++하고 끝났기 때문에 completion의 마지막 index + 1로 값이 저장되어있음

        // 루프문 다 돌아갈 때까지 답이 안나왔다는 것은 participant 마지막 요소에 중복이 아닌 값이 있다는 것이므로
        // 루프문 안에서 더해진 i==index인 participant 리턴 // i를 루프문 바깥에 선언했기 때문에 가능
        return participant[i];
    }

    /** Function.identify()

     람다식으로 자기 자신을 가리키는 것과 동일하지만 가독성이 더 좋음(단, 메모리 효율은 람다식이 더 좋음)

     static <T> Function<T, T> identity() {
    return t -> t;
    }
    */

    /** iterator().next()

     hasNext() 메서드로 이동이 가능한지 확인한 후에 next() 메서드로 해당 위치의 보관한 개체를 참조하여 원하는 작업을 수행

     Iterator it = arr.iterator();
     while (it.hasNext()){
        String st = (String)it.next();
     }
     */
    public String exam3(String[] participant, String[] completion) {

        Map<String, Long> participantMap = Arrays.asList(participant).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        //System.out.println(participantMap); // {marina=1, filipa=1, nikola=1, vinko=1, josipa=1}

        // Complettion에 중복인 요소들은 -1 해서 최종적으로 0이 아닌 요소만 답으로 리턴
        for(String name : completion) {

            // Collectors.counting()이 Long Object 타입으로 반환하므로 value도 Long으로 받은 것
            // 하지만 꼭 Long 말고 long으로 받아도 상관 X
            Long value = participantMap.get(name) - 1L;

            if(value == 0L) {
                participantMap.remove(name);
            } else {
                participantMap.put(name, value);
            }
        }

        return participantMap.keySet().iterator().next();
    }

    // exam2와 원리는 같으나, 변수 i를 굳이 바깥에 선언하지 않고
    // 루프문 안에서는 리턴되지 않아서 participant 마지막 요소를 리턴해야 할 때
    // participant[i]가 아닌 participant[participant.length - 1]로 리턴
    public String exam4(String[] participant, String[] completion) {
        Arrays.sort(participant);
        Arrays.sort(completion);
        for(int i=0; i<completion.length; i++){
            if(!participant[i].equals(completion[i])) {
                return participant[i];
            }
        }
        return participant[participant.length-1];
    }

    // exam2와 원리는 같으나 if문에서는 return문 대신 break문으로 종료, return문은 마지막 라인에 하나만 존재
    public String exam5(String[] participant, String[] completion) {
        int i;

        Arrays.sort(participant);
        Arrays.sort(completion);

        for(i=0; i<participant.length-1; i++){
            if(!participant[i].equals(completion[i])) break;
        }

        return participant[i];
    }

    public static void main(String[] args) {
        FindTheUncompleted findTheUncompleted = new FindTheUncompleted();
        String[] participant1 = {"leo", "kiki", "eden"};
        String[] completion1 = {"eden", "kiki"};
        String[] participant2 = {"marina", "josipa", "nikola", "vinko", "filipa"};
        String[] completion2 = {"josipa", "filipa", "marina", "nikola"};
        String[] participant3 = {"mislav", "stanko", "mislav", "ana"};
        String[] completion3 = {"stanko", "ana", "mislav"};
        //System.out.println(findTheUncompleted.exam3(participant1,completion1)); // expected: leo
        //System.out.println(findTheUncompleted.exam3(participant2,completion2)); // expected: vinko
        System.out.println(findTheUncompleted.exam3(participant3,completion3)); // expected: mislav
    }
}
