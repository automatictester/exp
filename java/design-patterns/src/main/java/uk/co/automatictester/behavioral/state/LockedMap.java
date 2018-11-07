package uk.co.automatictester.behavioral.state;

@SuppressWarnings("WeakerAccess")
public class LockedMap<K, V> implements LockableMapState<K, V> {

    private LockableMap<K, V> ref;

    public LockedMap(LockableMap<K, V> lockableMap) {
        ref = lockableMap;
    }

    @Override
    public void put(K key, V value) {
        throw new IllegalStateException("Instance of LockableMap is in read-only mode");
    }

    @Override
    public V get(K key) {
        return ref.map.get(key);
    }
}
