### Overview

Concurrency in Java is about:
- Mutual exclusion / atomicity
- Visibility
- Performance

### Common tools for solving concurrency problems in Java
- Intrinsic locking with `synchronized` methods and blocks
- Explicit locking with `ReentrantLock` and `ReentrantReadWriteLock`
- N-permit locking with `Semaphore` 
- Synchronizers
- Volatile variables
- Atomic classes
- Concurrent collections
- Inheritance-based wrappers for already synchronized classes with custom synchronized methods 
- Composition-based wrappers for synchronized or not synchronized classes with custom synchronized methods
- Custom synchronizers for single condition predicates with condition queues (`wait()` / `notify()` / `notifyAll()`)
- Custom synchronizers for N-condition predicates with condition objects (`await()` / `signal()` / `signalAll()`)
- Embedding `ReentrantLock` and `ReentrantReadWriteLock` constructs inside elements stored by concurrent collection
- Actor model

### Concurrent collections

| Collection      | Synchronized | Concurrent |
|-----------------|--------------|------------|
|List|Collections.synchronizedList|CopyOnWriteArrayList|
|Set|Collections.synchronizedSet|ConcurrentSkipListSet (concurrent replacement for TreeSet), CopyOnWriteArraySet|
|Map|Collections.synchronizedMap|ConcurrentSkipListMap (concurrent replacement for TreeMap), ConcurrentMap, ConcurrentHashMap|
|Queue| |ConcurrentLinkedQueue, ConcurrentLinkedDeque, implementations of BlockingQueue|

### Memory semantics

Summary of key Java Memory Model implications:

- When acquiring the monitor, local memory (*) is invalidated and subsequent reads go directly to main memory.
- When releasing the monitor, local memory (*) is flushed to main memory.
- The above process guarantees that all writes by thread A prior to releasing monitor M will be visible to thread B
  after it subsequently acquires the same monitor M.
- All the following have the same memory semantics of acquiring the lock: acquiring implicit lock, acquiring explicit
  lock, reading volatile variable, reading atomic variable.
- All the following have the same memory semantics of releasing the lock: releasing implicit lock, releasing explicit
  lock, writing volatile variable, writing atomic variable.

Local memory can be defined as caches, registers, and other hardware and compiler optimizations.

### Reordering

There are only 2 allowed reorderings, which involve monitor acquire or release actions:

1. Moving regular action after acquiring lock / volatile read:

    ```
    1. regular action       ==>     2. monitor acquire 
    2. monitor acquire              1. regular action
    ```

2. Moving releasing lock / volatile write after regular action: 

    ```
    1. monitor release      ==>     2. regular action 
    2. regular action               1. monitor release
    ```

### Partially constructed objects

Initializing a new object involves writing to new object's fields. Publishing a reference involves writing to another
variable - the reference to the new object. These can be reordered, if happens-before relationship is not enforced.
As a result, another thread may see stale values in the object's fields, as well as stale reference in a lazily
initialized singleton:
  
 ```java
@NotThreadSafe
class Singleton {

    private static Singleton singleton;

    // as there is no happens-before relationship, another thread can see either the default value of 0, or 2
    // this is the result of potential reordering of write to the field and setting the reference
    private int value;
    
    private Singleton() {
        this.value = 2;
    }

    // as there is no happens-before relationship, more than one instance may be created by concurrent threads
    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    // ...
}
```

For mutable objects, it is not safe to use an object initialized by another thread, unless the publication
happens-before the consuming thread uses it.

### Static initialization

Static initialization is thread-safe, as it is internally guided by locks by the JVM:

```java
@TheadSafe
public class MyStaticClass {
    private static int x = 10; // thread-safe unless re-written later
    private static int y;

    static {
        y = 20; // thread-safe unless re-written later
    }

    // ...
}
```

### Initialization safety

Initialization safety guarantees that properly constructed immutable objects (*) are always thread-safe and 
another threads will see the correct values of final fields that were set by the constructor without synchronization.
Same applies to any variables that can be reached only through a final field, e.g. items in a final ArrayList, 
as long as they don't change after construction:

```java
@ThreadSafe
public class MyClass {
    private final Set<Integer> set;

    public MyClass() {
        set = new HashSet<>();
        set.add(1);
        set.add(2);
    }
}
```

Properly constructed objects are objects, which reference is not published before the constructor has completed, 
e.g. through starting a thread from within a constructor, assigning it to a static field, registering it as a listener
with any other object etc.

### Double-checked locking

Double-checked locking was not thread-safe pre-Java 5, due to observing possibly partially constructed object.
Since Java 5 it is thread-safe, if the resource is volatile. However, it is no longer recommended as it addresses
no longer existing problem of slow uncontended synchronization and slow JVM startup:

```java
@ThreadSafe
public class MyDcl {
    private volatile static MyResource myResource;

    public static MyResource getInstance() {
        if (myResource == null) {
            synchronized (MyDcl.class) {
                if (myResource == null) {
                    myResource = new MyResource();
                }
            }
        }
    }
}
```

### Timeline

- Java 5:
    - `Atomic*`
    - `Callable`
    - `ConcurrentHashMap`
    - `ConcurrentMap`
    - `Condition`
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
- Java 8:
    - `Runnable` and `Callable` as lambda functions
    - Parallel streams