package com.keyboard2.dto;

import com.keyboard2.entity.*;
import com.keyboard2.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long itemKey;

    private String itemImage;

    private String itemName;

    private Integer itemPrice;

    private String itemDescription;

    private CategoryDTO category;

    private List<OptionDTO> options;



//
//    private List<CartItemDTO> cartItems;
//
//    private List<OrderItemDTO> orderItems;
//
//    private List<OptionDTO> options;
//
//    private List<ImageDTO> images;


}
