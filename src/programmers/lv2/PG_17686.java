package programmers.lv2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PG_17686 {
    // ~176.58ms, 131MB
    // 공백 regex -> "[\\s]" // 슬래쉬가 아닌 백슬래쉬(\) 주의
    public String[] codeOfMine(String[] files) {
        Arrays.sort(files, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String target = getHead(o1);
                String comparator = getHead(o2);
                if (target.compareTo(comparator) > 0) {
                    return 1;
                } else if (target.compareTo(comparator) < 0) {
                    return -1;
                } else {
                    String num1 = getNum(o1);
                    String num2 = getNum(o2);
                    if (Integer.parseInt(num1) > Integer.parseInt(num2)) {
                        return 1;
                    } else if (Integer.parseInt(num1) < Integer.parseInt(num2)) {
                        return -1;
                    // Problem Point
                    // 기존 순서 그대로 유지하려면 1이 아닌 0 리턴
                    } else {
                        return 0;
                    }
                }
            }

            private String getNum(String o) {
                String num = o.replaceAll("[^0-9]"," ")
                        .trim()
                        .split(" ")[0];
                if (num.length() > 5) num = num.substring(0,5);
                return num;
            }

            private String getHead(String o) {
//                return o.toUpperCase().split("[^A-Z\\-\\s.]")[0];
                return o.toUpperCase().split("[0-9]")[0];
            }

        });
        return files;
    }

    // ~28.11ms, 98.5MB
    // regex.Matcher, regex.Pattern 이용
    // find()와 matches() 차이
    // find -> 메서드를 수행할 때마다, 문자열 내에서 그 다음 순서의 해당 패턴을 찾는다 // 한번만 수행 시 첫번째 요소 리턴
    // matches -> 대상 문자열 전체가 해당 패턴과 일치하면 true를 리턴
    class Record {

        public String name;
        public String head;
        public int number;

        public Record(String name, String head, int number) {
            this.name = name; // 가공 전 (출력용)
            this.head = head;
            this.number = number;
        }
    }
    public String[] exam(String[] files) {
        List<Record> records = new ArrayList<>();


        for (String file : files) {
            final String number = getNumber(file);
            // number를 먼저 matcher로 찾은 다음
            records.add(
                    new Record(
                            file,
                            file.split(number)[0].toLowerCase(), // number를 기준으로 split하여 head 가져오기
                            Integer.parseInt(number)));
        }

        records.sort((a,b) -> {
            if (a.head.equals(b.head)) {
                return Integer.compare(a.number, b.number);
            }
            return a.head.compareTo(b.head);
        });

        return records.stream().map(e -> e.name).toArray(String[]::new); // e.name에만 해당하는 String 배열 생성하여 return
    }

    private String getNumber(String file) {

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(file);

        // 이 때, 숫자 형태 문자열을 모두 가져오고 싶다면 if (matcher.find()) -> while (matcher.find()) 로 사용
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public static void main(String[] args){
        PG_17686 pg_17686 = new PG_17686();
        System.out.println(Integer.parseInt("000321")); // 처음에 오는 0은 알아서 제거하고 변환

        // expected : ["img1.png", "IMG01.GIF", "img02.png", "img2.JPG", "img10.png", "img12.png"]
        System.out.println(pg_17686.exam(new String[] {"img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"}));
        // expected : ["A-10 Thunderbolt II", "B-50 Superfortress", "F-5 Freedom Fighter", "F-14 Tomcat"]
        System.out.println(pg_17686.exam(new String[]{"F-dfdf .dfd543222 Freedom Fighter","F-dfdf .dfd542 Freedom Fighter", "B-50 Supe rfortress","B-S50 Supe rfortress", "A- 1 0 Thunderbolt II", "F-14 Tomcat"}));
        // expected : ["O49qcGPHuRLR5FEfoO00321", "O00321"]
        // tail도 num으로 인식하는 경우 반례
        System.out.println(pg_17686.exam(new String[]{"O00321", "O49qcGPHuRLR5FEfoO00321"}));
        // expected : [" AB. C12","aBc12","AbC00000123aaac33"]
        System.out.println(pg_17686.exam(new String[]{"AbC00000123aaac33","aBc12"," AB. C12"}));
    }
}
