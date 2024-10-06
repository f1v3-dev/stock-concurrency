package com.f1v3.stock.service;

import com.f1v3.stock.domain.Stock;
import com.f1v3.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Named Lock Stock Service.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Service
@RequiredArgsConstructor
public class NamedLockStockService {

    private final StockRepository stockRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decrease(Long productId, Long quantity) {

        Stock stock = stockRepository.findByProductId(productId);
        stock.decrease(quantity);

        stockRepository.save(stock);
    }
}
