package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.exceptions.InternalServerException;
import com.kampus.kbazaar.exceptions.NotFoundException;
import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.product.ProductService;
import com.kampus.kbazaar.promotion.Promotion;
import com.kampus.kbazaar.shopper.ShopperService;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ShopperService shopperService;
    private final ProductService productService;
    private final CartRepository cartRepository;

    public Cart findCartByUsername(String username) {
        return Optional.of(username)
                .map(shopperService::getShopperByUsername)
                .map(cartRepository::findByShopper)
                .orElseThrow(() -> new NotFoundException("Failed to find cart by username"));
    }

    public Cart addProductByUsernameAndProductSku(
            String username, String productSku, Integer quantity) {
        Product product = productService.getProductBySku(productSku);
        Cart cart = findCartByUsername(username);
        // TODO: add deduct product stock logic here
        return CartProduct.of(product, quantity, cart)
                .map(cart::addProduct)
                .map(cartRepository::save)
                .orElseThrow(
                        () ->
                                new InternalServerException(
                                        "Product out of stock or failed to add product to cart"));
    }

    public CartResponse applyDiscount(String username, Promotion promotion) {
        Cart cart = findCartByUsername(username);
        cart.addPromotion(promotion);
        cartRepository.save(cart);
        return cart.toCartResponse();
    }

    public Optional<Cart> findCartByShopperId(Long shopperId) {
        return this.cartRepository.findByShopperId(shopperId);
    }

    public ArrayList<Product> getProductsByCartId(Long cartId) {
        return cartRepository.findProductsByCartId(cartId);
    }
}
