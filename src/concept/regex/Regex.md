## Metacharacters
| Regular Expression   | 	Description                                     |
|----------------------|--------------------------------------------------|
| .                    | 	어떤 문자 1개를 의미                                    |
| ^regex	              | ^ 다음 regex로 line을 시작하는지                          |
| regex$               | 	$ 앞의 regex가 line의 마지막으로 끝나는지                    |
| [abc]                | 	a, b, c 중의 문자 1개                                |
| [abc][vz]            | 	a, b, c 중에 문자 1개와 v, z 중에 문자 1개의 조합             |
| [^abc]               | 	a, b, c를 제외한 문자 1개                              |
| [a-d1-7]	            | ad, 17 사이의 문자 1개                                 |
| \d                   | 	0~9 사이의 숫자, [0-9]와 동일                           |
| \D                   | 	숫자가 아닌 어떤 문자, [^0-9]와 동일                        |
| \s                   | 	whitespace 1개, [\t\n\x0b\r\f]와 동일               |
| \S                   | 	whitespace를 제외한 문자                              |
| \w                   | 	Alphanumeric(alphabet, 숫자) 문자, [a-zA-Z_0-9]와 동일 |
| \W                   | 	Alphanumeric을 제외한 문자(whitespace 등)              |
| \S+                  | 	whitespace를 제외한 여러 문자                           |
|  \b	                 | 단어의 경계(공백)를 찾습니다                                 |

## References
- [정규표현식 개념과 예제](https://codechacha.com/ko/java-regex/)