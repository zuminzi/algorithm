package programmers.LV1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerifyPeriod {
    // TODO: 시간복잡도 단축
    public int[] codeOfMine(String today, String[] terms, String[] privacies) {
        DateTimeFormatter date_format = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate td = LocalDate.parse(today, date_format);
        HashMap<String, Integer> terms_map = new HashMap<>();
        List<Integer> invalidation_list = new ArrayList<>();

        for(int i=0; i<terms.length; i++){
            terms_map.put(terms[i].split(" ")[0], Integer.parseInt(terms[i].split(" ")[1]));
        }

        for(int i=0; i< privacies.length; i++){
            // 유효기간 반영
            LocalDate temp = LocalDate.parse(privacies[i].split(" ")[0], date_format)
                    .minusDays(1);
            temp = temp
                    .plusMonths(terms_map.get(privacies[i].split(" ")[1]));

            // 유효성 검사
            if(td.isAfter(temp)){
                invalidation_list.add(i+1);
            }
        }

        //System.out.println(invalidation_list);

        int[] answer = new int[invalidation_list.size()];
        for(int i=0; i<answer.length; i++){
            answer[i] = invalidation_list.get(i);
        }
        return answer;
    }

    public static void main(String[] args){
        VerifyPeriod verifyPeriod = new VerifyPeriod();
        String[] terms1 = {"A 6", "B 12", "C 3"};
        String[] privacies1 = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};
        System.out.println(verifyPeriod.codeOfMine("2022.05.19",terms1, privacies1 ));
    }
}
