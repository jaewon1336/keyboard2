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
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemOptionRepository cartItemOptionRepository;

    @Autowired
    private ItemOptionRepository itemOptionRepository;

    @Autowired
    private ItemRepository itemRepository;


    @Override
    @Transactional
    public void createCart(CartDTO cartDTO, User user) {
        Optional<Cart> alreadyCartEntity = cartRepository.findCartByUserUserKey(user.getUserKey());

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

//        System.out.println("cartItems :: " + cartItems);

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

//        System.out.println("cartItemDTOS :: " + cartItemDTOS.get(0).getCartItemOptions().get(0).getCartItemOptionKey());

//        List<String> optionValues = new ArrayList<>();
        for (CartItemDTO cartItemDTO : cartItemDTOS) {
            for (CartItemOptionDTO cartItemOptionDTO : cartItemDTO.getCartItemOptions()) {
                Optional<CartItemOption> cartItemOptions = cartItemOptionRepository.findById(cartItemOptionDTO.getCartItemOptionKey());
//                System.out.println("cartItemOptions :: " + cartItemOptions.get().getItemOption().getOptionValue());
//                optionValues.add(cartItemOptions.get().getItemOption().getOptionValue());
                ItemOptionDTO itemOptionDTO = ItemOptionDTO.builder()
                        .optionValue(cartItemOptions.get().getItemOption().getOptionValue())
                        .build();
                cartItemOptionDTO.setItemOption(itemOptionDTO);
                System.out.println("cartItemOptionDTO :: " + cartItemOptionDTO);
            }
        }



//        for (CartItemDTO cartItemDTO : cartItemDTOS) {
//            List<CartItemOptionDTO> cartItemOptions = new ArrayList<>();
//            for (CartItemOptionDTO cartItemOptionDTO : cartItemDTO.getCartItemOptions()) {
//                // 각 CartItemOptionDTO에 대한 조회 로직을 구현하여 가져옴
//                CartItemOptionDTO retrievedCartItemOptionDTO = retrieveCartItemOptionDTO(cartItemOptionDTO.getCartItemOptionKey());
//                cartItemOptions.add(retrievedCartItemOptionDTO);
//            }
//            cartItemDTO.setCartItemOptions(cartItemOptions);
//        }

        cartDTO.setCartItems(cartItemDTOS);


        return cartDTO;
    }
}
