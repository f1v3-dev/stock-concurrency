package com.f1v3.stock.service;

import com.f1v3.stock.domain.Stock;
import com.f1v3.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OptimisticLockStockServiceTest {

    @Autowired
    OptimisticLockStockService stockService;

    @Autowired
    StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    @DisplayName("낙관적 락 재고 감소 테스트")
    void test1() {
        stockService.decrease(1L, 1L);

        Stock stock = stockRepository.findByProductId(1L);
        assertEquals(99, stock.getQuantity());
    }

    @Test
    @DisplayName("낙관적 락 재고 감소 - 예외 발생")
    void tet2() throws InterruptedException {

        // when
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        Stock stock = stockRepository.findByProductId(1L);
        assertEquals(0, stock.getQuantity());

    }
}