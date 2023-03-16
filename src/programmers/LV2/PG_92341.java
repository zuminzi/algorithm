package programmers.LV2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// input: 주차요금 fees, 자동차의 입/출차 내역 records
// output: 차량번호가 작은 자동차부터 주차요금
public class PG_92341 {
    private static Map<Integer, Integer> result;
    private static Map<String, String> entranceTime;
    private static Map<String, Long> parkingHour;
    // ~82.17ms, 78.9MB
    public Object[] codeOfMine(int[] fees, String[] records) throws ParseException {
        result = new TreeMap<>();
        entranceTime = new HashMap<>();
        parkingHour = new HashMap<>();

        for(String record: records){
            String carNo = record.split(" ")[1];
            if (record.endsWith("N")) {
                entranceTime.put(carNo, record.split(" ")[0]);
            } else {
                calculateFee(fees[0], fees[1], fees[2], fees[3], carNo, record.split(" ")[0]);
            }
        }
        for(Map.Entry<String, String> entry : entranceTime.entrySet()){
            calculateFee(fees[0], fees[1], fees[2], fees[3], entry.getKey(), "23:59");
        }
        return result.values().toArray();
    }

    private static void calculateFee(int basicHour, int basicFee, int unitHour, int unitFee, String carNo, String outTime) throws ParseException {
        long diff = getDiff(carNo, outTime);

        if(diff <= basicHour){
            result.put(Integer.valueOf(carNo), basicFee);
        } else {
            double totalH = Math.ceil((diff - basicHour) / (double)unitHour);
            int fee =  (int) totalH * unitFee + basicFee;

            result.put(Integer.valueOf(carNo), fee);
        }
    }

    private static long getDiff(String carNo, String outTime) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        // 출차시간이 23:59인 케이스는 for문 순회 중이기 때문에 map.remove() 불가
        long in = outTime.equals("23:59")? timeFormat.parse(entranceTime.get(carNo)).getTime():timeFormat.parse(entranceTime.remove(carNo)).getTime();
        long out = timeFormat.parse(outTime).getTime();
        long diff = (out - in) / (1000 * 60);
        long prev = parkingHour.getOrDefault(carNo,0L);
        diff += prev;

        parkingHour.put(carNo, diff);

        return diff;
    }

    public static void main(String[] args) throws ParseException {
        PG_92341 pg_92341 = new PG_92341();
        System.out.println(pg_92341.codeOfMine(new int[]{180, 5000, 10, 600},
                new String[]{"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT",
                        "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN",
                        "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"})); // expected : [14600, 34400, 5000]
    }
}
