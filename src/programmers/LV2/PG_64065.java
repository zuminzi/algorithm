package programmers.LV2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PG_64065 {
    // Accuracy Test ~73.33ms, 107MB
    public List<Integer> solution(String s) {
        Map<Integer,Integer> map = new HashMap<>();
        String[] strings= s.split(",");
        for(int i=0; i<strings.length; i++){
            String target = strings[i];
            while(target.contains("{")||target.contains("}")) {
                if(!target.contains("{") && !target.contains("}")) break;
                target = target.replace("{", "");
                target = target.replace("}", "");
            }
            int num = Integer.parseInt(target);
            if(map.size() != 0 && map.containsKey(num)) {
                map.put(num,map.get(num)+1);
            } else{
                map.put(num,1);
            }
        }

        List<Integer> keySet = new ArrayList<>(map.keySet());
        keySet.sort((o1, o2) -> map.get(o2) - (map.get(o1)));

        return keySet;
    }

    public static void main(String[] args){
        PG_64065 pg_64065 = new PG_64065();
        System.out.println(pg_64065.solution("{{2},{2,1},{2,1,3},{2,1,3,4}}")); // expected : [2,1,3,4]
        System.out.println(pg_64065.solution("{{4,2,3},{3},{2,3,4,1},{2,3}}")); // expected : [3,2,4,1]
    }
}
