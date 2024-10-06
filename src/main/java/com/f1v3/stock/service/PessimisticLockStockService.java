package com.f1v3.stock.service;

import com.f1v3.stock.domain.Stock;
import com.f1v3.stock.repository.DbLockStockRepository;
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
public class PessimisticLockStockService {

    private final DbLockStockRepository stockRepository;

    @Transactional
    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdWithPessimistic(productId);

        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
