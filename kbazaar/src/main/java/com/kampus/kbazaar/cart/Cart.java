package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.promotion.Promotion;
import com.kampus.kbazaar.promotion.PromotionDiscount;
import com.kampus.kbazaar.shopper.Shopper;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "cart_promotion",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id"))
    private Set<Promotion> promotions = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopper_id", referencedColumnName = "id")
    private Shopper shopper;

    public BigDecimal getEntireCartDiscountAmount() {
        BigDecimal totalCost = this.getTotalCost();
        Integer allProductQuantity = this.getAllProductQuantity();
        return this.getPromotions().stream()
                .filter(prom -> prom.getApplicableTo().equals("ENTIRE_CART"))
                .reduce(
                        BigDecimal.ZERO,
                        (acc, prom) ->
                                acc.add(
                                        PromotionDiscount.getDiscountAmountFromPromotion(
                                                totalCost, allProductQuantity, prom)),
                        BigDecimal::add);
    }

    public Integer getAllProductQuantity() {
        return this.getCartProducts().size();
    }

    public BigDecimal getTotalCost() {
        return this.getCartProducts().stream()
                .map(CartProduct::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getFinalTotalCost() {
        return this.getTotalCost().subtract(this.getEntireCartDiscountAmount());
    }

    public Cart addPromotion(Promotion promotion) {
        this.promotions.add(promotion);
        promotion.getCarts().add(this);
        return this;
    }

    public Optional<CartProduct> addPromotionToProduct(Promotion promotion, String targetSku) {
        Optional<CartProduct> cartProduct =
                this.getCartProducts().stream()
                        .filter(filtered -> filtered.getProduct().getSku().equals(targetSku))
                        .findFirst();

        cartProduct.ifPresent(cp -> cp.setPromotion(promotion));
        return cartProduct;
    }

    public Cart addProduct(CartProduct cartProduct) {
        Optional<CartProduct> existingCartProduct =
                this.cartProducts.stream()
                        .filter(
                                filtered ->
                                        filtered.getProduct()
                                                .getId()
                                                .equals(cartProduct.getProduct().getId()))
                        .findFirst();

        if (existingCartProduct.isPresent()) {
            CartProduct existingCartProductFirst = existingCartProduct.get();
            existingCartProductFirst.setQuantity(
                    existingCartProductFirst.getQuantity() + cartProduct.getQuantity());
            return this;
        }

        cartProduct.setCart(this);
        this.cartProducts.add(cartProduct);
        return this;
    }

    public CartResponse toCartResponse() {
        return CartResponse.fromCart(this);
    }
}
