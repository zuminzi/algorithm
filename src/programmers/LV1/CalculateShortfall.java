package programmers.LV1;

public class CalculateShortfall {
    public long codeOfMine(int price, int money, int count) {
        long totalPrice = 0;
        for(int i=1; i<=count; i++){
            totalPrice += (price * i);
        }
        return money - totalPrice >= 0? 0 : Math.abs(totalPrice - money);
    }

    /* 등차수열의 합 */
    public long codeOfMine_refactor(int price, int money, int count) {
        int firstTerm = price;
        long lastTerm = count * price;

        long totalPrice = count * (firstTerm + lastTerm) / 2; // 여기서 바로 계산하면 (int * int)로 int 범위를 초과
        return Math.max(totalPrice - money, 0);
    }

    /*
    point 1. 1~n +1씩 증가하는 자연수의 합(시그마 공식) = n(n+1)/2
    point 2. 수열에 상가 곱해진 값의 합은 수열의 합에 상수를 곱한 것과 같음
    */
    public long exam(long price, long money, long count) {
        return Math.max(price * (count * (count + 1) / 2) - money, 0);
    }

    public static void main(String[] args){
        CalculateShortfall calculateShortfall = new CalculateShortfall();
        System.out.println(calculateShortfall.codeOfMine_refactor(2500, 1000000000, 2500)); // expected : 6,815,625,000

    }
}
