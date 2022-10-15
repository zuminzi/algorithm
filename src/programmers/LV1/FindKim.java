package programmers.LV1;

import java.util.Arrays;
import java.util.List;

public class FindKim {

        public String codeOfMine(String[] seoul) {
            int index = 0;
            List<String> seoulList = Arrays.asList(seoul);
            index = seoulList.indexOf("Kim");
            return "김서방은 " + index + "에 있다";
        }

        public String exam1(String[] seoul) {
           String answer = "";
            int index = 0;
            for (String str : seoul) {
                if (!str.equals("Kim")) index++;
               else answer = "김서방은 " + index + "에 있다";
           }
            return answer;
      }

        public String exam2(String[] seoul) {
            int x = 0;
            for (int i = 0; i < seoul.length; i++) {
                if (seoul[i].equals("Kim")) { // "Kim".equals(name)
                    x = i;
                }
            }
            return "김서방은 " + x + "에 있다";
        }

        public String exam3(String[] seoul){
            int x = 0;
            for (String name : seoul) {
              if(name.equals("Kim"))
                 break;
              x++;
            }
           return "김서방은 "+ x + "에 있다";
     }

    public static void main(String[] args) {
        FindKim findKim = new FindKim();
        String[] str = {"Jane", "Kim"};
        System.out.println(findKim.codeOfMine(str)); // expected: "김서방은 1에 있다"
    }

}
