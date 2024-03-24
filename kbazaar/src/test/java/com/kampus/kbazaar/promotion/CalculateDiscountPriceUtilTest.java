package com.kampus.kbazaar.promotion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.kampus.kbazaar.cart.CartProduct;
import com.kampus.kbazaar.cart.CartPromotionResponse;
import com.kampus.kbazaar.product.Product;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalculateDiscountPriceUtilTest {

    @InjectMocks private CalculateDisCountPriceUtil calculatePromotionService;

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    @Test
    @DisplayName(
            "test calculate discount price applicable to SPECIFIC_PRODUCTS and discount type FixedAmount when match 1 item")
    void
            testCalculateDiscountPriceApplicableToSpecificProductAndDiscountTypeFixedAmountAndMatch1Item() {
        Set<CartProduct> items = new HashSet<>();
        items.add(
                CartProduct.builder()
                        .product(Product.builder().sku("TEST1").price(ONE_HUNDRED).build())
                        .quantity(2)
                        .build());

        items.add(
                CartProduct.builder()
                        .product(Product.builder().sku("TEST2").price(ONE_HUNDRED).build())
                        .quantity(1)
                        .build());

        Promotion promotion =
                Promotion.builder()
                        .code("FIXEDAMOUNT10")
                        .discountType(DiscountType.FIXED_AMOUNT.value())
                        .applicableTo(ApplicableToType.SPECIFIC_PRODUCTS.value())
                        .productSkus("TEST1")
                        .discountAmount(BigDecimal.TEN)
                        .build();

        CartPromotionResponse response =
                calculatePromotionService.calculateDiscountPriceTypeSpecificProduct(
                        items, promotion);
        assertEquals(BigDecimal.TEN, response.getDiscountPrice());
    }

    @Test
    @DisplayName(
            "test calculate discount price applicable to SPECIFIC_PRODUCTS and discount type FixedAmount when match 0 item")
    void
            testCalculateDiscountPriceApplicableToSpecificProductAndDiscountTypeFixedAmountAndMatch0Item() {
        Set<CartProduct> items = new HashSet<>();
        items.add(
                CartProduct.builder()
                        .product(Product.builder().sku("TEST1").price(ONE_HUNDRED).build())
                        .quantity(2)
                        .build());

        Promotion promotion =
                Promotion.builder()
                        .code("FIXEDAMOUNT10")
                        .discountType(DiscountType.FIXED_AMOUNT.value())
                        .applicableTo(ApplicableToType.SPECIFIC_PRODUCTS.value())
                        .productSkus("ABC")
                        .discountAmount(BigDecimal.TEN)
                        .build();

        CartPromotionResponse response =
                calculatePromotionService.calculateDiscountPriceTypeSpecificProduct(
                        items, promotion);
        assertEquals(BigDecimal.ZERO, response.getDiscountPrice());
    }

    @ParameterizedTest
    @MethodSource("getCaseForCalculateDiscountPercentage")
    @DisplayName(
            "test calculate discount price applicable to SPECIFIC_PRODUCTS and discount type PERCENTAGE")
    void testCalculateDiscountPriceApplicableToSpecificProductAndDiscountTypePercentage(
            BigDecimal discountAmount, BigDecimal maxDiscountAmount, BigDecimal discountPrice) {
        Set<CartProduct> items = new HashSet<>();
        items.add(
                CartProduct.builder()
                        .product(Product.builder().sku("TEST1").price(ONE_HUNDRED).build())
                        .quantity(1)
                        .build());

        Promotion promotion =
                Promotion.builder()
                        .code("SPECIFICPRODUCT30OFF")
                        .discountType(DiscountType.PERCENTAGE.value())
                        .applicableTo(ApplicableToType.SPECIFIC_PRODUCTS.value())
                        .productSkus("TEST1")
                        .discountAmount(discountAmount)
                        .maxDiscountAmount(maxDiscountAmount)
                        .build();

        CartPromotionResponse response =
                calculatePromotionService.calculateDiscountPriceTypeSpecificProduct(
                        items, promotion);
        assertEquals(discountPrice, response.getDiscountPrice());
    }

    static Stream<Arguments> getCaseForCalculateDiscountPercentage() {
        BigDecimal DISCOUNT_30 = BigDecimal.valueOf(30);
        return Stream.of(
                arguments(DISCOUNT_30, null, DISCOUNT_30),
                arguments(DISCOUNT_30, BigDecimal.ZERO, BigDecimal.ZERO),
                arguments(DISCOUNT_30, BigDecimal.valueOf(200), DISCOUNT_30),
                arguments(DISCOUNT_30, BigDecimal.TEN, BigDecimal.TEN));
    }

    @ParameterizedTest
    @MethodSource("getCaseForCalculateDiscountFreeItem")
    @DisplayName("test calculate discount price applicable to SPECIFIC_PRODUCTS and type Free Item")
    void testCalculateDiscountPriceApplicableToSpecificProductAndDiscountTypeFreeItem(
            int unit,
            BigDecimal price,
            int minQuantity,
            int freeQuantity,
            BigDecimal discountPrice) {
        Set<CartProduct> items = new HashSet<>();
        items.add(
                CartProduct.builder()
                        .product(Product.builder().sku("TEST1").price(price).build())
                        .quantity(unit)
                        .build());

        Promotion promotion =
                Promotion.builder()
                        .code("BUY1GET1")
                        .discountType(DiscountType.BUY1_GET1.value())
                        .applicableTo(ApplicableToType.SPECIFIC_PRODUCTS.value())
                        .productSkus("TEST1")
                        .minQuantity(minQuantity)
                        .freeQuantity(freeQuantity)
                        .build();

        CartPromotionResponse response =
                calculatePromotionService.calculateDiscountPriceTypeSpecificProduct(
                        items, promotion);
        assertEquals(discountPrice, response.getDiscountPrice());
    }

    static Stream<Arguments> getCaseForCalculateDiscountFreeItem() {
        return Stream.of(
                arguments(1, ONE_HUNDRED, 1, 1, BigDecimal.ZERO),
                arguments(2, ONE_HUNDRED, 1, 1, ONE_HUNDRED),
                arguments(3, ONE_HUNDRED, 1, 1, ONE_HUNDRED),
                arguments(2, ONE_HUNDRED, 2, 1, BigDecimal.ZERO),
                arguments(3, ONE_HUNDRED, 2, 1, ONE_HUNDRED),
                arguments(4, ONE_HUNDRED, 2, 1, ONE_HUNDRED),
                arguments(4, ONE_HUNDRED, 3, 1, ONE_HUNDRED));
    }
}
