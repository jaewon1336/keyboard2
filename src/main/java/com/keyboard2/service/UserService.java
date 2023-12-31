package com.keyboard2.service;

import com.keyboard2.dto.*;
import com.keyboard2.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public interface UserService {
    public void UserRegister(UserDTO userDTO);
    public UserDTO UserLogin(UserDTO userDTO);

    public User getUser(String userId);

    public UserDTO getUserDTO(String userId);

    default OrderDTO entityToDto(Order order) {
        List<OrderItemDTO> orderItemDTOList = order.getOrderItems().stream()
                .map(this::entityToDtoOrderItem)
                .collect(Collectors.toList());


        return OrderDTO.builder()
                .orderKey(order.getOrderKey())
                .orderTotalPrice(order.getOrderTotalPrice())
                .orderDate(order.getOrderDate())
                .orderName(order.getOrderName())
                .shippingAddress(order.getShippingAddress())
                .orderPhone(order.getOrderPhone())
                .orderItems(orderItemDTOList)
                .build();
    }

    default OrderItemDTO entityToDtoOrderItem(OrderItem orderItem) {


        return OrderItemDTO.builder()
                .itemPrice(orderItem.getItemPrice())
                .itemQty(orderItem.getItemQty())
                .item(ItemDTO.builder()
                        .itemKey(orderItem.getItem().getItemKey())
                        .itemImage(orderItem.getItem().getItemImage())
                        .itemPrice(orderItem.getItem().getItemPrice())
                        .itemName(orderItem.getItem().getItemName())
                        .build())
                .build();
    }



}
