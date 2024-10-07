package com.f1v3.stock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * AOP Proxy Stock Service.
 *
 * @author 정승조
 * @version 2024. 10. 07.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProxyStockService {

    private final SynchronizedStockService stockService;

    public void decrease(Long productId, Long quantity) {

        // 트랜잭션 시작
        startTransaction();

        try {
            // 비즈니스 로직 호출
            stockService.decrease(productId, quantity);

            // 트랜잭션 커밋
            endTransaction();
        } catch (Exception e) {
            // 오류가 발생하면 트랜잭션 롤백
            rollbackTransaction();
        } finally {
            // 리소스 정리
            releaseResources();
        }
    }

    private void startTransaction() {
        // 트랜잭션 시작: 예시로 Connection Pool에서 커넥션을 가져오고, Auto Commit을 false로 설정하는 로직을 가정
        log.info("Transaction Start: Connection acquired, AutoCommit set to false");
    }

    private void endTransaction() {
        // 트랜잭션 커밋
        log.info("Transaction End: Commit!");
    }

    private void rollbackTransaction() {
        // 트랜잭션 롤백
        log.info("Transaction End: Rollback!");
    }

    private void releaseResources() {
        // 리소스 반환: 예시로 Connection을 반환하는 로직을 가정
        log.info("Resources released: Connection returned to pool");
    }
}
