package programmers.lv2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PG_84512 {
    static char[] vowel;
    static List<String> list;
    // Accuracy Test ~43.63ms, 70.7MB
    // 중복 순열
    public int codeOfMine(String word) {
        list = new ArrayList<>();
        vowel = new char[]{'A', 'E', 'I', 'O', 'U'};

        for(int i=0; i<vowel.length; i++){
                permutation(vowel.length, i, new char[vowel.length]);
        }
        list.sort(Comparator.naturalOrder());
        // = list.sort(((o1, o2) -> o1.compareTo(o2)));


        return list.indexOf(word)+1; // index는 0부터 시작이므로
    }

    private void permutation(int n, int r, char[] output) {
            if (r+1 == 0) {
                String sum = "";
                for(int k=0; k< output.length; k++){
                    sum += output[k];
                }
                list.add(sum.trim());
                return;
            }
            // 중복조합과의 차이점: 조합은 index를 따로 둔 후, 재귀호출된 permutation()의 for문에서 i=index부터 시작
            // 단, 일반 조합과 달리 중복 선택 가능하므로 인덱스 i를 그대로 넘겨준다.
            for (int i = 0; i < n; i++) {
                output[r] = vowel[i];
                permutation(n, r-1, output);
            }
    }

    // ~0.20ms, 81.4MB
    public int exam1(String word) {
        int answer = 0;
        int per = 3905; // A~UUUUU 모든 낱말의 개수

        for(String s : word.split("")) {
            int start = "AEIOU".indexOf(s); // 각 시작점 구하기
            // EX) EIO -> E: 3905의 다섯등분 중 두번째 파이 범위에 해당하는 782 -> EI: 782의 다섯등분 중 세번째 파이 범위에 해당하는 1095
            answer +=  start * (per /= 5) + 1;
        }

        return answer;
    }

    // ~31.25ms, 82.1MB
    public int exam2(String word) {
        list = new ArrayList<>();
        dfs("", 0);
        return list.indexOf(word);
    }
    void dfs(String str, int index) {
        if(index > 5) return;
        list.add(str);
        for(int i = 0; i < 5; i++)
            // A -> E -> I -> O -> U 순서대로 각 자릿수 채울 때까지 분기
            // EX) A, AA, AAA, AAAA, AAAAA, 다시 AAAA로 돌아가서 AAAAE, AAAAI, ...
            dfs(str + "AEIOU".charAt(i), index + 1);
    }

    // ~0.10ms, 73.7MB // BEST
    public int exam3(String word) {
            int answer = word.length(); // 문자열 길이에 따라 A, AA, AAA, AAAA, AAAAA 해당 길이의 A까지는 미리 디폴트로 추가
            int pos;
            for(pos = 0; pos < word.length(); pos ++){
                char c = word.charAt(pos);
                if(c=='A') continue; // A는 미리 처리해놨기 때문에 패스

                int temp = 0;
                for(int i = 0; i <= 4-pos; i++){
                    // 중복순열 경우의 수는 n의 r제곱
                    // n^0 = 1
                    // EX. |A..|E..|I..|O..|U..|에서 첫번째 문자(pos == 0) 위치는 해당 파이(5^0+5^1+5^2+5^3+5^4)에 val(c)(=AEIOU에서 몇번째 인덱스인지)를 곱한 값
                    temp += Math.pow(5,i);
                }
                answer += temp*val(c);
            }
            return answer;
        }

        public int val(char c){
            if(c=='E') return 1;
            else if(c=='I') return 2;
            else if(c=='O') return 3;
            else return 4;// U
        }

    public static void main(String[] args){
        PG_84512 pg_84512 = new PG_84512();
        System.out.println(pg_84512.codeOfMine("AAAAE")); // expected : 6
        System.out.println(pg_84512.codeOfMine("AAAE")); // expected : 10
        System.out.println(pg_84512.codeOfMine("E")); // expected : 782
        System.out.println(pg_84512.codeOfMine("EI")); // expected : 1095
        System.out.println(pg_84512.codeOfMine("EIO")); // expected : 1189
        System.out.println(pg_84512.codeOfMine("A")); // expected : 1

    }
}
