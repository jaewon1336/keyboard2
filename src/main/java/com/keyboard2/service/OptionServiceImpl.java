package com.keyboard2.service;

import com.keyboard2.dto.OptionDTO;
import com.keyboard2.entity.Item;
import com.keyboard2.entity.ItemOption;
import com.keyboard2.entity.Option;
import com.keyboard2.repository.ItemOptionRepository;
import com.keyboard2.repository.ItemRepository;
import com.keyboard2.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionServiceImpl implements OptionService{

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ItemOptionRepository itemOptionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Transactional
    public void addOption(OptionDTO optionDTO) {

        /* 우선 option을 저장함 */
        Option option = Option.builder()
                .optionName(optionDTO.getOptionName())
                .item(Item.builder()
                        .itemKey(optionDTO.getItem().getItemKey())
                        .build())
                .build();

        /* 저장된 option 객체를 가져옴 */
        Option savedOption =  optionRepository.save(option);

        /* optionDTO에 담겨있는 itemOptions 리스트에 있는 각각의 itemOption dto를 엔티티로 변환  */
        List<ItemOption> itemOptions = optionDTO.getItemOptions().stream()
                .map(itemd -> {
                    ItemOption itemOption = dtoToEntity(itemd);
                    itemOption.setOption(savedOption); // itemOption 객체의 옵션을 저장되어있던 옵션으로 set
                    return itemOption;
                }).collect(Collectors.toList());

        itemOptionRepository.saveAll(itemOptions);
    }

//    @Override
//    public void deleteOption(Long optionKey) {
//        optionRepository.deleteById(optionKey);
//    }
//
//    @Override
//    public void deleteItemOption(Long itemOptionKey) {
//        itemOptionRepository.deleteById(itemOptionKey);
//    }
}
