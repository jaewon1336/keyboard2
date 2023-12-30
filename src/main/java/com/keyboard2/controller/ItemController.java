package com.keyboard2.controller;

import com.keyboard2.dto.ItemDTO;
//import com.keyboard2.dto.ItemOptionDTO;
//import com.keyboard2.dto.OptionDTO;
import com.keyboard2.dto.ItemOptionDTO;
import com.keyboard2.repository.ImageRepository;
import com.keyboard2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class ItemController {

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageRepository imageRepository;



    @GetMapping("/items")
    public String itemsList(Model model) {
        List<ItemDTO> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "item/list";
    }





    @GetMapping("/item/detail/{itemKey}")
    public String itemDetail(@PathVariable Long itemKey, Model model) {
        ItemDTO item = itemService.getItemDetail(itemKey);
        model.addAttribute("item", item);

        return "item/detail";
    }


    @GetMapping("/items/option")
    public ResponseEntity<?> optionSelect(@RequestParam("color") String colors, @RequestParam("switch") String switches) {

        String[] colorsArray = colors.split("\\|");
        String[] switchArray = switches.split("\\|");

        List<ItemDTO> items = new ArrayList<>();

        for (String switch2 : switchArray) {
            for (ItemDTO item : itemService.getItemByOption(switch2)) {
                items.add(item);
            }
        }

        for (String color : colorsArray) {
            for (ItemDTO item : itemService.getItemByOption(color)) {
                items.add(item);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}
