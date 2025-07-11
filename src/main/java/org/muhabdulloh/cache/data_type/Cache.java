package org.muhabdulloh.cache.data_type;

import java.util.Optional;

public interface Cache<K, V> {
    void put(K key, V value);
    Optional<V> get(K key);
    int size();
    boolean isEmpty();
    void clear();
}
