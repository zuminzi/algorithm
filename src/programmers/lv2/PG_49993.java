package programmers.lv2;

public class PG_49993 {
    public int solution(String skill, String[] skill_trees) {
        System.out.println("aadd".indexOf("c"));
        int answer = 0;
        for (int i = 0; i < skill_trees.length; i++) {
            int[] indexArr = new int[skill.length()];

            for (int k = 0; k < skill.length(); k++) {
                int index = skill_trees[i].indexOf(skill.charAt(k));
                // Point
                // (문제) "BDA": B 스킬을 배우기 전에 C 스킬을 먼저 배워야 합니다. 불가능한 스킬트리입니다.
                // 포함되지 않을 때는 -1 리턴하므로 -1이 앞에 있는 경우 최댓값(26) 이상으로 예외 처리
                // 선행스킬의 의미 == CBD에서 C를 연마하지 않고는 B 불가
                // 따라서 C가 포함되지 않고 B부터 나오는 경우도 예외 처리 필요
                if(index == -1) index = 27;
                indexArr[k] = index;
            }

            int j = 0;
            for (; j < indexArr.length - 1; j++) {
                if (indexArr[j] > indexArr[j + 1]) break;
            }
            if (j == indexArr.length - 1) answer++;
        }
        return answer;
    }

    public static void main(String[] args){
        PG_49993 pg_49993 = new PG_49993();
        // expected : 2
        System.out.println(pg_49993.solution("CBD",new String[]{"BACDE", "CBADF", "AECB", "BDA"}));
        // expected : 3
        // (문제) 위 순서에 없는 다른 스킬(힐링 등)은 순서에 상관없이 배울 수 있습니다.
        // 아무것도 해당되지 않은 것 처리하지 않았을 때 반례
        System.out.println(pg_49993.solution("CBD", new String[]{"BACDE", "CBADF", "AECB", "BDA", "AQWER"}));
    }
}
