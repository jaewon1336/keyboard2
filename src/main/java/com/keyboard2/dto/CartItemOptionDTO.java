package com.keyboard2.dto;

import com.keyboard2.entity.CartItem;
import com.keyboard2.entity.ItemOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemOptionDTO {

    private Long cartItemOptionKey;

    private CartItemDTO cartItem;

    private ItemOptionDTO itemOption;
}
