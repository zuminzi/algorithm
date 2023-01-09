package programmers.LV1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

public class RankLotto {
    public int[] codeOfMine(int[] lottos, int[] win_nums) {
        int zero_cnt = 0;
        int win_cnt = 0;

        Arrays.sort(lottos);
        Arrays.sort(win_nums);

        for(int i=0; i<lottos.length; i++){
            zero_cnt = lottos[i] == 0? zero_cnt + 1 : zero_cnt;

            for(int j=0; j<win_nums.length; j++) {
                win_cnt = lottos[i] == win_nums[j]? win_cnt + 1 : win_cnt;
            }
        }

        return new int[] { rank((zero_cnt + win_cnt)), rank(win_cnt) };
    }

    private int rank(int value){
        switch (value){
            case 6:
                return 1;
            case 5:
                return 2;
            case 4:
                return 3;
            case 3:
                return 4;
            case 2:
                return 5;
            default:
                return 6;
        }
    }

    /* Map의 containsKey() 활용 */
    public int[] exam1(int[] lottos, int[] win_nums) {
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        int zeroCount = 0;

        for(int lotto : lottos) {
            if(lotto == 0) {
                zeroCount++;
                continue;
            }
            map.put(lotto, true);
        }


        int sameCount = 0;
        for(int winNum : win_nums) {
            if(map.containsKey(winNum)) sameCount++;
        }

        // 순위와 당첨내용 간의 관계 이용하여 구현
        int maxRank = 7 - (sameCount + zeroCount);
        int minRank = 7 - sameCount;
        if(maxRank > 6) maxRank = 6;
        if(minRank > 6) minRank = 6;

        return new int[] {maxRank, minRank};
    }

    /* stream -> 시간복잡도는 비효율적 */
    public int[] exam2(int[] lottos, int[] winNums) {
        return LongStream.of(
                        (lottos.length + 1) - Arrays.stream(lottos).filter(l -> Arrays.stream(winNums).anyMatch(w -> w == l) || l == 0).count(),
                        (lottos.length + 1) - Arrays.stream(lottos).filter(l -> Arrays.stream(winNums).anyMatch(w -> w == l)).count()
                )
                .mapToInt(op -> (int) (op > 6 ? op - 1 : op))
                .toArray();
    }

/**  이진탐색

 - 데이터가 정렬돼 있는 배열에서 특정한 값을 찾아내는 알고리즘
 - Arrays.binarySearch( Arrays, Object ) : 배열에서 특정 요소 검색
    - value가 있는 경우 지정된 array에 있는 지정된 value의 인덱스이고, 그렇지 않으면 음수
    - array가 다차원 배열인 경우에 오류 발생(RankException)
    - value의 형식이 array와 호환되지 않는 요소일 때 오류 발생(ArgumentException)
 */
    public int[] exam3(int[] lottos, int[] win_nums) {
        int[] rank = {6, 6, 5, 4, 3, 2, 1};
        int answer = 0;
        int hidden = 0;

        Arrays.sort(win_nums);
        for (int i = 0; i < lottos.length; i++)
            if (Arrays.binarySearch(win_nums, lottos[i]) > -1)
                answer++;
            else if (lottos[i] == 0)
                hidden++;

        return new int[] {rank[answer + hidden], rank[answer]};
    }

    public static void main(String args[]) {
        RankLotto rankLotto = new RankLotto();
        int[] lottos_1 = {44, 1, 0, 0, 31, 25};
        int[] win_nums_1 = {31, 10, 45, 1, 6, 19};
        System.out.println(rankLotto.codeOfMine(lottos_1, win_nums_1)); // expected : [3, 5]
    }
}