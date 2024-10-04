package com.f1v3.stock.service;

import com.f1v3.stock.domain.Stock;
import com.f1v3.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stock Service.
 *
 * @author 정승조
 * @version 2024. 10. 04.
 */
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

//    @Transactional
    public synchronized void decrease(Long id, Long quantity) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("stock not found"));

        stock.decrease(quantity);

        stockRepository.save(stock);
    }

}
