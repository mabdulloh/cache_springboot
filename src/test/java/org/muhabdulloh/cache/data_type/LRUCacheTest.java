package org.muhabdulloh.cache.data_type;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LRUCacheTest {

    private final static int SIZE = 10;
    private final Cache<Integer, String> cache = new LRUCache<>(SIZE);

    @Test
    void generateEmptyLRUCache_shouldReturnIsEmpty() {
        assertEquals(SIZE, cache.size());
        assertTrue(cache.isEmpty());
    }

    @Test
    void addingNewElement_shouldReturnFalseOnIsEmpty() {
        String firstElement = "first element";
        cache.put(1, firstElement);
        assertFalse(cache.isEmpty());

        var result =  cache.get(1);
        assertTrue(result.isPresent());
        assertEquals(firstElement, result.get());
    }

    @Test
    void clearing_shouldReturnIsEmpty() {
        String firstElement = "first element";
        cache.put(1, firstElement);
        assertFalse(cache.isEmpty());
        cache.clear();
        assertTrue(cache.isEmpty());
    }
}
