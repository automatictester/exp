### Common tools for solving concurrency problems in Java
- global synchronization (synchronized methods), an example of a structured lock
- object-based synchronization (synchronized blocks), an example of a structured lock
- coarse-grained unstructured locking with ReentrantLock
- fine-grained unstructured locking with ReentrantReadWriteLock
- atomic constructs, most importantly: AtomicInteger, AtomicReference(V)
- concurrent constructs, most importantly: ConcurrentHashMap, ConcurrentLinkedQueue, ConcurrentSkipListSet
- volatile keyword - see [this guideline](https://www.ibm.com/developerworks/java/library/j-jtp06197)

### Higher-level constructs
- Actor model with no shared state
- Embedding lock constructs inside elements stored by concurrent collection - for use cases, where more than
 one thread may access the same element of the collection, e.g. Boruvka's algorithm which uses more than one element 
 as part of the same iteration