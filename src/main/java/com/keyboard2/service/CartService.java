package com.keyboard2.service;


import com.keyboard2.dto.*;
import com.keyboard2.entity.*;

import java.util.List;

public interface CartService  {
    public void createCart(CartDTO cartDTO, User user);
    public CartDTO getCart(Long userKey);

    default CartItem dtoToEntity(CartItemDTO cartItemDTO) {

        return CartItem.builder()
                .itemPrice(cartItemDTO.getItemPrice())
                .itemQty(cartItemDTO.getItemQty())
                .cart(Cart.builder()
                        .cartKey(cartItemDTO.getCartItemKey())
                        .build())
                .item(Item.builder()
                        .itemKey(cartItemDTO.getItem().getItemKey())
                        .build())
                .build();
    }


    default CartItemOption dtoToEntity3(CartItemOptionDTO cartItemOptionDTO) {

        return CartItemOption.builder()
//                .cartItem(CartItem.builder()
//                        .cartItemKey(cartItemOptionDTO.getCartItem().getCartItemKey())
//                        .build())
                .itemOption(ItemOption.builder()
                        .itemOptionKey(cartItemOptionDTO.getItemOption().getItemOptionKey())
                        .build())
                .build();
    }



    default CartItemOptionDTO convertToCartItemOptionDTO(CartItemOption cartItemOption) {
        CartItemOptionDTO cartItemOptionDTO = new CartItemOptionDTO();
        cartItemOptionDTO.setCartItemOptionKey(cartItemOption.getCartItemOptionKey());
        // 추가적인 필드가 있다면 DTO에 설정

        return cartItemOptionDTO;
    }

    default CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemKey(cartItem.getCartItemKey());
        cartItemDTO.setItemPrice(cartItem.getItemPrice());
        cartItemDTO.setItemQty(cartItem.getItemQty());
        cartItemDTO.setItem(ItemDTO.builder()
                        .itemKey(cartItem.getItem().getItemKey())
                        .itemName(cartItem.getItem().getItemName())
                        .itemImage(cartItem.getItem().getItemImage())
                        .itemPrice(cartItem.getItem().getItemPrice())
//                        .options()를 설정할수 있을까?
                .build());
        // 추가적인 필드가 있다면 DTO에 설정

        return cartItemDTO;
    }








}
