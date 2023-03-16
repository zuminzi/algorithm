package programmers.LV2;

import java.util.*;

public class PG_42888 {
    private static final String ENTER_FORMAT = "%s님이 들어왔습니다.";
    private static final String LEAVE_FORMAT = "%s님이 나갔습니다.";

    // ~161.14ms, 187MB
    public String[] codeOfMine(String[] record) {
        Map<String,String> nickNameLog = new HashMap<>();
        List<String> result = new ArrayList<>();

        for(int i= 0; i < record.length; i++) {
            String target = record[i];
            if (target.split(" ").length == 3) {
                nickNameLog.put(target.split(" ")[1], target.split(" ")[2]);
            }
        }

        for(int i=0; i<record.length; i++){
            String target = record[i];
            String uid = target.split(" ")[1];
            if (target.startsWith("E")) {
                result.add(nickNameLog.get(uid) +ENTER_FORMAT);
            } else if(target.startsWith("L")){
                result.add(nickNameLog.get(uid) +LEAVE_FORMAT);
            }
        }
        return result.toArray(String[]::new);
    }

    // ~541.12ms, 344MB
    // Stream
    public String[] codeOfMine_refactor(String[] record) {
        Map<String,String> nickNameLog = new HashMap<>();

        for(int i= 0; i < record.length; i++) {
            String target = record[i];
            if (target.split(" ").length == 3) {
                nickNameLog.put(target.split(" ")[1], target.split(" ")[2]);
            }
        }
        return Arrays.stream(record)
                .filter(el -> el.startsWith("E")||el.startsWith("L"))
                .map(el -> String.format( el.startsWith("E")? ENTER_FORMAT : LEAVE_FORMAT , nickNameLog.get(el.split(" ")[1])))
                .toArray(String[]::new);
    }

    public static void main(String[] args){
        PG_42888 pg_42888 = new PG_42888();
        System.out.println(pg_42888.codeOfMine(new String[]
                {"Enter uid1234 Muzi", "Enter uid4567 Prodo","Leave uid1234","Enter uid1234 Prodo","Change uid4567 Ryan"}));
    }
}
