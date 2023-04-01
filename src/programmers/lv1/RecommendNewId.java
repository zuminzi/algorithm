package programmers.lv1;

public class RecommendNewId {
    public String codeOfMine(String new_id) {

        // step 1
        new_id = new_id.toLowerCase();

        // step 2
        new_id = new_id.replaceAll("[^a-z0-9\\_\\-\\.]+",""); // 배제식은 제일 처음에만 ^

        // step 3
        new_id = new_id.replaceAll("\\.{2,}",".");

        // step 4
        new_id = new_id.replaceAll("^\\.", "");
        new_id = new_id.replaceAll("\\.$", "");

        // step 5
        new_id = new_id.isBlank() ? "a" : new_id;

        // step 6
        new_id = new_id.length() >= 16 ? new_id.substring(0,15) : new_id;
        new_id = new_id.replaceAll("\\.$", "");

        // step 7
        StringBuilder stringBuilder = new StringBuilder(new_id); // String += 연산 대신 StringBuilder로 속도개선

        while(stringBuilder.toString().length() <= 2) {
            stringBuilder.append(new_id.charAt(new_id.length() - 1));
        }

        return stringBuilder.toString();
    }

    public static void main(String args[]) {
        RecommendNewId recommendNewId = new RecommendNewId();
        System.out.println(recommendNewId.codeOfMine("=.=")); // expected : aaa
        System.out.println(recommendNewId.codeOfMine("z-+.^.")); // expected : z--
        System.out.println(recommendNewId.codeOfMine("...!@BaT#*..y.abcdefghijklm")); // expected : bat.y.abcdefghi
    }
}