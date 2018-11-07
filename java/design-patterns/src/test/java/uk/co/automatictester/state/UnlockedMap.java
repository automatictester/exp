package uk.co.automatictester.state;

@SuppressWarnings("WeakerAccess")
public class UnlockedMap<K, V> implements LockableMapState<K, V> {

    private LockableMap<K, V> ref;

    public UnlockedMap(LockableMap<K, V> lockableMap) {
        ref = lockableMap;
    }

    @Override
    public void put(K key, V value) {
        ref.map.put(key, value);
    }

    @Override
    public V get(K key) {
        return ref.map.get(key);
    }
}
