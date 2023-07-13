package com.keyboard2.dto;

import com.keyboard2.entity.Item;
import com.keyboard2.entity.ItemOption;
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
public class OptionDTO {

    private Long optionKey;

    private String optionName;

    private ItemDTO item;

    private List<ItemOptionDTO> itemOptions;

}
