package org.muhabdulloh.cache.data_type;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LRUCache<K, V> implements Cache<K, V> {

    private final int size;
    private final Map<K, V> cacheMap = new HashMap<>();
    private final LinkedList<K> listKeys = new LinkedList<>();

    public LRUCache(int size) {
        this.size = size;
    }

    @Override
    public void put(K key, V value) {
        if (listKeys.contains(key)) {
            cacheMap.put(key, value);
            listKeys.remove(key);
        } else {
            if (cacheMap.size() >= size) {
                K leastUsedKey = listKeys.getLast();
                cacheMap.remove(leastUsedKey);
            }
            cacheMap.put(key, value);
        }
        listKeys.addFirst(key);
        log.info("successfully put new item with key: {} and value: {}", key, value);
    }

    @Override
    public Optional<V> get(K key) {
        if (!cacheMap.containsKey(key)) {
            return Optional.empty();
        }
        listKeys.remove(key);
        listKeys.addFirst(key);
        return Optional.ofNullable(cacheMap.get(key));
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.cacheMap.isEmpty() && this.listKeys.isEmpty();
    }

    @Override
    public void clear() {
        cacheMap.clear();
        listKeys.clear();
        log.info("successfully clear all items from cache...");
    }
}
