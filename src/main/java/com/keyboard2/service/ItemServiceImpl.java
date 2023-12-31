package com.keyboard2.service;

import com.keyboard2.dto.*;
import com.keyboard2.entity.*;
import com.keyboard2.repository.CategoryRepository;
import com.keyboard2.repository.ItemRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class ItemServiceImpl implements ItemService{


    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;


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
    public ItemDTO getItemDetail(Long itemKey) {

        Optional<Item> item = itemRepository.findById(itemKey);

        ItemDTO itemDTO = ItemDTO.builder()
                .itemKey(item.get().getItemKey())
                .itemName(item.get().getItemName())
                .itemPrice(item.get().getItemPrice())
                .itemDescription(item.get().getItemDescription())
                .itemImage(item.get().getItemImage())
                .category(CategoryDTO.builder()
                        .categoryKey(item.get().getCategory().getCategoryKey())
                        .categoryName(item.get().getCategory().getCategoryName())
                        .build())
                .build();

        return itemDTO;
    }

    BooleanBuilder builder = new BooleanBuilder();
    QItem qItem = QItem.item;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public List<Item> findProductByProductOption(String color, String switchType) {

        return jpaQueryFactory
                .select(qItem)
                .from(qItem)
                .where(eqColor(color),
                        eqSwitchType(switchType))
                .fetch();
    }

    private Predicate eqSwitchType(String switchType) {
        if (StringUtils.isEmpty(switchType)) {
            return null;
        }
        return qItem.switchType.eq(switchType);
    }

    private BooleanExpression eqColor(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return qItem.color.eq(name);
    }


}
