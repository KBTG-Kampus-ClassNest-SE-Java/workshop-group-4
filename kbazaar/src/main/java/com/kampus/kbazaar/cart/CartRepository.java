package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.shopper.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByShopper(Shopper shopper);
}
