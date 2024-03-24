package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.promotion.Promotion;
import com.kampus.kbazaar.promotion.PromotionDiscount;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "quantity")
    private Integer quantity;

    public BigDecimal getDiscountAmount() {
        return PromotionDiscount.getDiscountAmountFromPromotion(
                product.getPrice(), quantity, promotion);
    }

    public BigDecimal getPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getFinalPrice() {
        return this.getPrice().subtract(this.getDiscountAmount());
    }

    public static Optional<CartProduct> of(Product product, Integer quantity, Cart cart) {
        if (product.getQuantity() < quantity) {
            return Optional.empty();
        }
        return Optional.of(
                CartProduct.builder().product(product).quantity(quantity).cart(cart).build());
    }
}
