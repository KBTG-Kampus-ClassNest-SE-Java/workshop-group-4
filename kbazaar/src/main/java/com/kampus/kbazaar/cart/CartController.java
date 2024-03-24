package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.shopper.ShopperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final ShopperService shopperService;
    private final CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity getCart() { // NOSONAR
        return ResponseEntity.ok().build();
    }

    @PostMapping("/carts/{username}/products")
    public ResponseEntity<String> addProduct(
            @PathVariable("username") String username,
            @RequestBody AddProductRequest addProductRequest) {

        //          Cart cart = cartService.findCartByUsername(username);
        //        Product product =
        // productService.getProductBySku(addProductRequest.getProductSku());
        //        if (product.getQuantity() < addProductRequest.getQuantity()) {
        //            return ResponseEntity.badRequest().build();
        //        }

        //        return ResponseEntity.ok(cartService
        //                .findCartByUsername(username));
        //                        .addProduct(product)
        //                .toAddProductResponse());

        return ResponseEntity.ok("hey");
    }

    @PostMapping("/carts/{username}/promotions")
    public ResponseEntity applyPromotionToCart(
            @PathVariable("username") String username,
            @RequestBody ApplyPromotionRequest applyPromotionRequest) {
        return ResponseEntity.ok(cartService.applyPromotionToCart(username, applyPromotionRequest));
    }
}
