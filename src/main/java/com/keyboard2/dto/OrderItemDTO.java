package com.keyboard2.dto;

import com.keyboard2.entity.Item;
import com.keyboard2.entity.Order;
import com.keyboard2.entity.OrderItemOption;
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
public class OrderItemDTO {

    private Long orderItemKey;

    private Integer itemQty;

    private Integer itemPrice;

    private ItemDTO item;

    private OrderDTO order;

    private List<OrderItemOptionDTO> orderItemOptions;
}
