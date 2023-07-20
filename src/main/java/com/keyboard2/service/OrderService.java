package com.keyboard2.service;

import com.keyboard2.dto.OrderDTO2;
import com.keyboard2.entity.User;

public interface OrderService {
    public void createOrder(OrderDTO2 orderDTO2, User user);
}
