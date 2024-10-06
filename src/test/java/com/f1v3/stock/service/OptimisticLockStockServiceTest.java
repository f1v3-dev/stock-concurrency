package com.f1v3.stock.service;

import com.f1v3.stock.domain.Stock;
import com.f1v3.stock.facade.OptimisticLockFacade;
import com.f1v3.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    OptimisticLockFacade optimisticLockFacade;

    @BeforeEach
    public void setUp() {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    void 낙관적_락_재고_감소_오류발생() throws InterruptedException {

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

    @Test
    void 낙관적_락_퍼사드_패턴_적용() throws InterruptedException {

        // when
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    optimisticLockFacade.decrease(1L, 1L);
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