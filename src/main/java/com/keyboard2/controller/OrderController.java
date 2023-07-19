package com.keyboard2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.keyboard2.dto.*;
import com.keyboard2.entity.Item;
import com.keyboard2.entity.ItemOption;
import com.keyboard2.repository.ItemOptionRepository;
import com.keyboard2.repository.ItemRepository;
import com.keyboard2.service.ItemService;
import com.keyboard2.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemOptionRepository itemOptionRepository;

    @Autowired
    private OptionService optionService;

    @GetMapping("/checkout")
    public String order(@RequestParam("item[]") String encodedItem, Model model) {
        Gson gson = new Gson();
        OrderDTO2 orderDTO2 = gson.fromJson(encodedItem, OrderDTO2.class);

        List<ItemOptionDTO> itemOptionDTOList = new ArrayList<>();
        ItemDTO itemDTO = itemService.getItemDetail(orderDTO2.getItemKey());
        for (Long itemOptionKey : orderDTO2.getItemOptionKey()) {
            ItemOptionDTO itemOptionDTO = optionService.getOption(itemOptionKey);
            itemOptionDTOList.add(itemOptionDTO);
        }
        model.addAttribute("itemOptionDTOList", itemOptionDTOList);
        model.addAttribute("itemDTO", itemDTO);

        return "item/checkout";
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody OrderDTO2 orderDTO2) throws JsonProcessingException {

        // orderDTO를 JSON 형식으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOrderDTO = null;
        String encodedItem = null;
        try {
            jsonOrderDTO = objectMapper.writeValueAsString(orderDTO2);
            encodedItem = URLEncoder.encode("item[]", StandardCharsets.UTF_8)
                    + "="
                    + URLEncoder.encode(jsonOrderDTO, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 전송 로직 수행 (예시에서는 출력)
        System.out.println("Encoded OrderDTO: " + encodedItem);


        return ResponseEntity.status(HttpStatus.OK).body(encodedItem);
    }

}
