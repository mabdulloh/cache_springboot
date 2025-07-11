package org.muhabdulloh.cache.data_type;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class LRUConcurrentCacheTest {
    private final static int SIZE = 50;
    private final Cache<Integer, String> cache = new LRUConcurrentCache<>(SIZE);

    @Test
    void runLRUConcurrentCacheTest_NoDataLost() {
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            CountDownLatch countDownLatch = new CountDownLatch(SIZE);
            IntStream.range(0, SIZE).<Runnable>mapToObj(key -> () -> {
                cache.put(key, "value: " + key);
                countDownLatch.countDown();
            }).forEach(executorService::submit);
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("Interrupted with: {}", e.getMessage());
        } finally {
            executorService.shutdown();
        }
        assertEquals(SIZE, cache.size());
        IntStream.range(0, SIZE).forEach(i -> {
            assertTrue(cache.get(i).isPresent());
            assertEquals("value: " + i, cache.get(i).get());
        });
    }
}
