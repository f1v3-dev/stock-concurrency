package com.f1v3.stock.repository;

import com.f1v3.stock.domain.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

/**
 * Stock Repository (DB Locking).
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
public interface StockLockRepository extends JpaRepository<Stock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.productId = :productId")
    Stock findByProductIdWithPessimistic(Long productId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.productId = :productId")
    Stock findByProductIdWithOptimistic(Long productId);

}
