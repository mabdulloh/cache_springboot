package org.muhabdulloh.cache.data_type;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class LRUConcurrentCache<K, V> extends LRUCache<K, V> {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUConcurrentCache(int size) {
        super(size);
        this.cacheMap = new ConcurrentHashMap<>();
    }

    @Override
    public void put(K key, V value) {
        this.lock.writeLock().lock();
        try {
            super.put(key, value);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<V> get(K key) {
        this.lock.readLock().lock();
        try {
            return super.get(key);
        } finally {
            this.lock.readLock().unlock();
        }
    }
}
