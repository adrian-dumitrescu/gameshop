package com.gamekeys.gameshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "CART_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id", nullable = false, updatable = false)
    private Long id;

    private Integer quantity = 0;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shopping_cart_fk")
    private ShoppingCart shoppingCart;

//    @OneToOne
//    @JoinColumn(name = "product_fk", referencedColumnName = "product_id")
//    //@PrimaryKeyJoinColumn
//    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_fk", nullable = false)
    //@JsonBackReference
    private Product product;


}
