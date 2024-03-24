package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.shopper.Shopper;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByShopper(Shopper shopper);

    Optional<Cart> findByShopperId(Long shopperId);

    @Query("SELECT c.cartProducts FROM Cart c WHERE c.id = :cartId")
    ArrayList<Product> findProductsByCartId(@Param("cartId") Long cartId);
}
