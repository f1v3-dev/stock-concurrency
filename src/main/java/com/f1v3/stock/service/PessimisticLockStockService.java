package com.f1v3.stock.service;

import com.f1v3.stock.domain.Stock;
import com.f1v3.stock.repository.StockLockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stock Service With Pessimistic Lock.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

    private final StockLockRepository stockRepository;

    @Transactional
    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdWithPessimistic(productId);

        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
