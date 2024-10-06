package com.f1v3.stock.facade;

import com.f1v3.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redisson Lock Stock Facade.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;
    private final StockService stockService;

    public void decrease(Long productId, Long quantity) {
        RLock lock = redissonClient.getLock(productId.toString());

        try {
            // 10초 동안 락 획득을 시도
            // 5초동안 점유할 수 있다.
            boolean available = lock.tryLock(10, 5, TimeUnit.SECONDS);

            if (!available) {
                log.info("락 획득 실패...");
                return;
            }

            stockService.decrease(productId, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
