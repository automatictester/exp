### Overview

Concurrency in Java is all about:
- Synchronization
- Visibility

### Common tools for solving concurrency problems in Java
- Synchronized methods
- Synchronized blocks
- Explicit locking with `ReentrantLock`
- Fine-grained explicit locking with `ReentrantReadWriteLock`
- Atomic classes, e.g. `AtomicInteger`, `AtomicReference(V)`
- Volatile - see [this guideline](https://www.ibm.com/developerworks/java/library/j-jtp06197)
- Synchronizers, e.g. `CyclicBarrier`, `CountDownLatch`, `Phaser`
- Implementing wrapper classes with custom synchronized methods for already synchronized classes through inheritance, if no such concurrent class exists
- Implementing wrapper classes with custom synchronized methods for synchronized or not synchronized classes through composition
- Actor model with no shared state
- Embedding lock constructs inside elements stored by concurrent collection
- Concurrent collections - see below

### Concurrent collections

| Single-threaded | Synchronized | Concurrent |
|-----------------|--------------|------------|
|List|Collections.synchronizedList|CopyOnWriteArrayList|
|Set|Collections.synchronizedSet|ConcurrentSkipListSet (concurrent replacement for TreeSet), CopyOnWriteArraySet|
|Map|Collections.synchronizedMap|ConcurrentSkipListMap (concurrent replacement for TreeMap), ConcurrentMap, ConcurrentHashMap|

### Timeline

- Java 5:
    - `Atomic*`
    - `ConcurrentHashMap`
    - `ConcurrentMap`
    - `CopyOnWriteArrayList`
    - `CopyOnWriteArraySet`
    - `CountDownLatch`
    - `CyclicBarrier`
    - `ExecutorService`
    - `ReentrantLock`
    - `ReentrantReadWriteLock`
- Java 6:
    - `ConcurrentSkipListMap`
    - `ConcurrentSkipListSet`
- Java 7:
    - `ForkJoinPool`
    - `ForkJoinTask`
    - `Phaser`
    - `RecursiveAction`
    - `RecursiveTask`
    - `ThreadLocalRandom`