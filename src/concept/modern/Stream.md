# Stream
## reduce와 collect 차이
### Reduction(reduce)
> T 타입에 대한 항등원에서 시작해서, 해당 이항 연산을 모든 T 타입 List의 원소들을 대입하여 연쇄적으로 계산

> 다시 말해, **T**에 대한 **이항 연산**을 반복하며 **T 타입 리스트를 한 바퀴 도는 것**이다

#### reduce의 input type과 output type
```java
// 1개 (accumulator)
Optional<T> reduce(BinaryOperator<T> accumulator);

// 2개 (identity)
T reduce(T identity, BinaryOperator<T> accumulator);

// 3개 (combiner)
<U> U reduce(U identity,
        BiFunction<U, ? super T, U> accumulator,
        BinaryOperator<U> combiner);
```
  - reduce는 타입이 T 한 가지로 고정되어 있다
  - `항등원(identity)`과 `이항연산자(BinaryOperator)`라는 다분시 수학적인 작명의 파라미터를 지니고 있다
    <br>
  - 3개의 인자를 받는 경우에는 combiner까지 받는다 
    - Combiner 는 병렬 처리 시 각자 다른 쓰레드에서 실행한 결과를 마지막에 합치기 때문에 병렬 스트림에서만 동작한다
  
    | Parameter | Description|
    |-----------|-----------|
    |accumulator | 각 요소를 처리하는 계산 로직. 각 요소가 올 때마다 중간 결과를 생성하는 로직|
    |identity | 계산을 위한 초기값으로 스트림이 비어서 계산할 내용이 없더라도 이 값은 리턴|
    |combiner | 병렬(parallel) 스트림에서 나눠 계산한 결과를 하나로 합치는 동작하는 로직|
#### reduce 메서드 작동 구조
```java
public <T> T streamReduce(List<T> tList, T identity, BinaryOperator<T> accumulator) {
for(T t : tList) {
accumulator.apply(identity, t);
}
return identity;
}
```
- ex
  - 항등원(identity)가 0이고, 이항연산자(BinaryOperator)가 +라면
    - 0 + 1 => 1 (항등원 + list[0])
    - 1 + 2 => 3 (앞의 연산 결과 + list[1])
    - 3 + 3 => 6 (앞의 연산 결과 + list[2])
    - 6 + 4 => 10 (앞의 연산 결과 + list[3])
    -    ...
    - 45 + 10 => 55 (앞의 연산 결과 + list[9])
### Collector(collect)
> accumulator 기능과 호환 가능한 value를 combine
#### collect의 input type과 output type
```java
  <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super T> accumulator,
                  BiConsumer<R, R> combiner);
```
- output 타입은 R 타입이고, input list의 타입은 T 계열(정확히는, ? super T)이다
### collect의 작동구조
1. `supplier` 로 `R 타입 인스턴스`를 생성한다.
2. 생성한 R 인스턴스에 `accumulator` 로 `T list`들을 합친다.(R 인스턴스에 T list 의 아이템들을 하나하나 축적해나간다.)
3. (parallel stream의 경우) 여러 스레드가 부분적으로 1, 2를 실행하여 얻어낸 R 타입 결과물들을 `combiner` 를 이용하여 하나로 합친다.

### reduce와 collect 사용하는 케이스?
| reduce                                          |collect|
|-------------------------------------------------|-------|
|List와 output의 타입이 같고, 이항 연산의 연쇄 작용으로서의 의미가 강한 경우 |리스트와 타입이 다른 output을 내는 경우|

#### References
- [stream 과 parallel stream, reduce 와 collect 의 차이와 사용법](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=woong17&logNo=221268337085)
- [Java 스트림 Stream (1) 총정리](https://futurecreator.github.io/2018/08/26/java-8-streams/)