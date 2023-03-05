package programmers.LV2;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

//  'A', 'E', 'I', 'O', 'U'만을 사용
// 1이상 5이하
// output : 몇번째 단어인지
public class PG_84512 {
    static char[] vowel;
    static List<String> list;
    // Accuracy Test ~43.63ms, 70.7MB
    // 중복 순열
    public int solution(String word) {
        list = new ArrayList<>();
        vowel = new char[]{'A', 'E', 'I', 'O', 'U'};

        for(int i=0; i<vowel.length; i++){
                permutation(5, i, new char[5]);
        }

        list.sort(Comparator.naturalOrder());

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
            // 중복조합과의 차이점: 조합은 index를 따로 둔 후, 재귀호출된 comb의 for문에서 index부터 시작
            // 단, 일반 조합과 달리 중복 선택 가능하므로 인덱스 i를 그대로 넘겨준다.
            for (int i = 0; i < n; i++) {
                output[r] = vowel[i];
                permutation(n, r-1, output);
            }
    }

    public static void main(String[] args){
        PG_84512 pg_84512 = new PG_84512();
        System.out.println(pg_84512.solution("AAAAE")); // expected : 6
        System.out.println(pg_84512.solution("AAAE")); // expected : 10
        System.out.println(pg_84512.solution("I")); // expected : 1563
        System.out.println(pg_84512.solution("EIO")); // expected : 1189
    }
}
