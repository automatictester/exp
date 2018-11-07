package uk.co.automatictester.state;

public interface LockableMapState<K, V> {
    void put(K key, V value);
    V get(K key);
}
