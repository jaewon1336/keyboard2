package com.keyboard2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cartItem","itemOption"})
@Table(name = "TBL_CART_ITEM_OPTION")
public class CartItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemOptionKey;

    @ManyToOne
    @JoinColumn(name = "CART_ITEM_KEY")
    private CartItem cartItem;

    @ManyToOne
    @JoinColumn(name = "ITEM_OPTION_KEY")
    private ItemOption itemOption;

}
