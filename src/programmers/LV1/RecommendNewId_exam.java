package programmers.LV1;

public class RecommendNewId_exam {
    public String solution(String new_id) {


        String answer =
                new KAKAOID(new_id) // 레퍼런스 변수 없이 바로 KAKAOID 객체 생성
                .replaceToLowerCase()
                .filter()
                .toSingleDot()
                .noStartEndDot()
                .noBlank()
                .noGreaterThan16()
                .noLessThan2()
                .getResult();

        return answer;
    }

    private static class KAKAOID {
        // 굳이 외부에 노출될 필요 없으면 의식적으로 private으로 접근 지정
        private String s;

        KAKAOID(String s) { // Constructor
            this.s = s;
        }

        private KAKAOID replaceToLowerCase() {
            s = s.toLowerCase();
            return this; // 메서드 체이닝(다음 메서드 호출) 위한 return문
        }

        private KAKAOID filter() {
            s = s.replaceAll("[^a-z0-9._-]", "");
            return this;
        }

        private KAKAOID toSingleDot() {
            s = s.replaceAll("[.]{2,}", ".");
            return this;
        }

        private KAKAOID noStartEndDot() {
            s = s.replaceAll("^[.]|[.]$", ""); // 정규식에서 or연산은 |
            return this;
        }

        private KAKAOID noBlank() {
            s = s.isEmpty() ? "a" : s;
            return this;
        }

        private KAKAOID noGreaterThan16() {
            if (s.length() >= 16) {
                s = s.substring(0, 15);
            }
            s = s.replaceAll("[.]$", "");
            return this;
        }

        private KAKAOID noLessThan2() {
            StringBuilder sBuilder = new StringBuilder(s);
            while (sBuilder.length() <= 2) {
                sBuilder.append(sBuilder.charAt(sBuilder.length() - 1));
            }
            s = sBuilder.toString();
            System.out.println(this);
            return this;
        }

        private String getResult() {
            return s;
        }
    }

    public static void main(String args[]) {
        RecommendNewId_exam recommendNewIdExam = new RecommendNewId_exam();
        System.out.println(recommendNewIdExam.solution("=.=")); // expected : aaa
        System.out.println(recommendNewIdExam.solution("z-+.^.")); // expected : z--
        System.out.println(recommendNewIdExam.solution("...!@BaT#*..y.abcdefghijklm")); // expected : bat.y.abcdefghi
    }
}
