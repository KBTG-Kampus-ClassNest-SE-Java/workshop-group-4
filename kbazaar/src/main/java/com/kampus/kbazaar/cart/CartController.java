package com.kampus.kbazaar.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity getCart() { // NOSONAR
        return ResponseEntity.ok().build();
    }

    @PostMapping("/carts/{username}/items")
    public CartResponse addProduct(
            @PathVariable("username") String username,
            @RequestBody AddProductRequest addProductRequest) {

        return cartService
                .addProductByUsernameAndProductSku(
                        username,
                        addProductRequest.getProductSku(),
                        addProductRequest.getQuantity())
                .toCartResponse();
    }
}
