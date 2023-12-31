package com.keyboard2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long cartItemKey;

    private Integer itemPrice;

    private Integer itemQty;

    private ItemDTO item;


    private CartDTO cart;

}
