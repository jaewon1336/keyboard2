package com.keyboard2.dto;

import com.keyboard2.entity.CartItem;
import com.keyboard2.entity.CartItemOption;
import com.keyboard2.entity.Option;
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
public class ItemOptionDTO {

    private Long itemOptionKey;

    private String optionValue;

    private OptionDTO option;

//    private List<CartItemOptionDTO> cartItemOptions;




}
