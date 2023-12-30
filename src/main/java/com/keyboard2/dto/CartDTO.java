package com.keyboard2.dto;

import com.keyboard2.entity.CartItem;
import com.keyboard2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long cartKey;

    private Integer cartTotalPrice;

    private UserDTO user;


    private List<CartItemDTO> cartItems;
}
