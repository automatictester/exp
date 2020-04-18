### Overview

Concurrency in Java is all about:
- Synchronization
- Visibility

### Common tools for solving concurrency problems in Java
- `synchronized` methods
- `synchronized` blocks
- Explicit locking with `ReentrantLock` and `ReentrantReadWriteLock`, when `synchronized` doesn't offer required functionality
- `Semaphore` as a N-permit lock mechanism
- Atomic classes, e.g. `AtomicInteger`, `AtomicReference(V)`
- `volatile` for read-by-many, written-by-one situations - see [this guideline](https://www.ibm.com/developerworks/java/library/j-jtp06197)
- Synchronizers, e.g. `CyclicBarrier`, `CountDownLatch`, `Phaser`
- Implementing wrapper classes with custom synchronized methods for already synchronized classes through inheritance, if no such concurrent class exists
- Implementing wrapper classes with custom synchronized methods for synchronized or not synchronized classes through composition
- Embedding `ReentrantLock` and `ReentrantReadWriteLock` constructs inside elements stored by concurrent collection
- Concurrent collections - see below
- Actor model with no shared state

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