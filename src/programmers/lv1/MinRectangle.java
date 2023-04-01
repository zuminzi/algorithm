package programmers.lv1;

public class MinRectangle {
    public int codeOfMine(int[][] sizes) {
        int answer = 0;
        // 각 행별 오름차순 정렬
        for(int i=0; i<sizes.length; i++){
                if(sizes[i][0] > sizes[i][1]) {
                    int tmp = sizes[i][0];
                    sizes[i][0] = sizes[i][1];
                    sizes[i][1] = tmp;
                }
            }

        // 총 행의 개수
        //System.out.println(sizes.length); // 4
        // 각 행의 열의 개수
        //System.out.println(sizes[0].length); // 2


        // 각 열의 최댓값
        int maxWidth = 0;
        int maxLength = 0;
        for (int i = 0; i < sizes.length; i++) {
            if(sizes[i][0] > maxWidth ) maxWidth = sizes[i][0];
            if(sizes[i][1] > maxLength) maxLength = sizes[i][1];
        }

        answer = maxWidth * maxLength;

        return answer;
    }

    /* Math.max */
    public int exam1(int[][] sizes) {
        int length = 0, height = 0;
        for (int[] card : sizes) {
            length = Math.max(length, Math.max(card[0], card[1]));
            height = Math.max(height, Math.min(card[0], card[1]));
        }
        int answer = length * height;
        return answer;
    }

    public static void main(String[] args) {
        int[][] sizes = {{60, 50}, {30, 70}, {60, 30}, {80, 40}};
        MinRectangle minRectangle = new MinRectangle();
        System.out.println(minRectangle.codeOfMine(sizes));
    }
}
