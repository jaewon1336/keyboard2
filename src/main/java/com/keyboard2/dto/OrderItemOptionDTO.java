package com.keyboard2.dto;

import com.keyboard2.entity.ItemOption;
import com.keyboard2.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOptionDTO {

    private Long orderItemOptionKey;

    private OrderItemDTO orderItem;

    private ItemOptionDTO itemOption;
}
