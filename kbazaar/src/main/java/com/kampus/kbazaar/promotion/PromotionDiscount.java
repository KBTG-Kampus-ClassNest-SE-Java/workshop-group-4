package com.kampus.kbazaar.promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PromotionDiscount {
    public static BigDecimal getDiscountAmountFromPromotion(
            BigDecimal price, Integer quantity, Promotion promotion) {
        if (promotion == null) {
            return BigDecimal.ZERO;
        }
        return switch (promotion.getDiscountType()) {
            case "FIXED_AMOUNT" -> promotion.getDiscountAmount();
            case "PERCENTAGE" -> price.subtract(
                    price.multiply(promotion.getDiscountAmount())
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            case "buy1_get1" -> {
                if (quantity >= 2) {
                    yield price;
                }
                yield BigDecimal.ZERO;
            }
            case "buy2_get1" -> {
                if (quantity >= 3) {
                    yield price;
                }
                yield BigDecimal.ZERO;
            }
            default -> BigDecimal.ZERO;
        };
    }

    public static BigDecimal divideDiscount(BigDecimal price, Integer divider) {
        return price.divide(BigDecimal.valueOf(divider), 2, RoundingMode.HALF_UP);
    }
}
