/*
[두 테이블 이너조인하여 날짜 컬럼 비교하기](https://school.programmers.co.kr/learn/courses/30/lessons/59043)
보호 시작일보다 입양일이 더 빠른 동물의 아이디와 이름을 조회해주세요. (보호 시작일 빠른 순 정렬)
 */
SELECT I.ANIMAL_ID, I.NAME
FROM ANIMAL_INS I
         INNER JOIN ANIMAL_OUTS O ON I.ANIMAL_ID = O.ANIMAL_ID
WHERE I.DATETIME > O.DATETIME
ORDER BY I.DATETIME;

/*
[차집합](https://school.programmers.co.kr/learn/courses/30/lessons/59043)
아직 입양을 못 간 동물 중, 가장 오래 보호소에 있었던 동물 3마리의 이름과 보호 시작일을 조회해주세요.(보호시작일 빠른 순 정렬)
 */
SELECT I.NAME, I.DATETIME
FROM ANIMAL_INS I
         LEFT JOIN ANIMAL_OUTS O ON I.ANIMAL_ID = O.ANIMAL_ID
WHERE O.ANIMAL_ID IS NULL
ORDER BY I.DATETIME
    LIMIT 3;

/*
[특정 문자열 포함 - LIKE 연산](https://school.programmers.co.kr/learn/courses/30/lessons/59045)
보호소에 들어올 당시에는 중성화되지 않았지만, 보호소를 나갈 당시에는 중성화된 동물의 아이디와 생물 종, 이름을 조회하는 아이디 순으로 조회해주세요.
 */
SELECT I.ANIMAL_ID, I.ANIMAL_TYPE, I.NAME
FROM ANIMAL_INS I
         LEFT JOIN ANIMAL_OUTS O ON I.ANIMAL_ID = O.ANIMAL_ID
WHERE I.SEX_UPON_INTAKE LIKE 'Intact%' AND O.SEX_UPON_OUTCOME NOT LIKE 'Intact%'
ORDER BY I.ANIMAL_ID;

/*
 [집계함수 SUM](https://school.programmers.co.kr/learn/courses/30/lessons/133027)
 7월 아이스크림 총 주문량과 상반기의 아이스크림 총 주문량을 더한 값이 큰 순서대로 상위 3개의 맛을 조회해주세요.
 - 그냥 JOIN 으로 선언하는 명령어는 곧 INNER JOIN을 의미한다.
 */
 -- 조인
SELECT J.FLAVOR
FROM JULY J
         INNER JOIN FIRST_HALF H ON J.FLAVOR = H.FLAVOR
GROUP BY FLAVOR
ORDER BY SUM(J.TOTAL_ORDER + H.TOTAL_ORDER) DESC
    LIMIT 3;
-- 서브쿼리(UNION)
-- FROM절에 사용하는 서브 쿼리를 인라인 뷰라고 한다.
-- 메인쿼리에서 인라인 뷰 컬럼 조회 시 별칭(e.g. combined_tables)을 지정해야 한다.
SELECT FLAVOR
FROM
    (SELECT *
     FROM FIRST_HALF
     UNION
     SELECT *
     FROM JULY) combined_tables
GROUP BY FLAVOR
ORDER BY SUM(TOTAL_ORDER) DESC
    LIMIT 3;

/*
 [집계함수 SUM](https://school.programmers.co.kr/learn/courses/30/lessons/131533)
 상품코드 별 매출액(판매가 * 판매량) 합계를 출력해주세요. (매출액을 내림차순 정렬, 매출액이 같다면 상품코드 오름차순 정렬)
 */
SELECT PRODUCT_CODE, SUM(PRICE * SALES_AMOUNT) AS SALES
FROM PRODUCT p
         INNER JOIN OFFLINE_SALE o ON p.PRODUCT_ID = o.PRODUCT_ID
GROUP BY PRODUCT_CODE
ORDER BY SALES DESC, PRODUCT_CODE

/*
 [DATE_FORMAT](https://school.programmers.co.kr/learn/courses/30/lessons/144854)
 '경제' 카테고리에 속하는 도서들의 도서 ID, 저자명, 출판일 리스트를 출력해주세요. (출판일 오름차순 정렬, PUBLISHED_DATE 포맷 예시와 동일하게 작성)
 */
SELECT BOOK_ID, AUTHOR_NAME, DATE_FORMAT(PUBLISHED_DATE, '%Y-%m-%d') as PUBLISHED_DATE
FROM BOOK b
         INNER JOIN AUTHOR a ON b.AUTHOR_ID = a.AUTHOR_ID
WHERE CATEGORY = '경제'
ORDER BY PUBLISHED_DATE;

/*
[DATE_FORMAT](https://school.programmers.co.kr/learn/courses/30/lessons/131117)
생산일자가 2022년 5월인 식품들의 식품 ID, 식품 이름, 총매출을 조회해주세요.
 */
SELECT p.PRODUCT_ID, p.PRODUCT_NAME, SUM(p.PRICE * o.AMOUNT) AS TOTAL_SALES
FROM FOOD_PRODUCT p
         INNER JOIN FOOD_ORDER o ON p.PRODUCT_ID = o.PRODUCT_ID
WHERE DATE_FORMAT(o.PRODUCE_DATE, '%Y-%m') = '2022-05'
GROUP BY p.PRODUCT_ID
ORDER BY TOTAL_SALES DESC, p.PRODUCT_ID;