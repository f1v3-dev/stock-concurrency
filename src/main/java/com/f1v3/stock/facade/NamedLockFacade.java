package com.f1v3.stock.facade;

import com.f1v3.stock.repository.NamedLockRepository;
import com.f1v3.stock.service.NamedLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Named Lock Facade.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Component
@RequiredArgsConstructor
public class NamedLockFacade {

    private final NamedLockRepository namedLockRepository;
    private final NamedLockStockService namedLockStockService;

    public void decrease(Long id, Long quantity) {
        try {
            namedLockRepository.getLock(id.toString());
            namedLockStockService.decrease(id, quantity);
        } finally {
            namedLockRepository.releaseLock(id.toString());
        }
    }
}
