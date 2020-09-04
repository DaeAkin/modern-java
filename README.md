## Spliterator


Spliterator는 jdk8에서 나온 새로운 인터페이스인데, 요소를 순회하며, 분할 합니다. 이름에서도 암시하 듯이, 분할이 가능한 iterator 입니다. iterator와 마찬가지로 Spliterator는 요소를 순회할 수 있지만, stream 안에서만 순회가 가능 합니다.  Spliterator는 순차 stream, parallel 스트림 처리를 위한 몇 개의 중요한 메소드가 있습니다.

```java
public interface Spliterator<T> {

   boolean tryAdvance(Consumer<T> action);
   default void forEachRemaining(Consumer<T> action);
   Spliterator<T> trySplit();
   long estimateSize();
   int characteristics();
}
```

- **tryAdvance** 메소드는 spliterator의 요소를 컨슘할 때 사용합니다. 이 메소드는 처리할 요소가 남아 있으면 true를 리턴하고, 그렇지 않으면 spliterator의 모든요소가 처리 되었고, 나가도 된다는 false를 리턴 합니다.

- **forEachRemaining** 메소드는 디폴트 메소드이며 s a default method indicates spliterator to take certain action when no more splitting require. Basically this performs the given action for each remaining element, sequentially in the current thread, until all elements have been processed

  ```java
  default void forEachRemaining(Consumer<T> action) {
     do {
  
     } while (tryAdvance(action));
  }
  ```

  `forEachRemaning` 메소드를 보면 순차적으로 spliterator 요소를 처리하기 위해 do ~ while 문을 사용하여 tryAdvance 메소드를 반복적으로 호출을 합니다. While splitting task when a spliterator finds itself to be small enough that can be executed sequentially then it calls forEachRemaining method on its elements.
  
- **trySplit** 는 요소를 분할하여 두번 째 spliterator에게 병렬적으로 처리를 요청하기 위해 사용됩니다. 이런 아이디어는 자료 구조의 밸런스된 병렬 처리를 위함 입니다.  마지막의 분할 처리가 null을 반환 할 때 까지 spliterator는 반복적으로 trySplit 메소드를 호출합니다.  (parallel = true 일때만 동작하는 함수)

- **estimateSize** 는 spliterator에서 사용 가능한 요소의 수를 알려 줍니다. 이 메소드는 대부분 *AbstractTask* 같은 forkjoin에서 trySplit을 호출하기전에 사이즈를 체크하는 용도로 사용합니다.

- **characteristics** 메소드는 구조,소스,요소의 특징을 나타내는데, ORDERED, DISTINCT, STORED, SIZED, NONNULL, IMMUTABLE, CONCURRENT , SUBSIZE로 나타냅니다. Spliterator를 사용하는 클라이언트를 손쉽게 제어할 수 있게 해줍니다. 예를 들어, Collection의 관련된 Spliterator는 SZIED를 사용하고, Set과 관련된 Spliterator는 DISTINCT를 그리고 SortedSet과 관련된 Spliterator는 SORTED를 사용합니다.



## Oracle 공식 문서

Spliterator는 각각으로 요소를 순회하 거나(tryAdvance()), 순차적(forEachRemaining())으로 순회할 수 있습니다. 

trySplite()를 이용하여 병행 연산을 위해 또다른 Spliterator로 나눌 수 있습니다. Spliterator를 이용할 때 요소를 분할할 수 없거나, 분할 했는데 밸런스맞게 분할되지 않았거나, 비효율적인 방법을 사용했다면, 병행처리의 이점을 얻을 수 없습니다.





https://java8tips.readthedocs.io/en/stable/parallelization.html
