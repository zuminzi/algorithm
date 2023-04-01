package programmers.lv3;

import java.util.*;

public class PG_43162 {
    // primitive type 중에 boolean 배열은 stream 지원 X
    // bool 배열에 stream을 사용하고 싶다면 Boolean[]으로 생성
    // ex. if((Arrays.stream(visited).filter(el -> el == true).count()==visited.length)) return; // 그러나 이는 효율상 적용안하는 게 나음
    // https://stackoverflow.com/questions/42225001/java-8-boolean-primitive-array-to-stream

    // DFS
    // ~0.23ms, 70.6MB
    public int codeOfMine(int n, int[][] computers) {
        int networkCnt = 0;
        boolean[] visited = new boolean[n];
        if(n != 1) {
            for (int i = 0; i < computers.length; i++) {
                if (!visited[i]) {
                    makeGraph(i, visited, computers);
                    networkCnt++;
                }
            }
        } else {
            networkCnt = 1;
        }
        return networkCnt;
    }

    private void makeGraph(int root, boolean[] visited, int[][] graph) {
        // Problem Point
        // 재귀 호출 후 처음 실행하는 실행문
        visited[root] = true;
        for (int i=0; i < visited.length; i++) {
            if (graph[root][i] == 1 && !visited[i]) {
                graph[root][i] = -1;
                graph[i][root] = -1;
                makeGraph(i, visited, graph);
            }
        }
    }

    public static void main (String[] args){
    PG_43162 pg_43162 = new PG_43162();
    System.out.println(pg_43162.codeOfMine(3,new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}})); // expected : 2
    System.out.println(pg_43162.codeOfMine(3,new int[][]{{1, 1, 0}, {1, 1, 1}, {0, 1, 1}})); // expected : 1
    System.out.println(pg_43162.codeOfMine(3, new int[][]{{1,1,1},{1,1,1},{1,1,1}})); // expected : 1
    System.out.println(pg_43162.codeOfMine(7, new int[][]{{1,0,0,0,0,0,1}, {0,1,1,0,1,0,0}, {0,1,1,1,0,0,0},
                                            {0,0,1,1,0,0,0}, {0,1,0,0,1,1,0}, {0,0,0,0,1,1,1}, {1,0,0,0,0,1,1}})); // expected : 1
    System.out.println(pg_43162.codeOfMine(6, new int[][]{{1, 0, 1, 1, 0, 0}, {0, 1, 0, 0, 1, 1},{1, 0, 1, 1, 1, 1},
                                            {1, 0, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}})); // expected: 1
    System.out.println(pg_43162.codeOfMine(5,new int[][]{{1,1,1,0,0}, {1,1,0,0,0}, {1,0,1,0,0},{0,0,0,1,1},{0,0,0,1,1}})); // expected : 2
    System.out.println(pg_43162.codeOfMine(4, new int[][]{{1, 1, 0, 1}, {1, 1, 0, 0}, {0, 0, 1, 1}, {1, 0, 1, 1}})); // expected : 1
    System.out.println(pg_43162.codeOfMine(3,new int[][]{{1,1,0},{0,1,0},{0,0,1}})); // expected : 2 // 단방향 그래프 ** //예시와 달리 단방향도 주어짐
    }
}