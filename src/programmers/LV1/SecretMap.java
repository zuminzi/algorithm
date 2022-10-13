package programmers.LV1;

import java.util.ArrayList;

public class SecretMap {

    public String[] codeOfMine(int n, int[] arr1, int[] arr2) {
        /** point
        * 비트연산
        * 이진수 로직(나누어지는 수가 1이하일 때는 몫 포함 + 그 외 나머지값들을 거꾸로 출력)
        ** personal feedback
        * 1차 구현시 while문 증감식 부재로 무한루프
         * 라이브러리 최대한 쓰지 않고 구현하려고 노력 -> 시간복잡도 비교해서 효율적인 코드로 개선 필요
         * 중복 로직 메서드로 구현(1기능 1메서드 분리)
         */
        String[] answer = new String[n];
        ArrayList<String> binary_arr1 = new ArrayList<>();
        ArrayList<String> binary_arr2 = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String binary = "";

            if (arr1[i] == 1) {
                binary = "10";
            } else {
                while (arr1[i] > 1) {
                    binary += String.valueOf(arr1[i] % 2);
                    arr1[i] /= 2;
                }
                binary = binary + arr1[i];
            }
            while(binary.length()!=n) {
                binary=binary.concat("0");
            }
            StringBuffer sb = new StringBuffer(binary);
            String reverse = sb.reverse().toString();

            binary_arr1.add(reverse);

            binary = ""; // 변수 초기화
            if (arr2[i] == 1) {
                binary = "10";
            } else {
                while (arr2[i] > 1) {
                    binary += String.valueOf(arr2[i] % 2);
                    arr2[i] /= 2;
                }
                binary = binary + arr2[i];
            }
            while(binary.length()!=n) {
                binary=binary.concat("0");
            }
            StringBuffer sb_ = new StringBuffer(binary);
            String reverse_ = sb_.reverse().toString();

            binary_arr2.add(reverse_);

            binary=""; // binary 변수 초기화
            for (int j = 0; j < n; j++) {
                String temp="";
                temp = binary_arr1.get(i).charAt(j) == '1' || binary_arr2.get(i).charAt(j) == '1' ? "#" : " ";
                binary += temp;
            }
            answer[i]=binary;
        }
        return answer;
    }

    /* Math.pow() */
    public String[] exam1(int n, int[] arr1, int[] arr2) {
        String[] answer = {};
        String[] result = new String[n];
        for(int i = 0; i < n; i++){
            int value = 0;
            int tmp1 = arr1[i];
            int tmp2 = arr2[i];
            boolean flag = false;
            boolean space = true;
            String tmpStr = "";
            for(int j = n - 1; j >= 0; j--){
                if(tmp1 >= (int)Math.pow(2,j)){
                    tmp1 -= (int)Math.pow(2,j);
                    tmpStr += "#";
                    flag = true;
                    space = false;
                }
                if(tmp2 >= (int)Math.pow(2,j)){
                    tmp2 -= (int)Math.pow(2,j);
                    if(!flag)
                        tmpStr += "#";
                    space = false;
                }
                if(space){
                    tmpStr += " ";
                }
                flag = false;
                space = true;
            }
            result[i] = tmpStr;
        }
        answer = result;
        return answer;
    }

    /* 재귀함수 */
    public String[] exam3(int n, int [] arr1, int [] arr2) {
        String [] answer = new String[n];
        int [] secretMap = new int[n];
        for(int i = 0; i < n; i++) {
            secretMap[i] = arr1[i] | arr2[i];
            answer[i] = makeSharp(secretMap[i], n);
        }
        return answer;
    }

    public String makeSharp(int n, int m) {
        if(n == 0) {
            if( m > 0) {
                String str = "";
                for(int i = 0; i < m; i++) {
                    str += " ";
                }
                return str;
            }
            else return "";
        }
        else {
            return n % 2 == 0 ? makeSharp(n/2, m-1) + " " : makeSharp(n/2, m-1) + "#";
        }
    }

    /* Integer.toBinaryString(), String.format(), replaceAll() */
    public String[] exam4(int n, int[] arr1, int[] arr2) {
        String[] result = new String[n];
        for (int i = 0; i < n; i++) {
            result[i] = Integer.toBinaryString(arr1[i] | arr2[i]);
        }

        for (int i = 0; i < n; i++) {
            result[i] = String.format("%" + n + "s", result[i]);
            result[i] = result[i].replaceAll("1", "#");
            result[i] = result[i].replaceAll("0", " ");
        }

        return result;
    }

    public String[] exam5(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        for (int i = 0; i < n; i++)
            answer[i] = decoding(binary(arr1[i], arr2[i], n));
        return answer;
    }

    public String binary(int n, int m, int cnt) {
        StringBuilder sb = new StringBuilder();
        while(cnt != 0) {
            int tmp = n % 2 + m % 2;
            sb.append(tmp);
            n /= 2;
            m /= 2;
            cnt--;
        }
        return sb.reverse().toString();
    }

    public String decoding(String code) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) > '0')
                sb.append("#");
            else
                sb.append(" ");
        }
        return sb.toString();
    }

    public String[] exam6(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];

        int[][] binary1 = new int[n][n];
        int[][] binary2 = new int[n][n];
        // Decimal -> Binary
        for(int i = 0; i < n; i++){
            int num = arr1[i];
            int b = 0;
            while ( num != 0 ){
                if(num < 1){
                    break;
                }
                binary1[i][b] = num % 2;
                num = num/2;
                b++;
            }
        }
        for(int i = 0; i < n; i++){
            int num = arr2[i];
            int b = 0;
            while ( num != 0 ){
                if(num < 1){
                    break;
                }
                binary2[i][b] = num % 2;
                num = num/2;
                b++;
            }
        }

        StringBuilder str = new StringBuilder();
        for(int x = 0; x < n; x++){
            for(int y = 0; y < n; y++){
                if((binary1[x][y] == 1 && binary2[x][y] == 1) || (binary1[x][y] == 0 && binary2[x][y] == 1) || (binary1[x][y] == 1 && binary2[x][y] == 0)){
                    str.append("#");
                }else{
                    str.append(" ");
                }
            }
            str.reverse();
            answer[x] = String.valueOf(str);
            str = new StringBuilder("");
        }
        return answer;
    }

    public static void main(String args[]) {
        SecretMap secretMap = new SecretMap();
        int [] arr1 = {9, 20, 28, 18, 11};
        int [] arr2 = {30, 1, 21, 17, 28};
        System.out.println(secretMap.codeOfMine(5,arr1,arr2)); // expected: ["#####","# # #", "### #", "# ##", "#####"]
    }
}
