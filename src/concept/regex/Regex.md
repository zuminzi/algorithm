## Metacharacters
|  Regular Expression  | 	Description                                           |
|:--------------------:|:-------------------------------------------------------|
|          .           | 	어떤 문자 1개를 의미                                          |
|       ^regex	        | ^ 다음 regex로 line을 시작하는지                                |
|        regex$        | 	$ 앞의 regex가 line의 마지막으로 끝나는지                          |
|        [abc]         | 	a, b, c 중의 문자 1개                                      |
|      [abc][vz]       | 	a, b, c 중에 문자 1개와 v, z 중에 문자 1개의 조합                   |
|        [^abc]        | 	a, b, c를 제외한 문자 1개                                    |
|      [a-d1-7]	       | ad, 17 사이의 문자 1개                                       |
|          \d          | 	**0~9 사이의 숫자** 하나, [0-9]와 동일                              |
|          \D          | 	숫자가 아닌 어떤 문자 하나, [^0-9]와 동일                           |
|          \s          | 	whitespace(**공백문자**)(탭, 줄바꿈, 스페이스) 1개, [\t\n\x0b\r\f]와 동일 |
|          \S          | 	whitespace를 제외한 문자 하나                                 |
|          \w          | 	Alphanumeric(**숫자, 영문, 언더바**) 문자 하나, [a-zA-Z_0-9]와 동일     |
|          \W          | 	Alphanumeric을 제외한 문자(whitespace 등)                    |
|         \S+          | 	whitespace를 제외한 여러 문자                                 |
|         \b	          | **단어의 경계(공백)**를 찾습니다                                       |
## Quantifiers
요소들을 얼마나 반복시킬지 정의

| Regular Expression | 	Description|
|:------:|:---------------------|
| * | 	0회 이상 반복|
| + | 	1회 이상 반복|
| ? | 	0 또는 1회만|
|   {X}   | 	X회 이상 반복|
|   {X,Y}   | 	X~Y 사이의 수만큼 반복|
|   *?   | 	가장 적게 일치하는 패턴을 찾음|

- EX
  - `a(bc)*` : a 그리고 0개 이상의 bc를 포함한 문자열

## Operators
| Regular Expression | 	Description |
|-------------------|--------------|
| a(b)              | a 그리고 b      |
| a[bc]             | a 그리고 b 또는 c |

## Regex를 지원하는 String 메소드
|                                    Method                                    | 	Description                             |
|:----------------------------------------------------------------------------:|------------------------------------------|
|                            String.matches(regex)                             | 	String이 regex와 일치하면 true 리턴             |
|                             String.split(regex)                              | 	regex와 일치하는 것을 기준으로 String을 분리하여 배열로 리턴 |
| String.replaceFirst(regex, replacement)| 	regex와 가장 먼저 일치하는 것을 replacement로 변환    |
|    String.replaceAll(regex, replacement)	| regex와 일치하는 모든 것을 replacement로 변환        |
## Flags
정규식은 보통 `/abc/`와 같은 형식을 사용하며, 두개의 슬래쉬 문자/ 사이에 정규식을 작성합니다. 
<br> 두번째 슬래쉬 뒤에 플래그를 사용할 수 있습니다.

| Flag | 	Description |
|------|----------------|
|g(global)| 매칭되는 모든 항목을 리턴|
|m(multi-line)| anchor(^ 또는 $)가 문자열 전체가 아닌, 줄 각각에 매칭하여 줄별로 정규식 패턴을 매칭|
|i(insensitive)| 대소문자 구분을 무시|
- EX
  - `/aBc/i` : AbC와도 매칭가능
## Greedy and Lazy match
수량표현식 `(* + {})`은 욕심많은 연산자이기 때문에, 제공된 데이터에서 최대한 많이 매칭하려고 합니다.
<br> 예를 들어, `<.+>`에서 `?`를 추가하여 게으르게 만들 수 있습니다.

> `<.+?>` : .(1개)와 +(1개이상)을 통해 <> 사이에 하나 이상의 문자와 매칭하지만, ?을 통해 최대한 짧게 매칭

더 나은 방법은 `.` 연산자를 사용하지 않고 좀더 엄격한 정규식을 사용하는 것입니다.

> `<[^<>]+>` : < 와 > 사이에 <,>가 아닌 모든 문자가 하나 이상인 문자와 매칭

## References
- [정규표현식 개념과 예제](https://codechacha.com/ko/java-regex/)
- [정규식 핵심만 모아놓은 Cheat Sheet](https://chrisjune-13837.medium.com/%EC%A0%95%EA%B7%9C%EC%8B%9D-%ED%8A%9C%ED%86%A0%EB%A6%AC%EC%96%BC-%EC%98%88%EC%A0%9C%EB%A5%BC-%ED%86%B5%ED%95%9C-cheatsheet-%EB%B2%88%EC%97%AD-61c3099cdca8)