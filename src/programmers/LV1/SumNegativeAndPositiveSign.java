package programmers.LV1;

public class SumNegativeAndPositiveSign {
    public int codeOfMine(int[] absolutes, boolean[] signs) {
        int sum = 0;
        for(int i = 0; i < absolutes.length; i++) {
            absolutes[i] = signs[i] == true ? absolutes[i] : Integer.parseInt("-" + absolutes[i]);
            sum+=absolutes[i];
        }

        return sum;
    }

    public int exam1(int[] absolutes, boolean[] signs) {
        int answer = 0;
        for (int i=0; i<signs.length; i++)
            answer += absolutes[i] * (signs[i]? 1: -1);
        return answer;
    }

    public int exam2(int[] absolutes, boolean[] signs) {
        int answer = 0;
        for (int i = 0; i < absolutes.length; i++) {
            answer += (signs[i]) ? absolutes[i] : -absolutes[i];
        }
        return answer;
    }

    public static void main(String[] args) {
        SumNegativeAndPositiveSign sumNegativeAndPositiveSign = new SumNegativeAndPositiveSign();
        int[] absolutes = {4,7,12};
        boolean[] signs = {true,false,true};
        System.out.println(sumNegativeAndPositiveSign.exam2(absolutes, signs)); // expected: 9
    }
}
