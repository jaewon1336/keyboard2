package com.keyboard2.service;


import com.keyboard2.dto.ItemOptionDTO;
import com.keyboard2.dto.OptionDTO;
import com.keyboard2.dto.OrderDTO2;
import com.keyboard2.entity.ItemOption;
import com.keyboard2.entity.Option;

public interface OptionService {
    public void addOption(OptionDTO optionDTO);
//    public void deleteOption(Long optionKey);
//    public void deleteItemOption(Long itemOptionKey);
    public ItemOptionDTO getOption(Long itemOptionKey);


    default ItemOption dtoToEntity(ItemOptionDTO itemOptionDTO) {
        return ItemOption.builder()
                .optionValue(itemOptionDTO.getOptionValue())
                .option(Option.builder().optionKey(itemOptionDTO.getItemOptionKey())
                        .build())
                .build();
    }


}
