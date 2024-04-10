package com.keyboard2.controller;

import com.keyboard2.dto.ItemDTO;
//import com.keyboard2.dto.ItemOptionDTO;
//import com.keyboard2.dto.OptionDTO;
import com.keyboard2.dto.PageDTO;
import com.keyboard2.entity.Item;
import com.keyboard2.repository.ImageRepository;
import com.keyboard2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@Controller
public class ItemController {

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageRepository imageRepository;



//    @GetMapping("/items")
//    public String itemsList(Model model, @RequestParam(value = "page", required = false) String page) {
//        List<ItemDTO> items = itemService.getAllItems();
//        List<ItemDTO> paginationItems = itemService.getPagenationItems(page);
//        if (page == null || page.equals("1")) {
//            model.addAttribute("items", items);
//        } else {
//            return "item/list";
//        }
//        return "item/list";
//    }


    @GetMapping("/items")
    public String getProducts(Model model,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "5") int size) {
        PageDTO items = itemService.getAllItems(PageRequest.of(page-1,size));
        model.addAttribute("items", items);
        System.out.println("아이템들! : " + items);
        return "item/list";
    }

    @GetMapping("/item/detail/{itemKey}")
    public String itemDetail(@PathVariable Long itemKey, Model model) {
        ItemDTO item = itemService.getItemDetail(itemKey);
        model.addAttribute("item", item);
        return "item/detail";
    }



    @GetMapping("/items/option")
    public Object optionSelect(Model model, @RequestParam(value = "color", required = false) String colors,
                                          @RequestParam(value = "switch", required = false) String switches,
                                          @RequestParam(value = "refresh", required = false) String refresh,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "2") int size) {
        if (refresh == null) {
            PageDTO items = itemService.getAllItems(PageRequest.of(page-1,size));
            model.addAttribute("items", items);
            return "item/list";
        } else {
            String[] colorsArray = null;
            String[] switchArray = null;
            List<Item> items = new ArrayList<>();
            int count = 0;

            if (colors != null) {
                colorsArray = colors.split("\\|");
                count = count + 1;
            } else if (switches != null) {
                switchArray = switches.split("\\|");
                count = count + 1;
            }

            for (int i = 0; i < count; i++) {
                for (Item item : itemService.findProductByProductOption(colors, switches)) {
                    items.add(item);
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(items);
        }
    }



}
