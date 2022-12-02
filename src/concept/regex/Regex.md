## Metacharacters
|  Regular Expression  | 	Description                                     |
|:--------------------:|:-------------------------------------------------|
|          .           | 	어떤 문자 1개를 의미                                    |
|       ^regex	        | ^ 다음 regex로 line을 시작하는지                          |
|        regex$        | 	$ 앞의 regex가 line의 마지막으로 끝나는지                    |
|        [abc]         | 	a, b, c 중의 문자 1개                                |
|      [abc][vz]       | 	a, b, c 중에 문자 1개와 v, z 중에 문자 1개의 조합             |
|        [^abc]        | 	a, b, c를 제외한 문자 1개                              |
|      [a-d1-7]	       | ad, 17 사이의 문자 1개                                 |
|          \d          | 	0~9 사이의 숫자, [0-9]와 동일                           |
|          \D          | 	숫자가 아닌 어떤 문자, [^0-9]와 동일                        |
|          \s          | 	whitespace 1개, [\t\n\x0b\r\f]와 동일               |
|          \S          | 	whitespace를 제외한 문자                              |
|          \w          | 	Alphanumeric(alphabet, 숫자) 문자, [a-zA-Z_0-9]와 동일 |
|          \W          | 	Alphanumeric을 제외한 문자(whitespace 등)              |
|         \S+          | 	whitespace를 제외한 여러 문자                           |
|         \b	          | 단어의 경계(공백)를 찾습니다                                 |
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

## Regex를 지원하는 String 메소드
|                                    Method                                    | 	Description                             |
|:----------------------------------------------------------------------------:|------------------------------------------|
|                            String.matches(regex)                             | 	String이 regex와 일치하면 true 리턴             |
|                             String.split(regex)                              | 	regex와 일치하는 것을 기준으로 String을 분리하여 배열로 리턴 |
| String.replaceFirst(regex, replacement)| 	regex와 가장 먼저 일치하는 것을 replacement로 변환    |
|    String.replaceAll(regex, replacement)	| regex와 일치하는 모든 것을 replacement로 변환        |
## References
- [정규표현식 개념과 예제](https://codechacha.com/ko/java-regex/)