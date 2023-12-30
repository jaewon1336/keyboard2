package com.keyboard2.dto;

import com.keyboard2.entity.OrderItem;
import com.keyboard2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderKey;

    private LocalDate orderDate;

    private Integer orderTotalPrice;

    private String shippingAddress;

    private String orderName;

    private String orderPhone;

    private UserDTO user;

    private List<OrderItemDTO> orderItems;
}
