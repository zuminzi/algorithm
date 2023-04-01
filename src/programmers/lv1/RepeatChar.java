package programmers.lv1;

public class RepeatChar {
    public String codeOfMine(int n) {
        char[] array = new char[n];
        String answer="";
        for(int i=0; i<n; i++){
            //if(i==0) {array[0] ='수';} // 제거 -> 분자가 0인 경우는 ArithmeticException 발생 X
            if(i%2==1) {array[i] = '박';}
            if(i%2==0){array[i] = '수';}
        }
        for(char tmp:array){
            answer += tmp;
        }
        return answer;
    }

    public String exam1(int n){
        return new String(new char [n/2+1]) // 배열 idx는 0부터 시작, 길이는 1부터 시작이므로 +1, "수박" 두 글자씩 채울 예정이므로 n/2
                .replace("\0", "수박") // 빈 char배열 생성 시 '\0'으로 생성
                .substring(0,n); // 0부터 n 미만까지
    }

    public String exam2(int n){
        String result = "";
        for (int i = 0; i < n; i++)
            result += i % 2 == 0 ? "수" : "박";
        return result;
    }

    /* StringBuffer */
    public String exam3(int n){
        StringBuffer sf = new StringBuffer();
        for (int i=1; i<=n; ++i) {
            sf.append(i%2==1?"수":"박");
        }
        return sf.toString();
    }

    /* while(n-- > 0) */
    public String exam4(int n){
        boolean flag = true;
        String result = "";
        while(n-- > 0){

            result += flag ? "수":"박";
            flag = !flag;
        }
        return result;
    }

    public static void main(String[] args){
        RepeatChar repeatChar = new RepeatChar();
        System.out.println(repeatChar.codeOfMine(3)); // expected : 수박수
        System.out.println(repeatChar.codeOfMine(4)); // expected : 수박수박
    }
}
