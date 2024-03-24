package com.kampus.kbazaar.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.shopper.username = ?1")
    Cart findByUsername(String username);
}
