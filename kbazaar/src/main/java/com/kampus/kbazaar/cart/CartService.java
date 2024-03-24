package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.exceptions.InternalServerException;
import com.kampus.kbazaar.exceptions.NotFoundException;
import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.product.ProductService;
import com.kampus.kbazaar.shopper.ShopperService;
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
}
