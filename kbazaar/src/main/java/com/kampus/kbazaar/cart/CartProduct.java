package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartProductResponse toResponse() {
        return new CartProductResponse(
                this.product.getSku(),
                this.product.getName(),
                this.quantity,
                this.product.getPrice());
    }
}
