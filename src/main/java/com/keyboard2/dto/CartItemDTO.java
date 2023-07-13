package com.keyboard2.dto;

import com.keyboard2.entity.Cart;
import com.keyboard2.entity.CartItemOption;
import com.keyboard2.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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


    private List<CartItemOptionDTO> cartItemOptions;
}
