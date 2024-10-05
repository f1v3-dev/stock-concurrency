package com.f1v3.stock.domain;

import jakarta.persistence.*;

/**
 * Stock Entity.
 *
 * @author 정승조
 * @version 2024. 10. 04.
 */
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long quantity;

    @Version
    private Long version; // Optimistic Lock 사용

    public Stock() {
    }

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void decrease(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("out of stock!");
        }

        // 수량 갱신
        this.quantity -= quantity;
    }
}
