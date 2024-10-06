package com.f1v3.stock.repository;

import com.f1v3.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Named Lock Repository.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
public interface NamedLockRepository extends JpaRepository<Stock, Long> {

    @Query(value = "select get_lock(:key, 1000)", nativeQuery = true)
    void getLock(String key);

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    void releaseLock(String key);
}