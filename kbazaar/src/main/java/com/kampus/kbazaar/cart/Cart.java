package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.promotion.Promotion;
import com.kampus.kbazaar.shopper.Shopper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    private Set<CartProduct> cartProducts;

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
        if (!this.promotions.isEmpty()) {
            this.promotions.clear();
        }

        this.promotions.add(promotion);
        promotion.getCarts().add(this);
        return this;
    }
}
