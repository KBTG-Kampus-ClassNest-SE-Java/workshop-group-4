package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.shopper.ShopperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final ShopperService shopperService;

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
}
