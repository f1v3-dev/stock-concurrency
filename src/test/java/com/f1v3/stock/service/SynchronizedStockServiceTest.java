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

/**
 * {class name}.
 *
 * @author 정승조
 * @version 2024. 10. 07.
 */
@SpringBootTest
class SynchronizedStockServiceTest {

    @Autowired
    SynchronizedStockService stockService;

    @Autowired
    StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        stockRepository.save(new Stock(1L, 100L));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    @DisplayName("synchronized 키워드 테스트 - 실패")
    void test1() throws InterruptedException {
        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findByProductId(1L);
        assertEquals(0, stock.getQuantity());

        /*
        실패한 이유가 무엇일까?

        - @Transactional 어노테이션
        - synchronized 키워드

        둘의 조합을 생각해보자.
        트랜잭션은 AOP 방식으로 동작한다! (프록시 객체를 생성하여 동작)

        비즈니스 로직이 호출된 후, 커밋이 되기 전이라고 생각해보자.
        이 때, 다른 스레드가 synchronized 메서드에 진입 후 조회를 통해 현재 남아있는 재고 개수를 조회했다면?!
         */
    }

}