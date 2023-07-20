package com.keyboard2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO2 {

    private String shippingAddress;

    private String orderName;

    private String orderPhone;

    private Long itemKey;

    private Integer itemQty;

    private List<Long> itemOptionKey;
}
