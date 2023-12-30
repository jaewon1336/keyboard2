package com.keyboard2.service;

import com.keyboard2.dto.*;
import com.keyboard2.entity.Category;
import com.keyboard2.entity.Item;
import com.keyboard2.entity.ItemOption;
import com.keyboard2.repository.CategoryRepository;
import com.keyboard2.repository.ItemOptionRepository;
import com.keyboard2.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService{



    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemOptionRepository itemOptionRepository;

    @Override
    public void addItem(ItemDTO itemDTO) {

        Optional<Category> category = categoryRepository.findById(itemDTO.getCategory().getCategoryKey());

        Item item = Item.builder()
                .itemName(itemDTO.getItemName())
                .itemPrice(itemDTO.getItemPrice())
                .itemDescription(itemDTO.getItemDescription())
                .itemImage(itemDTO.getItemImage())
                .category(category.get())
                .build();

        itemRepository.save(item);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(entity -> {
            ItemDTO itemDTO = ItemDTO.builder()
                    .itemKey(entity.getItemKey())
                    .itemName(entity.getItemName())
                    .itemPrice(entity.getItemPrice())
                    .itemDescription(entity.getItemDescription())
                    .itemImage(entity.getItemImage())
                    .build();

            itemDTO.setCategory(mapCategoryToDTO(entity.getCategory()));
            return itemDTO;
        }).collect(Collectors.toList());
    }


    @Override
    public List<ItemDTO> getItemByOption(String optionValue) {
        List<ItemOption> itemOptions = itemOptionRepository.findByOptionValue(optionValue);
//        ItemOption itemOption = itemOptionRepository.findByOptionValue(optionValue);


//        return ItemDTO.builder()
//                .itemName(itemOption.getOption().getItem().getItemName())
//                .itemPrice(itemOption.getOption().getItem().getItemPrice())
//                .itemDescription(itemOption.getOption().getItem().getItemDescription())
//                .itemImage(itemOption.getOption().getItem().getItemImage())
//                .build();

        return itemOptions.stream().map(entity -> {
            ItemDTO itemDTO = ItemDTO.builder()
                    .itemName(entity.getOption().getItem().getItemName())
                    .itemPrice(entity.getOption().getItem().getItemPrice())
                    .itemDescription(entity.getOption().getItem().getItemDescription())
                    .itemImage(entity.getOption().getItem().getItemImage())
                    .build();
            return itemDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ItemDTO> getItemQuerydsl(String testColor, String testSwitch) {

        ItemOption itemOption = new ItemOption();

        return null;
    }


//    @Override
//    public List<ItemDTO> getAllItems() {
//        List<Item> items = itemRepository.findAll();
//        return items.stream().map(entity -> ItemDTO.builder()
//                        .itemKey(entity.getItemKey())
//                        .itemName(entity.getItemName())
//                        .itemPrice(entity.getItemPrice())
//                        .itemDescription(entity.getItemDescription())
//                        .itemImage(entity.getItemImage())
////                        .category(entity.getCategory())
////                        .categoryKey(entity.getCategory().getCategoryKey())
////                        .categoryName(entity.getCategory().getCategoryName())
////                        .options(entity.getOptions())
//                .build())
//                .collect(Collectors.toList());
//
//    }

    @Override
    public ItemDTO getItemDetail(Long itemKey) {
        Optional<Item> itemEntity = itemRepository.findById(itemKey);
        Item item = itemEntity.get();


        List<OptionDTO> optionDTOList = item.getOptions().stream()
                .map(option -> {
                    OptionDTO optionDTO = OptionDTO.builder()
                            .optionKey(option.getOptionKey())
                            .optionName(option.getOptionName())
                            .build();
                    optionDTO.setItemOptions(option.getItemOptions().stream().map(
                            itemOption -> ItemOptionDTO.builder()
                                    .itemOptionKey(itemOption.getItemOptionKey())
                                    .optionValue(itemOption.getOptionValue())
//                                    .option(itemOption.getOption())
//                                    .cartItemOptions(itemOption.getCartItemOptions())
                                    .build()
                    ).collect(Collectors.toList()));
                    return optionDTO;
                })
                .collect(Collectors.toList());


        return ItemDTO.builder()
                .itemKey(item.getItemKey())
                .itemImage(item.getItemImage())
                .itemName(item.getItemName())
                .itemDescription(item.getItemDescription())
                .itemPrice(item.getItemPrice())
                .category(CategoryDTO.builder()
                        .categoryKey(item.getCategory().getCategoryKey())
                        .categoryName(item.getCategory().getCategoryName())
                        .build())
                .options(optionDTOList)
//                .options(item.getOptions().stream().map(
//                        option -> OptionDTO.builder()
//                                .optionKey(option.getOptionKey())
//                                .optionName(option.getOptionName())
//                                .itemOptions()
//                                .build()
//                ).collect(Collectors.toList()))

                .build();
    }

}
