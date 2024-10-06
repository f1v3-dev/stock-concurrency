package com.f1v3.stock.facade;

import com.f1v3.stock.service.OptimisticLockStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

/**
 * Optimistic Lock Facade.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Slf4j
@Component
public class OptimisticLockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public OptimisticLockFacade(OptimisticLockStockService optimisticLockStockService) {
        this.optimisticLockStockService = optimisticLockStockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {

        while (true) {
            try {
                optimisticLockStockService.decrease(id, quantity);

                break;
            } catch (ObjectOptimisticLockingFailureException e) {
                log.error("{} 발생, 재시도가 필요합니다.", e.getClass().getSimpleName(), e);
                Thread.sleep(50);
            }
        }
    }
}
