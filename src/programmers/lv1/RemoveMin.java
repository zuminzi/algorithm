package programmers.lv1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveMin {
    public String codeOfMine(int[] arr) {
        /* todo
        * Stream 사용하면 가독성은 좋은데 for문*3보다 효율성 떨어짐-> 개선필요
         */
        if(arr.length<=1) {
            arr[0]= -1;
        } else {
            int min = Arrays.stream(arr).min().getAsInt();
            arr = Arrays.stream(arr).filter(i -> i != min).toArray();
        }

        return Arrays.toString(arr);
    }

    public String exam(int[] arr) {
        if(arr.length == 1){
            int[] answer = {-1};
            return Arrays.toString(answer);
        }

        int[] answer = new int[arr.length-1];
        int minIndex=0;

        for(int i=0;i<arr.length;i++){
            if(arr[minIndex]>arr[i]){
                minIndex = i; // Find minIndex
            }
        }
        for(int i=minIndex+1;i<arr.length;i++){
            arr[i-1] = arr[i]; // Remove minIndex // minIndex 뒤의 요소들 앞으로 이동
             }
        for(int i=0;i<answer.length;i++){
            answer[i] = arr[i];
        }
        return Arrays.toString(answer);
    }

    /* int[]->ArrayList<Integer>, Collections.min() */
    public int[] exam2(int[] arr) {
        if (arr.length == 1) {
            arr[0] = -1;
            return arr;
        } else {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (int a : arr) {
                arrayList.add(a);
            }
            Integer minimum = Collections.min(arrayList);
            arrayList.remove(minimum);
            int[] resultArray = new int[arr.length - 1];
            for (int i = 0; i < arrayList.size(); ++i) {
                resultArray[i] = arrayList.get(i);
            }
            return resultArray;
        }
    }

    /* Arrays.concept.sort() 후에 int[] arr로 새로 생성한 List<Integer> list에서 arr[0] 제거 */
    public int[] exam3(int[] arr) {
        List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
        Arrays.sort(arr);
        list.remove(list.indexOf(arr[0]));
        if (list.size() <= 0) return new int[]{-1};
        return list.stream().mapToInt(i->i).toArray();
    }

    /* min과 index를 arr 첫번째값과 0으로 설정해두고 min이면 해당 인덱스(i)와 값(arr[i]) 저장 */
    public int[] exam4(int[] arr) {
        if(arr.length == 1) {
            int[] answer = {-1};
            return answer;
        }

        int[] answer = new int[arr.length-1];
        int min = arr[0];
        int index = 0;

        for(int i=1; i<arr.length; i++) {
            index = arr[i] < min ? i : index;
            min = arr[i] < min ? arr[i] : min;
        }

        int cnt = 0;
        for(int i=0; i<arr.length; i++) {
            if(i != index) { // for(i=0; i<index; i++)와 같은 맥락
                answer[cnt] = arr[i];
                cnt++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        RemoveMin removeMin = new RemoveMin();
        int[] arr1 = {10};
        int[] arr2 = {4, 3, 2, 1};
        System.out.println(removeMin.exam3(arr1)); // expected: [-1]
        System.out.println(removeMin.exam3(arr2)); // expected: [4, 3, 2]

    }
}
