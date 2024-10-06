package com.f1v3.stock.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Stock Entity.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Getter
@Entity
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long quantity;

    @Version
    private Long version;

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void decrease(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        this.quantity -= quantity;
    }
}
