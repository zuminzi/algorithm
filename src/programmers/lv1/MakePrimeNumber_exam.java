package programmers.lv1;

public class MakePrimeNumber_exam {
    /** boolean vs Boolean
    boolean은 기본 자료형이고, Boolean은 참조형 객체이므로 Boolean은 null 가능

     Null check가 필요 없는 경우 기본 자료형 사용 권장. unboxing의 시간이 소요되기 때문.
     그러나 Null 값 허용이 필요하다던지, DB에 널값이 있을 수 있다던지, Collection에서 사용해야하는 경우는 Boolean을 사용하는 것이 좋음.
    (List와 Map으로 잘 알려진 컬렉션에서는 객체만을 받아들일 수 있으므로 기본 자료형이 아닌 박스화된 기본 자료형만 사용가능하기 때문)
     */
    public int exam1(int[] nums) {
        int ans = 0;

        for(int i = 0; i < nums.length - 2; i ++){
            for(int j = i + 1; j < nums.length - 1; j ++){ // i보다 커야 뽑을 후보가 됨
                for(int k = j + 1; k < nums.length; k ++ ){ // j보다 커야 뽑을 후보가 됨
                    if(isPrime(nums[i] + nums[j] + nums[k])){
                        ans += 1;
                    }
                }
            }
        }
        return ans;
    }
    public boolean isPrime(int num){
        int cnt = 0;
        for(int i = 1; i <= (int)Math.sqrt(num); i ++){ // limit에는 Math.sqrt()까지 포함
            if(num % i == 0) cnt += 1;
        }
        return cnt == 1; // 약수가 1밖에 없는가 // isPrime()이 true인가
    }

    public static void main(String[] args){
        MakePrimeNumber_exam makePrimeNumber_exam = new MakePrimeNumber_exam();
        System.out.println(makePrimeNumber_exam.isPrime(16));
    }
}
