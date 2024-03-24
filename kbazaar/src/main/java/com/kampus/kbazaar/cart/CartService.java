package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.exceptions.NotFoundException;
import com.kampus.kbazaar.promotion.CalculateDisCountPriceUtil;
import com.kampus.kbazaar.promotion.Promotion;
import com.kampus.kbazaar.promotion.PromotionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final PromotionRepository promotionRepository;
    private final CalculateDisCountPriceUtil calculatePromotionService;

    public Cart findCartByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    @Transactional
    public CartPromotionResponse applyPromotionToCart(
            String username, ApplyPromotionRequest applyPromotionRequest) {
        Cart cart = findCartByUsername(username);

        if (cart == null) {
            throw new NotFoundException("Cart not found");
        }

        Promotion promotion =
                promotionRepository
                        .findByCode(applyPromotionRequest.code())
                        .orElseThrow(() -> new NotFoundException("Promotion not found"));

        cart.addPromotion(promotion);
        cartRepository.save(cart);
        return calculatePromotionService.calculateDiscountPriceTypeSpecificProduct(
                cart.getCartProducts(), promotion);
    }
}
