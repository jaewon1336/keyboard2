package com.keyboard2.controller;

import com.keyboard2.dto.ItemDTO;
//import com.keyboard2.dto.ItemOptionDTO;
//import com.keyboard2.dto.OptionDTO;
import com.keyboard2.repository.ImageRepository;
import com.keyboard2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


        System.out.println("items :: " + itemService.findProductByProductOption("크림블루", "황축"));


        return ResponseEntity.status(HttpStatus.OK).body("wait");
    }


}
