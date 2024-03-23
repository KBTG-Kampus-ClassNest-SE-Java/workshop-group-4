package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.promotion.Promotion;
import com.kampus.kbazaar.shopper.Shopper;
import jakarta.persistence.*;
import java.util.Set;
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
    private Set<CartProduct> products;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "cart_promotion",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id"))
    private Set<Promotion> promotions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopper_id", referencedColumnName = "id")
    private Shopper shopper;

    public Cart addPromotion(Promotion promotion) {
        this.promotions.add(promotion);
        promotion.getCarts().add(this);
        return this;
    }
}
