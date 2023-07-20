package com.keyboard2.service;

import com.keyboard2.dto.OrderDTO2;
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
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemOptionRepository orderItemOptionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemOptionRepository itemOptionRepository;

    @Override
    @Transactional
    public void createOrder(OrderDTO2 orderDTO2, User user) {
        Optional<Item> optionalItem = itemRepository.findById(orderDTO2.getItemKey());

        Order order = Order.builder()
                .shippingAddress(orderDTO2.getShippingAddress())
                .orderName(orderDTO2.getOrderName())
                .orderPhone(orderDTO2.getOrderPhone())
                .orderTotalPrice(optionalItem.get().getItemPrice() * orderDTO2.getItemQty())
                .user(user)
                .build();

        Order savedOrder = orderRepository.save(order);



        OrderItem orderItem = OrderItem.builder()
                .itemPrice(optionalItem.get().getItemPrice() * orderDTO2.getItemQty())
                .itemQty(orderDTO2.getItemQty())
                .item(optionalItem.get())
                .order(savedOrder)
                .build();

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        List<ItemOption> itemOptionList = new ArrayList<>();
        for (Long itemOptionKey : orderDTO2.getItemOptionKey()) {
            Optional<ItemOption> itemOption = itemOptionRepository.findById(itemOptionKey);
            itemOptionList.add(itemOption.get());
        }


        List<OrderItemOption> orderItemOptions = itemOptionList.stream()
                .map(itemOption -> OrderItemOption.builder()
                        .orderItem(savedOrderItem)
                        .itemOption(itemOption)
                        .build())
                .collect(Collectors.toList());

        orderItemOptionRepository.saveAll(orderItemOptions);

    }
}
