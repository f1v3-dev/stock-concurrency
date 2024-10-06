package com.f1v3.stock.facade;

import com.f1v3.stock.repository.RedisRepository;
import com.f1v3.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Lettuce Lock Stock Facade.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisRepository repository;
    private final StockService stockService;

    public void decrease(Long productId, Long quantity) throws InterruptedException {

        while(!repository.lock(productId)) {
            log.info("락 획득 시도중..");
            Thread.sleep(100);
        }

        log.info("락 획득 성공! 비즈니스 로직 수행");

        try {
            stockService.decrease(productId, quantity);
        } finally {
            repository.unlock(productId);
            log.info("락 해제");
        }

    }

}
