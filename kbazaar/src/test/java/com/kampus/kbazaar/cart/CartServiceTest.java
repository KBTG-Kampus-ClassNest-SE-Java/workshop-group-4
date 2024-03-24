package com.kampus.kbazaar.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.promotion.Promotion;
import com.kampus.kbazaar.shopper.ShopperService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class CartServiceTest {

    @Mock private CartRepository cartRepository;

    @Spy @InjectMocks private CartService underTest;

    @Mock private ShopperService shopperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should be able to apply discount")
    void shouldBeAbleToApplyDiscount() {
        String username = "TechNinja";
        LocalDateTime ldt = LocalDateTime.now();

        Promotion promotion = new Promotion();
        promotion.setPromotionId(1L);
        promotion.setCode("FIXEDAMOUNT10");
        promotion.setName("Fixed Amount $10 Off Entire Cart");
        promotion.setDescription("Get $10 off on your entire cart purchase.");
        promotion.setStartDate(ldt);
        promotion.setEndDate(ldt);
        promotion.setDiscountType("FIXED_AMOUNT");
        promotion.setDiscountAmount(BigDecimal.valueOf(10.00));
        promotion.setMaxDiscountAmount(BigDecimal.valueOf(10.00));
        promotion.setApplicableTo("ENTIRE_CART");
        promotion.setProductSkus("");
        promotion.setMinQuantity(null);
        promotion.setFreeQuantity(null);
        Product product1 =
                new Product(
                        1L,
                        "Apple iPhone 12 Pro",
                        "MOBILE-APPLE-IPHONE-12-PRO",
                        BigDecimal.valueOf(10010),
                        1);
        Cart cart = new Cart();
        CartProduct cartProduct = new CartProduct(1L, product1, null, cart, 1);
        cart.addProduct(cartProduct);

        BigDecimal expectFinalTotalCost = BigDecimal.valueOf(10000.0);

        doReturn(cart).when(underTest).findCartByUsername(Mockito.any());
        CartResponse cartResponse = underTest.applyDiscount(username, promotion);

        assertEquals(expectFinalTotalCost, cartResponse.getFinalTotalCost());
        assertEquals(promotion.getCode(), cartResponse.getPromotionCodes().get(0));
    }
}
