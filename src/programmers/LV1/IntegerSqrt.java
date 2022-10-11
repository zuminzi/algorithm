package programmers.LV1;

public class IntegerSqrt {

    public long codeOfMine(long n) {
        if((Math.sqrt(n)-(int)Math.sqrt(n))==0) {
            long result = (long) Math.pow(Math.sqrt(n)+1,2);
            return result;
        }
        return -1;
    }

    public long exam(long n) {
        if (Math.pow((int)Math.sqrt(n), 2) == n) {
            return (long) Math.pow(Math.sqrt(n) + 1, 2);
        }
        return -1;
    }

    /* 삼항 연산자 사용 */
    public long exam_(long n) {
        double i = Math.sqrt(n);
        return Math.floor(i) == i ? (long) Math.pow(i + 1, 2) : -1;
    }

    public static void main(String[] args) {
        IntegerSqrt integerSqrt = new IntegerSqrt();
        System.out.println(integerSqrt.codeOfMine(121)); // expected: 144
        System.out.println(integerSqrt.codeOfMine(3)); // expected: -1
    }
}
