package com.f1v3.stock.repository;

import com.f1v3.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Stock JPA Repository.
 *
 * @author 정승조
 * @version 2024. 10. 04.
 */
public interface StockRepository extends JpaRepository<Stock, Long> {
}
