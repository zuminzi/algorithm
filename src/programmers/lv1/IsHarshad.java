package programmers.lv1;

public class IsHarshad {
    public boolean codeOfMine(int x) {
        int total = 0;
        int temp = x;

        while(temp > 0) {
            total += (temp % 10);
            temp /= 10;
        }
        return (x % total)==0;
    }

    public boolean exam(int num) {
        String[] temp = String.valueOf(num).split("");

        int sum = 0;
        for (String s : temp) {
            sum += Integer.parseInt(s); // Integer.parseInt() 써서 바로 Sum
        }

        return num % sum == 0;
    }

    public boolean exam2(int x) {
        int sum = String.valueOf(x).chars().map(ch -> ch - '0').sum();
        return x % sum == 0;
    }

    public static void  main(String[] args){
        IsHarshad isHarshad = new IsHarshad();
        System.out.println(isHarshad.exam2(10)); // expected: true
        System.out.println(isHarshad.exam2(12)); // expected: true
        System.out.println(isHarshad.exam2(11)); // expected: false
        System.out.println(isHarshad.exam2(13)); // expected: false
    }
}
