package com.kampus.kbazaar.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {


    @GetMapping("/carts")
    public ResponseEntity getCart() { // NOSONAR
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/carts/{username}/products")
//    public ResponseEntity<AddProductResponse> addProduct(
//            @PathVariable("username") String username,
//            @RequestBody AddProductRequest addProductRequest) {
//
//        Product product = productService.getProductBySku(addProductRequest.getProductSku());
//        if (product.getQuantity() < addProductRequest.getQuantity()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        //        return ResponseEntity.ok(cartService
//        //                .findCartByUsername(username));
//        //                .addProduct(product)
//        //                .toAddProductResponse());
//        return ResponseEntity.ok().build();
//    }
}
