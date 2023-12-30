package com.keyboard2.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"orderItem","itemOption"})
@Table(name = "TBL_ORDER_ITEM_OPTION")
public class OrderItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemOptionKey;

    @ManyToOne
    @JoinColumn(name = "ORDER_ITEM_KEY")
    private OrderItem orderItem;

    @ManyToOne
    @JoinColumn(name = "ITEM_OPTION_KEY")
    private ItemOption itemOption;
}
