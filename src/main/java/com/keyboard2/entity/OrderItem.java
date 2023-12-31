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
@ToString(exclude = {})
@Table(name = "TBL_ORDER_ITEM")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemKey;

    private Integer itemQty;

    private Integer itemPrice;

    @ManyToOne
    @JoinColumn(name = "ITEM_KEY")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "ORDER_KEY")
    private Order order;


}

