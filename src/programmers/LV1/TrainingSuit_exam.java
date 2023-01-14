package programmers.LV1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

public class TrainingSuit_exam {
    public int exam1(int n, int[] lost, int[] reserve) {
        int[] people = new int[n]; // 각 인덱스의 값들은 0으로 초기화됨
        int answer = n;

        for (int l : lost)
            people[l-1]--; // 잃어버린 사람 = (-1) // 인덱스는 0부터 시작하므로 l-1
        for (int r : reserve)
            people[r-1]++; // 여분 있는 사람 = (+1)

        for (int i = 0; i < people.length; i++) {
            if(people[i] == -1) {
                // i가 0이 아니고, i-1이 여분이 있는 사람(reserve)이면
                if(i-1>=0 && people[i-1] == 1) {
                    people[i]++; // 잃어버린 사람 0 처리
                    people[i-1]--; // 여분 빌려준 사람 0 처리

                // i가 마지막 요소가 아니고, i+1이 여분이 있는 사람(reserve)이면
                }else if(i+1< people.length && people[i+1] == 1) {
                    people[i]++; // 잃어버린 사람 0 처리
                    people[i+1]--; // 여분 빌려준 사람 0 처리

                // 위 조건문들에 다 해당되지 않는다면
                }else
                    answer--; // 체육 할 수 있는 인원 줄이기
            }
        }
        return answer;
    }

    /* 배열 크기를 n+2로 설정하여 i가 첫번째 인덱스인지, 마지막인덱스인지 검사하는 조건문 제외 가능 */
    public int exam1_refactor(int n, int[] lost, int[] reserve) {
        int[] people = new int[n+2];
        int answer = n;

        System.out.println("before="+Arrays.toString(people)); // [0, 0, 0, 0, 0, 0, 0]

        for (int l : lost)
            people[l-1]--;
        for (int r : reserve)
            people[r-1]++;

        System.out.println("after="+Arrays.toString(people)); // [0, -1, 1, -1, 1, 0, 0]

        for (int i = 0; i < people.length; i++) {
            if(people[i] == -1) {
                if(people[i-1] == 1) {
                    people[i]++;
                    people[i-1]--;

                }else if(i+1< people.length && people[i+1] == 1) {
                    people[i]++;
                    people[i+1]--;

                }else
                    answer--;
            }
        }
        return answer;
    }

    /* HashSet? TreeSet?

    * HashSet = 중복되지 않는 데이터를 순서에 상관없이 저장
    * TreeSet = 데이터를 저장할 시 이진탐색트리(BinarySearchTree)의 형태로 데이터를 저장하기에 기본적으로 nature ordering를 지원
    -> 따라서 (HashSet에 비해) TreeSet의 이진 탐색 트리는 추가와 삭제에는 시간이 조금 더 걸리지만 정렬, 검색에 높은 성능을 보이는 자료구조
    -> 생성자의 매개변수로 Comparator객체를 입력하여 정렬 방법을 임의로 지정해 줄 수도 있음

    -> Set(HashSet, TreeSet)은 메서드를 이용하여 데이터 간의 차집합, 교집합, 합집합 쉽게 구할 수 있음

    * 데이터 추가
    - add(E e) : 기존 데이터에 e가 없을 경우 축
    - addAll(collections<?> c) : 기존 데이터에 없는 collection c의 모든 데이터 추가 // 합집합

    * 데이터 삭제
    - remove(Object o) : Object o 삭제
    - removeAll(Collections<?> c) : collection c에 해당하는 데이터 삭제 // 차집합
    - retainAll(Collections<?> c) : collection c에 해당하는 데이터만 남기고 삭제 // 교집합

    * 데이터 확인
    - contains() : 포함여부 확인하는 boolean 리턴타입 메서드
    - isEmpty() : HashSet에 데이터가 하나도 없는지 확인하는 boolean 리턴타입 메서드

    * 데이터 반환
    - Object[] toArray() : HashSet의 모든 데이터를 array로 반환
    - <T> T[] toArray(T[] a) : HashSet의 모든 데이터를 array a에 담아 반

    * 클래스 객체를 HashSet에 추가, 삭제
    // Student class 멤버 -> String name, int age

    HashSet<Student> h1 = new HashSet<Student>();

    h1.add(new Student("A", 10));
    h1.add(new Student("B", 20));

    h1. remove(new Student("B", 20));

    System.out.println(h1) // [[A,10]]
     */
    // 기존 풀이 코드에서는 Arrays.sort()로 정렬이 안되어있고, TreeSet 대신 HashSet을 사용했음
    // 그렇게 하니 정렬이 안된 테케에서는 통과를 못하여 lost와 reserve 정렬 + TreeSet으로 natural ordering 유지
    public int exam2(int n, int[] lost, int[] reserve) {
        int answer=n-lost.length;

        Arrays.sort(lost);
        Arrays.sort(reserve);

        TreeSet<Integer> ko = new TreeSet<Integer>();
        for(int k : reserve) {
            ko.add(k);
        }
        for(int i =0;i<lost.length;i++) {
            if(ko.contains(lost[i])) {
                //System.out.println("내껀내가입지");
                answer++;
                ko.remove(lost[i]);
                lost[i]=-1;
            }
        }


        for(int i =0;i<lost.length;i++) {
            //System.out.println(i);

            if(ko.contains(lost[i]-1)) {
                //System.out.println("있다");
                answer++;
                ko.remove(lost[i]-1);
            }else if(ko.contains(lost[i]+1)) {
                //System.out.println("있다");
                answer++;
                ko.remove(lost[i]+1);
            }
            //System.out.println("없다");
        }


        return answer;
    }

    public static void main(String[] args){
        TrainingSuit_exam trainingSuit_ex = new TrainingSuit_exam();
        int[] lost = {4,2};
        int[] reserve = {3,5};
        System.out.println(trainingSuit_ex.exam2(5 ,lost, reserve)); // expected : 5 // 정렬 안한 코드 반례
    }
}