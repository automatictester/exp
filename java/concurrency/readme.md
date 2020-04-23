### Overview

Concurrency in Java is about:
- Mutual exclusion / atomicity
- Visibility
- Performance

### Common tools for solving concurrency problems in Java
- `synchronized` methods
- `synchronized` blocks
- Explicit locking with `ReentrantLock` and `ReentrantReadWriteLock`, when `synchronized` doesn't offer required functionality
- `Semaphore` as a N-permit lock mechanism
- Atomic classes, e.g. `AtomicInteger`, `AtomicReference(V)`, `AtomicIntegerArray`, `AtomicReferenceArray<E>`
- `volatile` for written-by-one, read-by-many scenarios - see [this guideline](https://www.ibm.com/developerworks/java/library/j-jtp06197)
- Synchronizers, e.g. `CyclicBarrier`, `CountDownLatch`, `Phaser`
- Wrapper classes with custom synchronized methods for already synchronized classes through inheritance
- Wrapper classes with custom synchronized methods for synchronized or not synchronized classes through composition
- Custom synchronizers for single condition predicates with condition queues (`wait()` / `notify()` / `notifyAll()`)
- Custom synchronizers for multiple condition predicates with condition objects (`await()` / `signal()` / `signalAll()`)
- Embedding `ReentrantLock` and `ReentrantReadWriteLock` constructs inside elements stored by concurrent collection
- Actor model with no shared state
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

### Java Memory Model

- To guarantee that the thread executing action B can see the results of action A, there must be a happens-before
  relationship between two operations. In the absence of a happens-before relationship, JVM is free to reorder them 
  as it sees fit.
- Ordinary actions are partially ordered, synchronizations are totally ordered.
- An unlock on a monitor happens-before every subsequent lock on that same monitor. Applies both to explicit Lock
  objects and intrinsic locks.
- A call to Thread.start on a thread happens-before every action in that started thread.
- Any action in a thread happens-before any other thread detects that the thread has terminated, either by Thread.join
  or Thread.isAlive == false.
- A thread calling interrupt on another thread happens-before the interruptied threads detects the interrupt,
  either by having InterruptedException thrown, or invoking isInterrupted or interrupted.
- The end of a constructor for an object happens-before the start of the finalizer for that object.
- If A happens-before B and B happens-before C, then A happens-before C.
- If thread A execution happens-before thread B, thread B will see value V at least as up-to-date as thread A set it.
  Subsequent writes may or may not be visible.

### Java Memory Model and library classes

- Placing an item in a thread-safe collection happens-before another thread reads it.
- Counting down on a CountDownLatch happens-before a thread returns from await on that latch.
- Releasing a permit to a Semaphore happens-before acquiring a permit from the same Semaphore.
- Actions taken by the task represented by a Future happens-before another thread successfully returns from Future.get.
- Submitting a Runnable or Callable to an executor happens-before the task begins execution.
- A thread arriving at a CyclicBarrier happens-before the other threads are released from that same barrier.
- If CyclicBarrier uses a barrier action, arriving at the barrier happens-before the barrier action,
  which in turns happens-before threads are released from the barrier.

### Memory semantics

- When acquiring the monitor, local memory (*) is invalidated and subsequent reads go directly to main memory.
- When releasing the monitor, local memory (*) is flushed to main memory.
- Above process guarantees that when a variable V is written by a thread A with a given monitor M acquired and read by
  thread B with the same monitor M acquired, the write to the variable V will be visible for thread B.
- Moreover, when thread A released monitor M, and thread B reads acquired the same monitor M, any variable values
  that were visible to A at the time that monitor M was released are guaranteed now to be visible to thread B 
  (piggybacking on synchronization).
- All the following have the same memory semantics of acquiring the lock: acquiring implicit lock, acquiring explicit
  lock, reading volatile variable, reading atomic variable.
- All the following have the same memory semantics of releasing the lock: releasing implicit lock, releasing explicit
  lock, writing volatile variable, writing atomic variable.

Local memory can be defined as caches, registers, and other hardware and compiler optimizations.

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
