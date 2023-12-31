package com.keyboard2.service;

import com.keyboard2.dto.*;
import com.keyboard2.entity.*;
import com.keyboard2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public void createCart(CartDTO cartDTO, User user) {
        Optional<Cart> alreadyCartEntity = cartRepository.findCartByUserUserId(user.getUserId());

        Optional<Item> itemEntity = itemRepository.findById(cartDTO.getCartItems().get(0).getItem().getItemKey());
        Item item = itemEntity.get();

        System.out.println("itemprice :: " + item.getItemPrice()); //23000

        if (alreadyCartEntity.isPresent()) {
            Integer cartItemPrice = cartDTO.getCartItems().get(0).getItemQty() * item.getItemPrice();
            Cart alreadyCart = alreadyCartEntity.get();
            alreadyCart.setCartTotalPrice(alreadyCart.getCartTotalPrice() + cartItemPrice);

            List<CartItem> cartItems = cartDTO.getCartItems().stream()
                    .map(cartItemd -> {
                        cartItemd.setItemPrice(item.getItemPrice());
                        CartItem cartItem = dtoToEntity(cartItemd);
                        cartItem.setCart(alreadyCart);
                        return cartItem;
                    }).collect(Collectors.toList());

            cartItemRepository.saveAll(cartItems);

        } else {
                Cart cart = Cart.builder()
                        .cartTotalPrice(cartDTO.getCartItems().get(0).getItemQty() * item.getItemPrice())
                        .user(user)
                        .build();

                Cart newCart = cartRepository.save(cart);

                List<CartItem> cartItems = cartDTO.getCartItems().stream()
                        .map(cartItemd -> {
                            cartItemd.setItemPrice(item.getItemPrice());
                            CartItem cartItem = dtoToEntity(cartItemd);
                            cartItem.setCart(newCart);
                            return cartItem;
                        }).collect(Collectors.toList());

                List<CartItem> savedCartItems = cartItemRepository.saveAll(cartItems);

        }
    }


    @Override
    public CartDTO getCart(String userId) {
        Optional<Cart> cartEntity = cartRepository.findCartByUserUserId(userId);
        Cart cart = cartEntity.get();

        CartDTO cartDTO = new CartDTO();

        List<CartItem> cartItems = cartItemRepository.findCartItemByCartCartKey(cart.getCartKey());

//        System.out.println("cartItems :: " + cartItems);

        List<CartItemDTO> cartItemDTOS = cartItems.stream()
                .map(cartItem -> {
                    return convertToCartItemDTO(cartItem);
                }).collect(Collectors.toList());

        cartDTO.setCartItems(cartItemDTOS);


        return cartDTO;
    }
}



