package com.kampus.kbazaar.cart;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Value("${enabled.shipping.fee:true}")
    private boolean enableShippingFee;

    @GetMapping("/carts")
    public ResponseEntity getCart() { // NOSONAR
        return ResponseEntity.ok().build();
    }

    @PostMapping("/carts/{username}/items")
    public CartResponse addProduct(
            @PathVariable("username") String username,
            @RequestBody AddProductRequest addProductRequest) {

        CartResponse cartResponse =
                cartService
                        .addProductByUsernameAndProductSku(
                                username,
                                addProductRequest.getProductSku(),
                                addProductRequest.getQuantity())
                        .toCartResponse();

        if (enableShippingFee) {
            cartResponse.setShippingFee(new BigDecimal(25));
        }

        return cartResponse;
    }
}
