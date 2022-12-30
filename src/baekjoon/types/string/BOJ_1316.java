package baekjoon.types.string;

import java.util.*;

public class BOJ_1316 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int totalNum = sc.nextInt();
        String[] wordArray = new String[totalNum];
        int nonGroupWordCnt = 0;


        for (int i = 0; i < totalNum; i++) {
            wordArray[i] = sc.next();

            Set<String> set = new HashSet<>();
            List<String> deduplicationList = new ArrayList<>();
            String prev = "";

            for (int k = 0; k < wordArray[i].length(); k++) {
                String target = String.valueOf(wordArray[i].charAt(k));
                // problem Point 1 - Compare Str("") with Char('') [comparison type ERROR]
                if(prev.equals(target) == false){
                    deduplicationList.add(target);
                    prev = target;
                }
            }

            for(int k=0; k<deduplicationList.size(); k++){
                // problem Point 2 - Put i, (not k), in the Argument of get() method [args ERROR]
                if(!set.add(deduplicationList.get(k))){
                    nonGroupWordCnt++;
                    // problem Point 3 - Count nonGroupWordCnt several Times [non-break ERROR]
                    break;
                }
            }
        }
        System.out.println(totalNum - nonGroupWordCnt);
    }
}
