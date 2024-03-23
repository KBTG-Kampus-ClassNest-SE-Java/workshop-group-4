package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.product.ProductService;
import com.kampus.kbazaar.shopper.ShopperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final ShopperService shopperService;
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/carts")
    public ResponseEntity getCart() { // NOSONAR
        return ResponseEntity.ok().build();
    }

    @PostMapping("/carts/{username}/products")
    public ResponseEntity<AddProductResponse> addProduct(
            @PathVariable("username") String username,
            @RequestBody AddProductRequest addProductRequest) {

        Product product = productService.getProductBySku(addProductRequest.getProductSku());
        if (product.getQuantity() < addProductRequest.getQuantity()) {
            return ResponseEntity.badRequest().build();
        }

        //        return ResponseEntity.ok(cartService
        //                .findCartByUsername(username));
        //                .addProduct(product)
        //                .toAddProductResponse());
        return ResponseEntity.ok().build();
    }
}
