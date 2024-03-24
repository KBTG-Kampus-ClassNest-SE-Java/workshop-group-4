package com.kampus.kbazaar.cart;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CartPromotionResponse {

    private List<CartProductResponse> items;
    private BigDecimal actualPrice;
    private BigDecimal discountPrice;
    private BigDecimal totalPrice;
}
