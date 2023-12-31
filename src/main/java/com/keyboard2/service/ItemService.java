package com.keyboard2.service;

import com.keyboard2.dto.CategoryDTO;
import com.keyboard2.dto.ItemDTO;
import com.keyboard2.entity.Category;
import com.keyboard2.entity.Item;

import java.util.List;

public interface ItemService {
    public void addItem(ItemDTO itemDTO);
    public List<ItemDTO> getAllItems();

    public ItemDTO getItemDetail(Long itemKey);

    List<Item> findProductByProductOption(String color, String switchType);

    default CategoryDTO mapCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryKey(category.getCategoryKey());
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }

}
