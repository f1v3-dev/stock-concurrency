package com.f1v3.stock.facade;

import com.f1v3.stock.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

/**
 * {class name}.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OptimisticLockFacade {

    private final OptimisticLockStockService stockService;

    public void decrease(Long productId, Long quantity) throws InterruptedException {

        while (true) {
            try {
                stockService.decrease(productId, quantity);

                break;
            } catch (ObjectOptimisticLockingFailureException e) {
                log.info("재고 감소 중 버전 충돌이 발생하였습니다.");
                Thread.sleep(30);
            }
        }
    }
}

