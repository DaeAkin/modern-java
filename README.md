## Spliterator

- Spliterator는 분할을 어떤식으로 하는거징?
?


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

  `forEachRemaning` 메소드를 보면 do ~ while 문을 사용하여 tryAdvance 메소드를 반복적으로 호출을 합니다. 









https://java8tips.readthedocs.io/en/stable/parallelization.html
