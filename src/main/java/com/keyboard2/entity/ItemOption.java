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
@Table(name = "TBL_ITEM_OPTION")
@ToString(exclude = {"option", "cartItems"})
public class ItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemOptionKey;

    private String optionValue;

    @ManyToOne
    @JoinColumn(name = "OPTION_KEY")
    private Option option;


    @OneToMany(mappedBy = "itemOption", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CartItemOption> cartItemOptions = new ArrayList<>();



}
