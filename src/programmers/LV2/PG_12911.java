package programmers.LV2;

public class PG_12911 {
    /*
    * Integer.toBinaryString() : 정수 -> 이진수 표현의 문자열
    * Integer.parseInt(String str, int radix), Integer.toString(int n, int radix)
    지정된 기수(radix)에 있는 문자열의 정수 표현을 반환
    ex. String str = 0010;
        Integer parseInt(str,2); // 2
     */
    public int solution(int n) {
        int answer = 0;
        int cnt = 0;
        String binaryString = Integer.toBinaryString(n);

        for(int i=binaryString.length() - 1; i >= 0; i--){
            if(binaryString.charAt(i) == '1'){
                cnt++;
            }
        }

        for(int i=n+1; i<=1000000;i++){
            int cntOne = 0;
            String comparator = Integer.toBinaryString(i);
            for(int k=0; k<comparator.length(); k++){
                if(comparator.charAt(k) == '1'){
                    cntOne++;
                }
            }
            if(cnt == cntOne){
                answer = i;
                break;
            }
        }
        return answer;
    }

    // Integer.bitCount() : 이진수에서 1의 개수를 세어주는 메서드
    public int exam1(int n) {
        int a = Integer.bitCount(n);
        int compare = n+1;
        while(true) {
            if(Integer.bitCount(compare)==a)
                break;
            compare++;
        }
        return compare;
    }

    // 비트연산자 이용
    // 최하단 1비트값을 더하고, 1 비트 개수들은 가장 오른쪽(가장 작은 값)으로 밀어주기
    public int exam2(int n){
        // input n = 0000 1111 0000(2)인 경우

        int rightOne = n & -n; // 최하단 1비트 // 결과: 0000 0001 0000 // 2^4 -> 16

        /*
         * [왼쪽 비트 패턴 구하기]
         * rightOne 과 n을 더하여 n의 오른쪽 1 뭉치들을 조작하는 과정
         * 왼쪽 비트 값이 여기서 설정됨 (1 뭉치 기준 왼쪽 1들과 1 뭉치 내 자리수 올라가는 고립된 1 숫자끼리 합쳐줌)
         * 0000 1111 0000 + 000 0001 0000
         */
        int nextHigherOneBit = n + rightOne; // 결과: 0001 0000 0000

        /*
         * [오른쪽 비트 패턴 구하기]
         * 오른쪽 1 bit 뭉치 분리
         * XOR 연산: 비트가 서로 다르면 1, 같으면 0
         * 0000 1111 0000 (XOR) 0001 0000 0000
         */
        int rightOnesPattern = n ^ nextHigherOneBit; // 결과: 0001 1111 0000

        /*
         * 나머지 1 bit 뭉치들 왼쪽으로 옮기기 (bit shift 계산 순서 상관 없음)
         * [1] 1 bit 뭉치 바로 앞까지 있는 0 싹 다 날려줌
         * 0001 1111 0000 / 0000 0001 0000 ( 나누기 2^4 는 << 4 와 동일함)
         */
        rightOnesPattern = rightOnesPattern / rightOne; // 결과: 0000 0001 1111

        /*
         * [2] 1 개수 총합이 같아야 하므로 2만큼 shift 해줌
         * 올림에 사용된 1이 중복으로 2개 들어가있음
         * 0000 0001 1111 >> 2
         */
        rightOnesPattern = rightOnesPattern >> 2; //  결과: 0000 0000 0111

        // 0001 0000 0000 (OR) 0000 0000 0111 (왼쪽 비트 패턴과 오른쪽 비트 패턴끼리 엮어줌)
        int next = nextHigherOneBit | rightOnesPattern; // 0001 0000 0111
        return next;
    }
    public static void main(String[] args){
        PG_12911 pg_12911 = new PG_12911();
        System.out.println(pg_12911.exam2(240));
        System.out.println(pg_12911.exam2(78));
        System.out.println(pg_12911.exam2(15));
    }
}
