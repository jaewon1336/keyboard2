package com.keyboard2.entity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cart", "item"})
@Table(name = "TBL_CART_ITEM")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemKey;

    private Integer itemPrice;

    private Integer itemQty;

    @ManyToOne
    @JoinColumn(name = "ITEM_KEY")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "CART_KEY")
    private Cart cart;

}

