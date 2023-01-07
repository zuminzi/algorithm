package programmers.LV1;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonalityTypeTest {
    public String codeOfMine(String[] survey, int[] choices) {
        String answer="";
        LinkedHashMap<String,Integer> score_list = new LinkedHashMap (){{
            put("R", 0); put("T", 0);
            put("C", 0); put("F", 0);
            put("J", 0); put("M", 0);
            put("A", 0); put("N", 0);
        }};
        int[][] choices_array = {
                {1,3},
                {2,2},
                {3,1},
                {4,0},
                {5,1},
                {6,2},
                {7,3}
        };

        // test
        int old_value;
        for(int i=0; i<survey.length; i++) {
            if(choices[i] < 4) {
                old_value = score_list.get(survey[i].substring(0, 1));
                score_list.replace(survey[i].substring(0, 1), old_value + choices_array[choices[i] - 1][1]);
            } else {
                old_value = score_list.get(survey[i].substring(1));
                score_list.replace(survey[i].substring(1), old_value + choices_array[choices[i] - 1][1]);
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
            }
            // value가 같을 경우 compare
            else{
                if(entry_first.getKey().compareTo(entry_second.getKey()) > 0)
                    sb.append(entry_second.getKey());
                else {
                    sb.append(entry_first.getKey());
                }
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
