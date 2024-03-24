package com.kampus.kbazaar.promotion;

import com.kampus.kbazaar.cart.CartProduct;
import com.kampus.kbazaar.cart.CartProductResponse;
import com.kampus.kbazaar.cart.CartPromotionResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class CalculateDisCountPriceUtil {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    public CartPromotionResponse calculateDiscountPriceTypeSpecificProduct(
            Set<CartProduct> items, Promotion promotion) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal discountPrice = BigDecimal.ZERO;
        BigDecimal actualPrice = BigDecimal.ZERO;

        if (ApplicableToType.SPECIFIC_PRODUCTS.value().equals(promotion.getApplicableTo())
                && StringUtils.isNotBlank(promotion.getProductSkus())) {
            List<String> productSkus = Arrays.asList(promotion.getProductSkus().split(","));

            actualPrice =
                    items.stream()
                            .map(
                                    item ->
                                            calculateOriginalPrice(
                                                    item.getProduct().getPrice(),
                                                    item.getQuantity()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            for (CartProduct item : items) {
                if (productSkus.contains(item.getProduct().getSku())) {
                    if (DiscountType.FIXED_AMOUNT.value().equals(promotion.getDiscountType())) {
                        discountPrice = discountPrice.add(promotion.getDiscountAmount());
                    } else if (DiscountType.PERCENTAGE
                            .value()
                            .equals(promotion.getDiscountType())) {
                        discountPrice =
                                discountPrice.add(
                                        calculateDiscountTypePercentage(
                                                promotion,
                                                item.getProduct().getPrice(),
                                                item.getQuantity()));
                    } else if (Arrays.asList(
                                    DiscountType.BUY1_GET1.value(), DiscountType.BUY2_GET1.value())
                            .contains(promotion.getDiscountType())) {
                        discountPrice =
                                discountPrice.add(
                                        calculateDiscountFreeItems(
                                                item.getQuantity(),
                                                item.getProduct().getPrice(),
                                                promotion.getMinQuantity(),
                                                promotion.getFreeQuantity()));
                    }
                }
            }
            totalPrice =
                    actualPrice.compareTo(discountPrice) > 0
                            ? actualPrice.subtract(discountPrice)
                            : BigDecimal.ZERO;
        }

        List<CartProductResponse> cartProductResponse =
                items.stream().map(CartProduct::toResponse).collect(Collectors.toList());
        CartPromotionResponse response = new CartPromotionResponse();
        response.setItems(cartProductResponse);
        response.setActualPrice(actualPrice);
        response.setDiscountPrice(discountPrice);
        response.setTotalPrice(totalPrice);
        return response;
    }

    private BigDecimal calculateDiscountTypePercentage(
            Promotion promotion, BigDecimal price, int quantity) {
        BigDecimal originalPrice = calculateOriginalPrice(price, quantity);
        BigDecimal discountedPrice = getPercentOf(promotion.getDiscountAmount(), originalPrice);

        if (promotion.getMaxDiscountAmount() == null
                || promotion.getMaxDiscountAmount().compareTo(discountedPrice) > 0) {
            return discountedPrice;
        }
        return promotion.getMaxDiscountAmount();
    }

    private static BigDecimal calculateOriginalPrice(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateDiscountFreeItems(
            int unit, BigDecimal price, int minQuantity, int freeQuantity) {
        if (unit > minQuantity) {
            int freeItems = unit / (minQuantity + freeQuantity);
            return price.multiply(BigDecimal.valueOf(freeItems));
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getPercentOf(BigDecimal percentage, BigDecimal total) {
        return percentage.multiply(total).divide(ONE_HUNDRED, RoundingMode.HALF_UP);
    }
}
