package com.f1v3.stock.service;

/**
 * `@Transactional` 어노테이션을 통해 프록시 동작 방식을 이해하고, 트랜잭션 동작 방식을 이해한다.
 *
 * @author 정승조
 * @version 2024. 10. 04.
 */
public class TransactionStockService {

    private StockService stockService;

    public TransactionStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) {

        // start transaction
        startTransaction();

        // call target method
        stockService.decrease(id, quantity);

        // end transaction
        endTransaction();
    }

    private void startTransaction() {
        System.out.println("Transaction started");
    }

    private void endTransaction() {
        System.out.println("Commit");
    }
}
