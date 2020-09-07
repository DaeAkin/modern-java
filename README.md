## Spliterator

Spliterator는 jdk8에서 나온 새로운 인터페이스인데, 요소를 순회하며, 분할 합니다. 이름에서도 암시하 듯이, 분할이 가능한 iterator 입니다. iterator와 마찬가지로 Spliterator는 요소를 순회할 수 있지만, stream 안에서만 순회가 가능 합니다.  Spliterator는 순차 stream, parallel 스트림 처리를 위한 몇 개의 중요한 메소드가 있습니다.

**Spliterator.java**

```java
public interface Spliterator<T> {

   boolean tryAdvance(Consumer<T> action);
   default void forEachRemaining(Consumer<T> action);
   Spliterator<T> trySplit();
   long estimateSize();
   int characteristics();
}
```

Spliterator의 클래스는 다음과 같이 5개의 메소드로 이루어져 있습니다.

## *tryAdvance*

**tryAdvance** 메소드는 spliterator의 요소(Element)를 순서대로 순회하기 위해 사용합니다. 이 메소드는 순서대로 하나씩 처리할 Consumer를 파라미터로 받습니다.  만약 처리할 요소가 남아 있으면 true를 리턴하고, 그렇지 않으면 spliterator의 모든요소(Element)가 처리 되었고, 나가도 된다는 false를 리턴 합니다.



## *trySplit*

**trySplit** 는 요소를 분할하여 Spliterator를 리턴합니다. 다른 스레드가 분할된 Spliterator을 처리하여, 병행처리를 가능하게 해줍니다. (parallel = true 일때만 동작하는 함수)

**The splitting process worked as intended and divided the records equally**.



## *estimatedSize*

**estimateSize** 는 spliterator에서 사용 가능한 요소의 수를 알려 줍니다. 이 메소드는 대부분 *AbstractTask* 같은 forkjoin에서 trySplit을 호출하기전에 사이즈를 체크하는 용도로 사용합니다.

```java
LOG.info("Size: " + split1.estimateSize());
```



## *characteristics*

**characteristics** 메소드는 구조,소스,요소의 특징을 나타내는데, ORDERED, DISTINCT, STORED, SIZED, NONNULL, IMMUTABLE, CONCURRENT , SUBSIZE로 나타냅니다. Spliterator를 사용하는 클라이언트가 손쉽게 제어할 수 있게 해줍니다. 예를 들어, Collection의 관련된 Spliterator는 SZIED를 사용하고, Set과 관련된 Spliterator는 DISTINCT를 그리고 SortedSet과 관련된 Spliterator는 SORTED를 사용합니다.

- **SIZED** : estimatedSize() 메소드를 사용하여 정확한 수의 요소를 반환 할 수 있는 경우
- **SORTED** : 정렬된 소스(source)를  순회할 수 있는 경우
- **SUBSIZED** :  trySplit() 메소드를 사용하여 분할 한 후, SIZED된 Spliterator를 얻을 수 있는 경우
- **CONCURRENT** : 소스(source)를 동시에 안전하게 수정할 수 있는 경우




- **forEachRemaining** 메소드는 디폴트 메소드이며 s a default method indicates spliterator to take certain action when no more splitting require. Basically this performs the given action for each remaining element, sequentially in the current thread, until all elements have been processed

  ```java
  default void forEachRemaining(Consumer<T> action) {
     do {
  
     } while (tryAdvance(action));
  }
  ```

  `forEachRemaning` 메소드를 보면 순차적으로 spliterator 요소를 처리하기 위해 do ~ while 문을 사용하여 tryAdvance 메소드를 반복적으로 호출을 합니다. While splitting task when a spliterator finds itself to be small enough that can be executed sequentially then it calls forEachRemaining method on its elements.
  
  
  
- **characteristics** 메소드는 구조,소스,요소의 특징을 나타내는데, ORDERED, DISTINCT, STORED, SIZED, NONNULL, IMMUTABLE, CONCURRENT , SUBSIZE로 나타냅니다. Spliterator를 사용하는 클라이언트를 손쉽게 제어할 수 있게 해줍니다. 예를 들어, Collection의 관련된 Spliterator는 SZIED를 사용하고, Set과 관련된 Spliterator는 DISTINCT를 그리고 SortedSet과 관련된 Spliterator는 SORTED를 사용합니다.





## characteristics





## Oracle 공식 문서

Spliterator는 각각으로 요소를 순회하 거나(tryAdvance()), 순차적(forEachRemaining())으로 순회할 수 있습니다. 

trySplite()를 이용하여 병행 연산을 위해 또다른 Spliterator로 나눌 수 있습니다. Spliterator를 이용할 때 요소를 분할할 수 없거나, 분할 했는데 밸런스맞게 분할되지 않았거나, 비효율적인 방법을 사용했다면, 병행처리의 이점을 얻을 수 없습니다.

IMMUTABLE이나 CONCURRENT를 사용하지 않는 Spliterator는 다음과 같은 정책이 적용 됩니다. A Spliterator that does not report `IMMUTABLE` or `CONCURRENT` is expected to have a documented policy concerning: when the spliterator *binds* to the element source; and detection of structural interference of the element source detected after binding. A *late-binding* Spliterator binds to the source of elements at the point of first traversal, first split, or first query for estimated size, rather than at the time the Spliterator is created. A Spliterator that is not *late-binding* binds to the source of elements at the point of construction or first invocation of any method. Modifications made to the source prior to binding are reflected when the Spliterator is traversed. After binding a Spliterator should, on a best-effort basis, throw [`ConcurrentModificationException`](https://docs.oracle.com/javase/8/docs/api/java/util/ConcurrentModificationException.html) if structural interference is detected. Spliterators that do this are called *fail-fast*. The bulk traversal method ([`forEachRemaining()`](https://docs.oracle.com/javase/8/docs/api/java/util/Spliterator.html#forEachRemaining-java.util.function.Consumer-)) of a Spliterator may optimize traversal and check for structural interference after all elements have been traversed, rather than checking per-element and failing immediately.

이런 병행 알고리즘이 있음에도 불구하고, Spliterator는 스레드세이프 하지 않습니다. 그 대신 병행 알고리즘의 구현은 Spliterator가 오직 한 번에 한 스레드가 동작하게 만들어놨습니다. 스레드가 tyrSplit() 메소드를 호출하면 다른 스레드에게 Spliterator를 건네줍니다. 





## Late-Binding?



## 참고자료

https://stackoverflow.com/questions/58595439/spliterator-on-modified-underlying-collection/59093939#59093939

https://java8tips.readthedocs.io/en/stable/parallelization.html
