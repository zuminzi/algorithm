package programmers.LV1;

import java.util.*;
import java.util.stream.Collectors;

public class PickTwoAndAdd {

    public String codeOfMine(int[] numbers) {
        /* Todo
        - 속도, 메모리 줄이기(특히 stream 대체 필요)
         */

        int size = numbers.length;
        boolean[] visited = new boolean[size];
        int[] output = new int[size];
        List<Integer> list = new ArrayList<>();

        permutation(numbers, output, visited, 0, size, 2, list);

        // list 중복제거 & 정렬
        // 이 때 기존 list에 다시 대입해주지 않으면 그냥 그대로 날아감
        list = list.stream().distinct().sorted().collect(Collectors.toList());

        int[] answer = new int[list.size()];
        for(int i=0; i<answer.length; i++){
            answer[i] = list.get(i);
        }
    return Arrays.toString(answer);
    }

    void permutation(int[] arr,int[] output, boolean[] visited, int start, int n, int r, List<Integer> list) {
        if (start == r) {
            sumTwo(output, r, list);
            return;
        }

        for (int i = 0; i < n; i++) {
            if (visited[i] != true) {
                visited[i] = true;
                output[start] = arr[i];
                permutation(arr, output, visited, start + 1, n, r, list);
                visited[i] = false;
            }
        }
    }

    static void sumTwo(int[] arr, int r, List<Integer> list) {
        int sum = 0;
        for (int i = 0; i < r; i++) {
            sum += arr[i];
            if (i == 1) {
                list.add(sum);
            }
        }
    }

    /* Set으로 저장 후 Stream으로 정렬 */
    // 그러나 스트림 때문에 속도 느림
    public int[] exam1(int[] numbers) {
        Set<Integer> set = new HashSet<>();

        for(int i=0; i<numbers.length; i++) {
            for(int j=i+1; j<numbers.length; j++) {
                set.add(numbers[i] + numbers[j]);
            }
        }

        return set.stream().sorted().mapToInt(Integer::intValue).toArray();
    }

    /** TreeSet

     이진탐색트리(Binaray Search Tree)를 저장하는 Set 자료구조

     BST = 부모 노드를 기준으로 왼쪽 자식 노드는 부모 노드보다 작은 값, 오른쪽 자식 노드는 부모 노드보다 큰 값을 갖는 트리
     */
    public String exam2(int[] numbers) {
        // TreeSet으로 sum을 받으면 오름차순 정렬할 필요가 없음
        // add()해서 저장될 때 자동으로 오름차순 정렬이 되기 때문
        Set<Integer> sumNumber = new TreeSet();

        for(int i = 0; i < numbers.length-1; i++){
            for(int j = i+1; j < numbers.length; j++){
                sumNumber.add(numbers[i] + numbers[j]);
            }
        }

        //System.out.println(sumNumber);

        int[] answer = new int[sumNumber.size()];
        int index = 0;
        // Set 단점 // 인덱스로 접근 불가, iterator로 순차 접근 가능
        Iterator itor = sumNumber.iterator();
        while(itor.hasNext()){
            answer[index] = (int)itor.next();
            index++;
        }

        return Arrays.toString(answer);
    }

    /* Set 대신 List 사용 후 저장하기 전 중복검사 */
    public int[] exam3(int[] numbers) {
        int[] answer;
        List<Integer> list = new ArrayList<Integer>();

        for(int i = 0 ; i < numbers.length; i++){
            for(int j = i+1 ; j < numbers.length; j++){
                int tmp = numbers[i] + numbers[j];
                // 중복 요소인지 저장하기 전 검사
                if(!list.contains(tmp)){
                    list.add(tmp);
                }
            }
        }

        int size = 0;
        answer = new int[list.size()];
        for(int num : list){
            answer[size++] = num;
        }
        Arrays.sort(answer);
        return answer;
    }

    public static void main(String[] args){
        PickTwoAndAdd pickTwoAndAdd = new PickTwoAndAdd();
        int[] arr1 = {2,1,3,4,1};
        int[] arr2 = {5,0,2,7};
        System.out.println(pickTwoAndAdd.exam2(arr1)); // expected : [2,3,4,5,6,7]
        System.out.println(pickTwoAndAdd.exam2(arr2)); // expected : [2,5,7,9,12]
    }
}
