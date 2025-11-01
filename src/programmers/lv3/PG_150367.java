package programmers.lv3;

public class PG_150367 {
    /**
     * 이진 트리 = 자식 노드 개수(=차수, degree)를 최대 2개로 제한 (루트만 있어도 이진트리 충족)
     * 포화이진트리(Full BT) = 이진트리이면서 서브트리까지 모두 빈 곳 없이 꽉찬 트리
     * 완전 이진 트리(Complete BT) = 높이가 h 일 때 레벨 h-1까지는 Full BT이고, 레벨 h에서는 왼쪽부터 노드가 순서대로 채워진 이진트리
     **/
    public int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            String binary = Long.toBinaryString(numbers[i]);
            // 1. 완전이진트리를 포화이진트리로 만들기 위해 맞추는 과정
            // 1-1. 높이 조정 // (2^h) - 1가 현재 노드의 개수보다 크거나 같아지도록 높이 조정 (노드 개수는 패딩으로 채우면 되므로)
            int h = 0;
            int currNodeCount = binary.length();
            while ((1 << h) - 1 < currNodeCount) h++;
            // 1-2. [높이]에 따른 [노드 개수] 조정
            currNodeCount = 1 << h;
            // 1-3. [노드 개수]에 따른 [노드 값] 조정
            // %Ns는 문자열 전체의 길이를 N으로 맞춤
            String padded = String.format("%" + currNodeCount + "s", binary);
            padded = padded.replace(' ', '0');

            answer[i] = isValid(padded) ? 1 : 0;
        }
        return answer;
    }

    private boolean isValid(String tree) {
        if (tree.length() == 0) return true; // 빈 문자열이면 유효한 서브트리로 간주
        // 2. 중위 순회이므로 mid = root
        char root = tree.charAt(tree.length() / 2);
        String left = tree.substring(0, tree.length() / 2);
        String right = tree.substring(tree.length() / 2 + 1);

        if (root == '0') {
            // 3. [핵심 아이디어] 루트가 0이면 자식도 모두 0이어야 함 (루트 1일 때 자식 0이면 모순)
            if (left.contains("1") || right.contains("1")) {
                return false;
            }
        }
        // 4. 재귀
        return isValid(left) && isValid(right);
    }

    public static void main(String[] args) {
        PG_150367 pg150367 = new PG_150367();
        // expected: [1, 1, 0]
        System.out.println(pg150367.solution(new long[]{7, 42, 5}));
    }
}
