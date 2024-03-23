package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.Product;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartTemp {
    private int userID;
    private Set<Product> products;

    public CartTemp(int userID, Set<Product> products) {
        this.userID = userID;
        this.products = products;
    }

    public CartTemp addProduct(Product product) {
        products.add(product);
        return this;
    }

    public AddProductResponse toAddProductResponse() {
        return new AddProductResponse(true, "Product added to cart");
    }
}
