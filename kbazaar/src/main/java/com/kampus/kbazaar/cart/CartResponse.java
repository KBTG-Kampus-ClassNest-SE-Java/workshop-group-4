package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.promotion.Promotion;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartResponse {
    private Long cartId;
    private List<Item> items;
    private List<String> promotionCodes;
    private BigDecimal totalCost;
    private BigDecimal entireCartPromotionDiscount;
    private BigDecimal finalTotalCost;

    public static CartResponse fromCart(Cart cart) {
        List<Item> items = cart.getCartProducts().stream().map(CartResponse::initItem).toList();

        return CartResponse.builder()
                .cartId(cart.getId())
                .items(items)
                .promotionCodes(cart.getPromotions().stream().map(Promotion::getCode).toList())
                .totalCost(cart.getTotalCost())
                .entireCartPromotionDiscount(cart.getEntireCartDiscountAmount())
                .finalTotalCost(cart.getFinalTotalCost())
                .build();
    }

    private static Item initItem(CartProduct cartProduct) {
        return new Item(
                cartProduct.getId(),
                cartProduct.getProduct().getName(),
                cartProduct.getQuantity(),
                cartProduct.getPrice(),
                cartProduct.getDiscountAmount(),
                cartProduct.getFinalPrice(),
                Optional.ofNullable(cartProduct.getPromotion()).map(Promotion::getCode).orElse(""));
    }
}

record Item(
        Long id,
        String name,
        Integer quantity,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal finalPrice,
        String promotionCode) {}
