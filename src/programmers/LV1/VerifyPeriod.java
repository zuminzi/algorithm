package programmers.LV1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerifyPeriod {
    /** 공백 기준으로 문자열 자르기

     - split(" ") 대신 split(\\s) regex도 활용 가능
     */
    public int[] codeOfMine(String today, String[] terms, String[] privacies) {
        DateTimeFormatter date_format = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate td = LocalDate.parse(today, date_format);
        HashMap<String, Integer> terms_map = new HashMap<>();
        List<Integer> invalidation_list = new ArrayList<>();

        for(int i=0; i<terms.length; i++){
            String[] term = terms[i].split("\\s");// 가독성 높이기 위하여 로컬변수 term 생성
            terms_map.put(term[0], Integer.parseInt(term[1]));
        }

        for(int i=0; i< privacies.length; i++){
            String[] privacy = privacies[i].split("\\s"); // 가독성 높이기 위하여 로컬 변수 privacy 생성
            // 유효기간 반영
            LocalDate temp = LocalDate.parse(privacy[0], date_format)
                    .minusDays(1);
            temp = temp
                    .plusMonths(terms_map.get(privacy[1]));

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
