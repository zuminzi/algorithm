package programmers.LV1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TrialExam {
    /*
    ** 완전탐색 -> O(n)
    * 순차 검색 알고리즘(특정 검색 key를 포함하는지)
    * 문자열 매칭 알고리즘(길이가 n인 문자열이 길이가 m인 문자열 패턴을 포함하는지 검색) -> O(mn)
    * 선택 정렬 알고리(min값 먼저 찾고 -> indx 0부터 시작하여 현재 indx와 min 비교 -> min이 더 작으면 자리교체)
    * 순열(중복X 순서O)
     */
    public String codeOfMine(int[] answers) {
        List<Integer> student1 = new ArrayList<>(List.of(1,2,3,4,5));
        List<Integer> student2 = new ArrayList<>(List.of(2,1,2,3,2,4,2,5));
        List<Integer> student3 = new ArrayList<>(List.of(3,3,1,1,2,2,4,4,5,5));

        int student1_cnt = 0;
        int student2_cnt = 0;
        int student3_cnt = 0;

        // count the number of correct answers
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == student1.get(i%student1.size())) { // i % 5
                student1_cnt++;
            }
            if (answers[i] == student2.get(i%student2.size())) { // i % 8
                student2_cnt++;
            }
            if (answers[i] == student3.get(i%student3.size())) { // i % 10
                student3_cnt++;
            }
        }

        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(1,student1_cnt);
        map.put(2,student2_cnt);
        map.put(3,student3_cnt);

        // find max
        int max=0;
        for (Integer key : map.keySet()) {
            if(map.get(key) >= max) max = map.get(key);
        }

        // add max to list
        List<Integer> list = new ArrayList<>();
        for(Integer key : map.keySet()) {
            if(map.get(key).equals(max)) {
                list.add(key);
            }
        }

        // list to array
        int[] answer = new int[list.size()];
        for (int i=0; i<answer.length; i++) {
            answer[i] = list.get(i);
        }

        return Arrays.toString(answer);
    }

    public String exam1(int[] answer){
        int[] a = {1,2,3,4,5};
        int[] b = {2,1,2,3,2,4,2,5};
        int[] c = {3,3,1,1,2,2,4,4,5,5};
        int[] score = new int[3]; // answer count를 배열로 관리 // 시간 효율: 배열로관리 < int 변수 3개 // 매번 배열 돌려서 요소 리턴하므로

        for(int i=0; i<answer.length; i++){
            if(answer[i] == a[i % a.length]) {score[0]++;}
            if(answer[i] == b[i % b.length]) {score[1]++;}
            if(answer[i] == c[i % c.length]) {score[2]++;}
        }

        int maxScore = Math.max(score[0], Math.max(score[1],score[2])); // 3가지 수 비교

        // Math.max는 둘 다 max일 때를 반영 못하므로
        // 각 score가 max와 같은지 비교해서 리스트에 추가
        // 요소가 하나 이상일 때 문제에서 제시한 리스트 정렬 기준: 오름차순
        ArrayList<Integer> list = new ArrayList<>();
        if(maxScore == score[0]) {list.add(1);} // 학생 1 추가
        if(maxScore == score[1]) {list.add(2);} // 학생 2 추가
        if(maxScore == score[2]) {list.add(3);} // 학생 3 추가

        // list to array // 시간효율: stream < for문
        int[] answers = list.stream().mapToInt(i->i.intValue()).toArray();

        return Arrays.toString(answers);
    }

    public String refactor(int[] answers) {
        List<Integer> student1 = new ArrayList<>(List.of(1,2,3,4,5));
        List<Integer> student2 = new ArrayList<>(List.of(2,1,2,3,2,4,2,5));
        List<Integer> student3 = new ArrayList<>(List.of(3,3,1,1,2,2,4,4,5,5));

        int student1_cnt = 0;
        int student2_cnt = 0;
        int student3_cnt = 0;

        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == student1.get(i%student1.size())) {
                student1_cnt++;
            }
            if (answers[i] == student2.get(i%student2.size())) {
                student2_cnt++;
            }
            if (answers[i] == student3.get(i%student3.size())) {
                student3_cnt++;
            }
        }

        int max = Math.max(student1_cnt, Math.max(student2_cnt,student3_cnt));

        ArrayList<Integer> list = new ArrayList<>();
        if(max == student1_cnt) {list.add(1);}
        if(max == student2_cnt) {list.add(2);}
        if(max == student3_cnt) {list.add(3);}

        // list to array
        int[] answer = new int[list.size()];
        for (int i=0; i<answer.length; i++) {
            answer[i] = list.get(i);
        }

        return Arrays.toString(answer);
    }

    public static int[] exam2(int[] answers) {
        int[][] patterns = {
                {1, 2, 3, 4, 5},
                {2, 1, 2, 3, 2, 4, 2, 5},
                {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}
        };

        int[] hit = new int[3];
        for(int i = 0; i < hit.length; i++) {
            for(int j = 0; j < answers.length; j++) {
                // patterns[i].length -> 각 행의 길이
                // j % patterns[i].length '=. i%student1.size() .. i%student2.size() .. i%student3.size()
                if(patterns[i][ j % patterns[i].length ] == answers[j]) hit[i]++;
            }
        }

        int max = Math.max(hit[0], Math.max(hit[1], hit[2]));
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < hit.length; i++)
            if(max == hit[i]) list.add(i + 1);

        int[] answer = new int[list.size()];
        int cnt = 0;
        for(int num : list)
            answer[cnt++] = num;
        return answer;
    }

    public static void main(String[] args) {
        TrialExam trialExam = new TrialExam();
        int[] arr1 = {1,2,3,4,5};
        int[] arr2 = {1,3,2,4,2};
        int[] arr3 = {1,2,3,4,5,1,2,3,4,5};
        System.out.println(trialExam.exam1(arr1)); // expected: [1]
        System.out.println(trialExam.exam1(arr2)); // expected: [1,2,3]
        System.out.println(trialExam.exam1(arr3)); // expected: [1]
    }
}
