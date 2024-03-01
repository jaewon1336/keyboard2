package com.keyboard2.dto;

import com.keyboard2.entity.*;
import com.keyboard2.entity.*;
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
public class ItemDTO {

    private Long itemKey;

    private String itemImage;

    private String itemName;

    private Integer itemPrice;

    private String itemDescription;

    private String color;

    private String switchType;

    private CategoryDTO category;

    private PageDTO pageDTO;


}
