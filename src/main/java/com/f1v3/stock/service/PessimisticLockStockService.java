package com.f1v3.stock.service;

import com.f1v3.stock.domain.Stock;
import com.f1v3.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Pessimistic Lock Service.
 *
 * @author 정승조
 * @version 2024. 10. 05.
 */
@Service
public class PessimisticLockStockService {

    private final StockRepository stockRepository;

    public PessimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {

        Stock stock = stockRepository.findByIdWithPessimisticLock(id);

        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
