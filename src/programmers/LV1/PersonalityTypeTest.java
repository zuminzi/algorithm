package programmers.LV1;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonalityTypeTest {
    /* 해시에 의해 랜덤으로 저장되는 HashMap이 아닌 입력순서대로 저장되는 LinkedHashMap 활용 */
    public String codeOfMine(String[] survey, int[] choices) {
        String answer="";
        LinkedHashMap<String,Integer> score_list = new LinkedHashMap (){{
            put("R", 0); put("T", 0);
            put("C", 0); put("F", 0);
            put("J", 0); put("M", 0);
            put("A", 0); put("N", 0);
        }};
        int[] choices_array = {0, 3, 2, 1, 0, 1, 2, 3}; // indx = option num

        // test
        int old_value;
        for(int i=0; i<survey.length; i++) {
            if(choices[i] < 4) {
                old_value = score_list.get(survey[i].substring(0, 1));
                score_list.replace(survey[i].substring(0, 1), old_value + choices_array[choices[i]]);
            } else {
                old_value = score_list.get(survey[i].substring(1));
                score_list.replace(survey[i].substring(1), old_value + choices_array[choices[i]]);
            }
            }

        // return test result
        StringBuffer sb = new StringBuffer(answer);
        Iterator<Map .Entry<String, Integer>> entries = score_list.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Integer> entry_first = entries.next();
            Map.Entry<String, Integer> entry_second = entries.next();
            // value가 같지 않을 경우 compare
            if(entry_first.getValue() != entry_second.getValue()) {
                if (entry_first.getValue() > entry_second.getValue()) {
                    sb.append(entry_first.getKey());
                } else {
                    sb.append(entry_second.getKey());
                }
                // value가 같을 경우
                // RT, CF, JM, AN은 이미 LinkedHashMap에 의해 들어간 순서로 저장됨
                // R vs T, C vs F 등 비교대상끼리는 이미 사전순으로 정렬되어 있기 때문에
                // entry_first.getKey()와 entry_second.getKey()를 compareTo()를 통해 따로 비교할 필요 X
            } else{
                    sb.append(entry_first.getKey());
            }
        }
        return sb.toString();
}

    public static void main(String[] args) {
        PersonalityTypeTest personalityTypeTest = new PersonalityTypeTest();
        String[] survey1 = {"AN", "CF", "MJ", "RT", "NA"};
        int[] choices1 = {5, 3, 2, 7, 5};
        System.out.println(personalityTypeTest.codeOfMine(survey1, choices1)); // expected: "TCMA"
    }
}
