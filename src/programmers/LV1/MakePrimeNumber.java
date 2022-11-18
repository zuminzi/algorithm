package programmers.LV1;

import java.util.Arrays;

public class MakePrimeNumber {
    // source: https://insight-bgh.tistory.com/336
    int answer = 0;
    /* 백트래킹 사용 */
    public int solution1(int[] nums) {
        boolean[] visited = new boolean[nums.length];

        combination1(nums,visited,0, nums.length, 3); // (int[] arr, boolean[] visited, int start, int n, int r)

        return answer;
    }

    /* 재귀함수 이용 */
    public int solution2(int[] nums) {
        boolean[] visited = new boolean[nums.length];

        combination2(nums,visited,0, nums.length, 3); // (int[] arr, boolean[] visited, int depth, int n, int r)

        return answer;
    }

    /* n개 중에 r개 선택하는 조합 알고리즘(1) */
    void combination1(int[] arr, boolean[] visited, int start, int n, int r) {
        if (r == 0) {
            sumCombination(arr, visited, n);
            return;
        }

        for (int i = start; i < n; i++) {
            visited[i] = true;
            combination1(arr, visited, i + 1 , n, r - 1);
            visited[i] = false;
        }
    }

    /* n개 중에 r개 선택하는 조합 알고리즘(2) */
    void combination2(int[] arr, boolean[] visited, int depth, int n, int r) {
        if (r == 0) {
            sumCombination(arr, visited, n);
            return;
        }

        if (depth == n) {
            return;
        }

        visited[depth] = true;
         combination2(arr, visited, depth + 1, n, r - 1);

        visited[depth] = false;
         combination2(arr, visited, depth + 1, n, r);
    }

    /* 조합 숫자들 합산하여 소수인지 판별 */
     int sumCombination(int[] arr, boolean[] visited, int n) { // origin: print combination -> modify code suitable for problem
        int total=0;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                total+=arr[i];
                cnt++;
                if(cnt==3 && isPrime(total)) {
                    answer++;
                }
            }
        }
    return  answer;
    }

    /* 소수 판별 */
    private boolean isPrime(int n) {
        if(n == 1 ) { return true; }

        if( n%2 == 0) { return false; }
        for(int i=3; i<=Math.sqrt(n); i += 2) {
            if( n % i == 0) {return  false;}
        }
        return true;
    }

    public static void main(String[] args){
        MakePrimeNumber makePrimeNumber = new MakePrimeNumber();
        int[] arr1 = {1,2,3,4};
        int[] arr2 = {1,2,7,6,4};
        //System.out.println(makePrimeNumber.solution(arr1)); // expected : 1
        System.out.println(makePrimeNumber.solution2(arr2)); // expected : 4
    }
}

