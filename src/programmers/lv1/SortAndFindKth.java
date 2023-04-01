package programmers.lv1;

import java.util.Arrays;

public class SortAndFindKth {
    /** Todo
     * quick, merge 외 다른 정렬 알고리즘도 리마인드 필요
     * 배열로 정의된 조건은 단일변수로 재정의
     */
    /* merge concept.sort -> Avg, Worst : O(nlogn) */
    public String codeOfMine(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        for(int k=0; k<commands.length; k++) {
            int length = commands[k][1] - commands[k][0] + 1;
            int[] temp = new int[length];
            int idx = 0;

            // make sub-List
            for (int i = commands[k][0]; i <= commands[k][1]; i++) {
                temp[idx++] = array[i-1]; // ~번째 -> index로 변환 시 -1 필요
            }

            // concept.sort
            mergeSort(temp, 0, temp.length);
            //Arrays.concept.sort(temp); // 대체 코드

            answer[k] = temp[commands[k][2]-1];
        }

        return Arrays.toString(answer);
    }
    private static void mergeSort(int[] arr, int low, int high) {
        if (high - low < 2) {
            return;
        }

        int mid = (low + high) / 2;
        mergeSort(arr, 0, mid);
        mergeSort(arr, mid, high);
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
            arr[i] = temp[i - low];
        }
    }

    /* Arrays.CopyOfRange() */
    public int[] exam1(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        for(int i=0; i<commands.length; i++){
            int[] temp = Arrays.copyOfRange(array, commands[i][0]-1, commands[i][1]);
            Arrays.sort(temp);
            answer[i] = temp[commands[i][2]-1];
        }

        return answer;
    }

    /* quick concept.sort -> Avg:O(nlogn), Worst:O(n^2) */
    public String exam2(int[] array, int[][] commands) {
        int n = 0;
        int[] ret = new int[commands.length];

        while(n < commands.length){
            int m = commands[n][1] - commands[n][0] + 1;

            if(m == 1){
                ret[n] = array[commands[n++][0]-1];
                continue;
            }

            int[] a = new int[m];
            int j = 0;
            for(int i = commands[n][0]-1; i < commands[n][1]; i++)
                a[j++] = array[i];

            quickSort(a, 0, m-1); // left= start idx, right= length - 1

            ret[n] = a[commands[n++][2]-1];
        }

        return Arrays.toString(ret);
    }

    void quickSort(int[] a, int left, int right){
        int pl = left; // pivot left
        int pr = right; // pivot right
        int x = a[(pl+pr)/2]; // pivot

        do{
            while(a[pl] < x) pl++;
            while(a[pr] > x) pr--;
            if(pl <= pr){
                int temp = a[pl];
                a[pl] = a[pr];
                a[pr] = temp;
                pl++;
                pr--;
            }
        }while(pl <= pr);

        if(left < pr) quickSort(a, left, pr);
        if(right > pl) quickSort(a, pl, right);
    }

    /* 가독성, 시간효율 높이기 위해 조건배열을 int 변수로 재정의 */
    public int[] exam3(int[] array, int[][] commands) {
        int[] answer = {};

        answer = new int[ commands.length ];

        for( int i=0; i<commands.length; i++ )
        {
            int startPoint = commands[i][0];
            int endPoint = commands[i][1];
            int answerPoint = commands[i][2];

            int[] partOfArray = new int[ endPoint - startPoint +1 ];

            int idx = 0;
            for( int j=startPoint-1; j<endPoint; j++ )
            {
                partOfArray[idx] = array[j];
                idx++;
            }

            Arrays.sort( partOfArray );

            answer[i] = partOfArray[ answerPoint-1 ];
        }

        return answer;
    }

    /* String.split()으로 부분배열 생성, selection concept.sort -> O(n^2) */
    public int[] exam4(int[] array, int[][] commands) {
        String str ="";
        int[] answer = new int[commands.length];
        for(int i=0; i<commands.length; i++){
            str = "";
            for(int j=commands[i][0]-1; j<commands[i][1]; j++){
                str += array[j]+ " ";

            }
            System.out.println(str);
            String[] arr = str.split(" ");
            for(int k=0; k<arr.length; k++){
                for(int l=k+1; l<arr.length; l++){
                    if(Integer.parseInt(arr[k]) > Integer.parseInt(arr[l])){
                        String tmp = arr[k];
                        arr[k] = arr[l];
                        arr[l] = tmp;
                    }
                }
            }
            answer[i] = Integer.parseInt(arr[commands[i][2]-1]);
        }
        return answer;
    }

    public static void  main(String[] args){
        SortAndFindKth sortAndFindKth = new SortAndFindKth();
        int[] array = {1, 5, 2, 6, 3, 7, 4};
        int[][] commands = {{2,5,3}, {4,4,1}, {1,7,3}};
        System.out.println(sortAndFindKth.exam2(array, commands)); // expected: [5, 6, 3]
    }
}
