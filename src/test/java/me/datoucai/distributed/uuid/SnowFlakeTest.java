package me.datoucai.distributed.uuid;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @author cc
 * @date 2018/11/29
 */
@Slf4j
public class SnowFlakeTest {

    @Test
    public void nextId() {
        SnowFlake snowFlake = new SnowFlake(1, 2);
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            Executors.newFixedThreadPool(10).submit(() -> {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Thread：{}--uuid：{}", Thread.currentThread().getName(), snowFlake.nextId());
                latch.countDown();
            });
        }
        try {
            log.info("count now：{}", latch.getCount());
            latch.await();
            log.info("count now：{}", latch.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}