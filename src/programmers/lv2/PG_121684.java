package programmers.lv2;

import java.util.*;

public class PG_121684 {
    private static int eventNum, studentNum, ans;
    private static List<Student>[] list;

    /**
     *
     * Key Point 1 : List 타입 배열 생성 문법 (List<>[] arr = new ArrayList[length];)
     * Key Point 2 : List 인터페이스의 sort()는 non-static이므로 static 변수 사용 불가
     * Key Point 3 : Comparable.compareTo(Object o)는 클래스 자체의 기본 정렬 방법 지정,
     *               Comparator.compare(Object o1, Object o2)는 기본 정렬과 다른 정렬 방법 지정
     */
    public int solution(int[][] ability) {
        ans = 0;
        eventNum = ability[0].length;
        studentNum = ability.length;
        int[] nums = new int[eventNum];
        list = new ArrayList[eventNum];

        for(int i=0; i<eventNum; i++){
            list[i] = new ArrayList<>();
            nums[i] = i;
        }

        for(int i=0; i<eventNum; i++){
            for(int j=0; j<studentNum; j++){
                list[i].add(new Student(ability[j][i], j));
            }
            Collections.sort(list[i]);
        }

        getMax(nums, new boolean[eventNum], new int[eventNum], 0);

        return ans;
    }

    private void getMax(int[] nums, boolean[] visited, int[] output, int depth){
        if(depth == eventNum){
            electRepresentative(output);
        }

        for(int i=0; i<eventNum; i++){
            if(!visited[i]){
                visited[i] = true;
                output[i] = nums[depth];
                getMax(nums, visited, output, depth+1);
                visited[i] = false;
                output[i] = 0;
            }
        }
    }

    private void electRepresentative(int[] output){
        int sum = 0;
        boolean[] visited = new boolean[studentNum];
        for(int i=0; i<output.length; i++){
            for(Student student : list[output[i]]){
                int score = student.score;
                int no = student.no;
                if(!visited[no]){
                    visited[no] = true;
                    sum += score;
                    break;
                }
            }
        }
        ans = Math.max(ans, sum);
    }

    class Student implements Comparable<Student>{
        int score;
        int no;
        Student(int score, int no){
            this.score = score;
            this.no = no;
        }

        @Override
        public int compareTo(Student o){
            return Integer.compare(o.score, this.score);
        }
    }

    public static void main(String[] args){
        PG_121684 pg_121684 = new PG_121684();
        // expected : 210
        System.out.println(pg_121684.solution(new int[][]{{40, 10, 10}, {20, 5, 0}, {30, 30, 30}, {70, 0, 70}, {100, 100, 100}}));
    }
}
