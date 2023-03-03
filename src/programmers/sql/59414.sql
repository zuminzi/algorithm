-- DATE_FORMAT(날짜 , 형식) : 날짜를 지정한 형식으로 출력
SELECT ANIMAL_ID, NAME, date_format(DATETIME,'%Y-%m-%d') AS 날짜
FROM ANIMAL_INS
ORDER BY animal_id;