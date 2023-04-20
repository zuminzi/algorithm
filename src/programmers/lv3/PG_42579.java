package programmers.lv3;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * input: 노래 별 장르(genres), 노래 별 재생횟수(plays)
 * output: 장르 별로 가장 많이 재생된 노래를 두 개 고유번호
 * 가장 많이 재생된 장르 > 많이 재생된 노래 > 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래
 - Comparable 클래스 implements 하여 정렬 기준 재정의
 - 문제에서 요구하는 정렬 기준이 오름차순인지, 내림차순인지 제대로 인지하기
 */
public class PG_42579 {
    // ~2.74ms, 71.9MB
    private class Song implements Comparable<Song> {
        int no; // 곡 번호
        int playNo; // 재생 횟수

        Song(int no, int playNo) {
            this.no = no;
            this.playNo = playNo;
        }

        public int compareTo(Song other) {
            if (this.playNo > other.playNo) { // 오름차순
                return -1;
            } else if (this.playNo < other.playNo) {
                return 1;
            } else {
                if (this.no > other.no) { // 내림차순
                    return 1;
                } else if (this.no < other.no) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    protected List<Integer> codeOfMine(String[] genres, int[] plays) {
        // Problem Point 1
        // 장르마다 곡이 2개 이하일 수도 있음을 고려하여 answer 요소를 동적으로 할당
        List<Integer> answer = new ArrayList<>();
        Map<String, Integer> genreMap = new HashMap<>(); // < 장르, 재생횟수 합 >
        Map<String, PriorityQueue<Song>> songMap = new HashMap<>(); // < 장르, (곡번호, 재생횟수) >

        // 1. 각 Map에 추가 작업
        for (int i = 0; i < genres.length; i++) {
            Song song = new Song(i, plays[i]);
            if (genreMap.containsKey(genres[i])) {
                songMap.get(genres[i]).add(song);
                genreMap.put(genres[i], genreMap.get(genres[i]) + plays[i]);
            } else {
                PriorityQueue<Song> pq = new PriorityQueue<>();
                pq.add(song);
                songMap.put(genres[i], pq);
                genreMap.put(genres[i], plays[i]);
            }
        }

        // 2. 재생횟수 합을 기준으로 장르(genreMap) 내림차순 정렬
        List<Map.Entry<String, Integer>> desc = new ArrayList<>(genreMap.entrySet());
        desc.sort(new Comparator<Map.Entry<String, Integer>>() { // 내림차순
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        // 3. 문제의 정렬 기준(장르 > 재생횟수 > 곡번호) 고려하여 answer 배열에 추가
        for (Map.Entry<String, Integer> entry : desc) {
            String genre = entry.getKey();
            for (int k = 0; k <= 1; k++) {
                // Problem Point 2
                // 자료구조 삭제 시 필드가 없는 경우 예외처리 **
                if (!songMap.get(genre).isEmpty()) {
                    answer.add(songMap.get(genre).poll().no);
                }
            }
        }
        return answer;
    }

    // ~1.26ms, 81.1MB
    // 중첩 HashMap
    // 곡 고유번호를 중첩 Map의 Key로 구현
    public List<Integer> exam1(String[] genres, int[] plays) {
        HashMap<String, HashMap<Integer, Integer>> music = new HashMap<>(); // <장르, (곡 고유번호, 재생횟수)>
        HashMap<String, Integer> genre = new HashMap<>(); // <장르, 총 장르 재생수>
        ArrayList<Integer> resultAL = new ArrayList<>();

        for (int i = 0; i < genres.length; i++) {
            String key = genres[i];
            HashMap<Integer, Integer> infoMap;

            if (music.containsKey(key)) {
                infoMap = music.get(key);
            } else {
                infoMap = new HashMap<>();
            }

            infoMap.put(i, plays[i]);
            music.put(key, infoMap);

            //재생수
            if (genre.containsKey(key)) {
                genre.put(key, genre.get(key) + plays[i]);
            } else {
                genre.put(key, plays[i]);
            }
        }

        int mCnt = 0;
        // Point - 정렬된 Map Iterator 사용하기
        Iterator it = sortByValue(genre).iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            // Point - 중첩 Map -> 중첩 Iterator
            Iterator indexIt = sortByValue(music.get(key)).iterator();
            int playsCnt = 0;

            while (indexIt.hasNext()) {
                resultAL.add((int) indexIt.next());
                mCnt++;
                playsCnt++;
                if (playsCnt > 1) break;
            }
        }

        return resultAL;
    }

    private ArrayList sortByValue(final Map map) {
        ArrayList<Object> keyList = new ArrayList(map.keySet());

        Collections.sort(keyList, new Comparator() {
            public int compare(Object o1, Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);

                return ((Comparable) v2).compareTo(v1);
            }
        });

        return keyList;
    }

    // ~13.07ms, 79.5MB
    // 스트림
    class Music implements Comparable<Music> {

        private int played;
        private int id;
        private String genre;

        public Music(String genre, int played, int id) {
            this.genre = genre;
            this.played = played;
            this.id = id;
        }

        @Override
        public int compareTo(Music other) {
            if (this.played == other.played) return this.id - other.id;
            return other.played - this.played;
        }

        public String getGenre() {
            return genre;
        }

        private int[] exam2(String[] genres, int[] plays) {
            return IntStream.range(0, genres.length)
                    .mapToObj(i -> new Music(genres[i], plays[i], i)) // (장르, 재생횟수, 곡 번호) 한 번에 관리
                    .collect(Collectors.groupingBy(Music::getGenre))
                    .entrySet().stream() // 모아서 정렬하기 위해 다시 스트림 생성
                    .sorted((a, b) -> sum(b.getValue()) - sum(a.getValue()))
                    .flatMap(x -> x.getValue().stream().sorted().limit(2))
                    .mapToInt(x -> x.id).toArray();
        }

        private int sum(List<Music> value) {
            int answer = 0;
            for (Music music : value) {
                answer += music.played;
            }
            return answer;
        }

        public static void main(String[] args) {
            PG_42579 pg_42579 = new PG_42579();
            // expected : [4,1,3,0]
            System.out.println(pg_42579.codeOfMine(
                    new String[]{"classic", "pop", "classic", "classic", "pop"},
                    new int[]{500, 600, 150, 800, 2500})
            );
            // expected : [4,1,0,3]
            // 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래가 우선순위 높다는 점 고려하지 않은 경우
            System.out.println(pg_42579.codeOfMine(
                    new String[]{"classic", "pop", "classic", "classic", "pop"},
                    new int[]{800, 600, 150, 800, 2500})
            );
            // expected : [0,3,1]
            // 한 장르에 한 가지 음악만 있는 경우
            System.out.println(pg_42579.codeOfMine(
                    new String[]{"classic", "pop", "classic", "classic"},
                    new int[]{800, 600, 150, 800})
            );
        }
    }
}
