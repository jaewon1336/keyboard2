package com.keyboard2.service;

import com.keyboard2.dto.CartDTO;
import com.keyboard2.dto.CartItemDTO;
import com.keyboard2.dto.CartItemOptionDTO;
import com.keyboard2.entity.*;
import com.keyboard2.repository.CartItemOptionRepository;
import com.keyboard2.repository.CartItemRepository;
import com.keyboard2.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemOptionRepository cartItemOptionRepository;


    @Override
    @Transactional
    public void createCart(CartDTO cartDTO, User user) {
        Optional<Cart> alreadyCartEntity = cartRepository.findCartByUserUserKey(user.getUserKey());

        if (alreadyCartEntity.isPresent()) {
            Cart alreadyCart = alreadyCartEntity.get();
            alreadyCart.setCartTotalPrice(alreadyCart.getCartTotalPrice() + cartDTO.getCartTotalPrice());

            List<CartItem> cartItems = cartDTO.getCartItems().stream()
                    .map(cartItemd -> {
                        CartItem cartItem = dtoToEntity(cartItemd);
                        cartItem.setCart(alreadyCart);
                        return cartItem;
                    }).collect(Collectors.toList());

            List<CartItem> savedCartItems =  cartItemRepository.saveAll(cartItems);

            List<CartItemOption> cartItemOptions = cartDTO.getCartItems().get(0).getCartItemOptions().stream()
                    .map(cartItemOptionDTO -> {
                        CartItemOption cartItemOption = dtoToEntity3(cartItemOptionDTO);
                        cartItemOption.setCartItem(savedCartItems.get(0));
                        return cartItemOption;
                    }).collect(Collectors.toList());


            cartItemOptionRepository.saveAll(cartItemOptions);
        } else {
            Cart cart = Cart.builder()
                    .cartTotalPrice(cartDTO.getCartTotalPrice())
                    .user(user)
                    .build();

            Cart newCart = cartRepository.save(cart);

            List<CartItem> cartItems = cartDTO.getCartItems().stream()
                    .map(cartItemd -> {
                        CartItem cartItem = dtoToEntity(cartItemd);
                        cartItem.setCart(newCart);
                        return cartItem;
                    }).collect(Collectors.toList());

            List<CartItem> savedCartItems =  cartItemRepository.saveAll(cartItems);

            List<CartItemOption> cartItemOptions = cartDTO.getCartItems().get(0).getCartItemOptions().stream()
                    .map(cartItemOptionDTO -> {
                        CartItemOption cartItemOption = dtoToEntity3(cartItemOptionDTO);
                        cartItemOption.setCartItem(savedCartItems.get(0));
                        return cartItemOption;
                    }).collect(Collectors.toList());


            cartItemOptionRepository.saveAll(cartItemOptions);


        }
    }

    @Override
    public CartDTO getCart(Long userKey) {
        Optional<Cart> cartEntity = cartRepository.findCartByUserUserKey(userKey);
        Cart cart = cartEntity.get();

        CartDTO cartDTO = new CartDTO();



        List<CartItem> cartItems = cartItemRepository.findCartItemByCartCartKey(cart.getCartKey());

        System.out.println("cartItems :: " + cartItems);

        List<CartItemDTO> cartItemDTOS = cartItems.stream()
                .map(cartItem -> {
                    List<CartItemOptionDTO> cartItemOptionDTOS = cartItem.getCartItemOptions().stream()
                            .map(this::convertToCartItemOptionDTO)
                            .collect(Collectors.toList());

                    CartItemDTO cartItemDTO = convertToCartItemDTO(cartItem);
                    cartItemDTO.setCartItemOptions(cartItemOptionDTOS);
                    return cartItemDTO;
                })
                .collect(Collectors.toList());

        System.out.println("cartItemDTOS :: " + cartItemDTOS);


        cartDTO.setCartItems(cartItemDTOS);




        return cartDTO;
    }
}
