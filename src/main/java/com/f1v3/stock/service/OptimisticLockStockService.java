package com.f1v3.stock.service;

import com.f1v3.stock.domain.Stock;
import com.f1v3.stock.repository.StockLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stock Service With Optimistic Lock.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

    private final StockLockRepository stockRepository;

    @Transactional
    public void decrease(Long productId, Long quantity) {

        // 낙관적 락을 통해 Stock 엔티티를 조회한다.
        Stock stock = stockRepository.findByProductIdWithOptimistic(productId);

        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
