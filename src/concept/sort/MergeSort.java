package concept.sort;

import java.util.Arrays;

public class MergeSort {
    /* Arrays.copyOfRange()로 배열 복제 */
    /* result
        mid=3
        mid=1
        배열 길이 1
        mid=1
        배열 길이 1
        배열 길이 1

        low_arr=[5]
        high_arr=[2]
        mergedArr=[2, 5]

        low_arr=[1]
        high_arr=[2, 5]
        mergedArr=[1, 2, 5]

        mid=2
        mid=1
        배열 길이 1
        배열 길이 1

        low_arr=[6]
        high_arr=[3]
        mergedArr=[3, 6]

        mid=1
        배열 길이 1
        배열 길이 1

        low_arr=[7]
        high_arr=[4]
        mergedArr=[4, 7]

        low_arr=[3, 6]
        high_arr=[4, 7]
        mergedArr=[3, 4, 6, 7]

        low_arr=[1, 2, 5]
        high_arr=[3, 4, 6, 7]
        mergedArr=[1, 2, 3, 4, 5, 6, 7]
    */
    public static int[] sort1(int[] arr) {
        // 각 요소가 하나씩만 존재할 때까지 분할
        if (arr.length < 2) {
            System.out.println("배열 길이 1");
            return arr;
        }

        // 재귀로 분할
        int mid = arr.length / 2;
        System.out.println("mid="+mid);
        int[] low_arr = sort1(Arrays.copyOfRange(arr, 0, mid));
        int[] high_arr = sort1(Arrays.copyOfRange(arr, mid, arr.length));
        System.out.println("low_arr="+Arrays.toString(low_arr));
        System.out.println("high_arr="+Arrays.toString(high_arr));

        int[] mergedArr = new int[arr.length];
        int m = 0, l = 0, h = 0;
        while (l < low_arr.length && h < high_arr.length) {
            if (low_arr[l] < high_arr[h])
                mergedArr[m++] = low_arr[l++];
            else
                mergedArr[m++] = high_arr[h++];
        }
        while (l < low_arr.length) {
            mergedArr[m++] = low_arr[l++];
        }
        while (h < high_arr.length) {
            mergedArr[m++] = high_arr[h++];
        }
        System.out.println("mergedArr="+Arrays.toString(mergedArr));
        return mergedArr;
    }

    /* 배열 복제 X */
    /* result
    mid=3
    high,low=7,0
    mid=1
    high,low=3,0
    mid=2
    high,low=3,1
    mid=1
    high,low=2,0
    mid=5
    high,low=7,3
    mid=2
    high,low=5,0
    mid=1
    high,low=2,0
    mid=3
    high,low=5,2
    mid=1
    high,low=3,0
    mid=2
    high,low=3,1
    mid=1
    high,low=2,0
    mid=4
    high,low=5,3
    mid=2
    high,low=4,0
    mid=1
    high,low=2,0
    mid=3
    high,low=4,2
    mid=1
    high,low=3,0
    mid=2
    high,low=3,1
    mid=1
    high,low=2,0
    mid=6
    high,low=7,5
    mid=3
    high,low=6,0
    mid=1
    high,low=3,0
    mid=2
    high,low=3,1
    mid=1
    high,low=2,0
    mid=4
    high,low=6,3
    mid=2
    high,low=4,0
    mid=1
    high,low=2,0
    mid=3
    high,low=4,2
    mid=1
    high,low=3,0
    mid=2
    high,low=3,1
    mid=1
    high,low=2,0
    mid=5
    high,low=6,4
    mid=2
    high,low=5,0
    mid=1
    high,low=2,0
    mid=3
    high,low=5,2
    mid=1
    high,low=3,0
    mid=2
    high,low=3,1
    mid=1
    high,low=2,0
    mid=4
    high,low=5,3
    mid=2
    high,low=4,0
    mid=1
    high,low=2,0
    mid=3
    high,low=4,2
    mid=1
    high,low=3,0
    mid=2
    high,low=3,1
    mid=1
    high,low=2,0
     */
    public static void sort2(int[] arr) {
        sort(arr, 0, arr.length);
    }

    private static void sort(int[] arr, int low, int high) {
        if (high - low < 2) {
            return;
        }

        int mid = (low + high) / 2;
        //System.out.println("mid="+mid);
        //System.out.println("high,low="+high+","+low);
        sort(arr, 0, mid);
        sort(arr, mid, high);
        merge(arr, low, mid, high);
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low];
        int t = 0, l = low, h = mid;

        while (l < mid && h < high) {
            if (arr[l] < arr[h]) {
                temp[t++] = arr[l++];
            } else {
                temp[t++] = arr[h++];
            }
        }

        while (l < mid) {
            temp[t++] = arr[l++];
        }

        while (h < high) {
            temp[t++] = arr[h++];
        }

        for (int i = low; i < high; i++) {
            arr[i] = temp[i - low]; // i는 low부터 시작하므로 0부터 순차적으로 올라가려면 (i-low)
        }
    System.out.println(Arrays.toString(arr));
    }
    public static void  main(String[] args){
        MergeSort mergeSort = new MergeSort();
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        //System.out.println(mergeSort.sort1(array)); // expected: [1, 2, 3, 4, 5, 6, 7]
        mergeSort.sort2(array);
    }
}
