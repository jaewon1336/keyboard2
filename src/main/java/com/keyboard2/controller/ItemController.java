package com.keyboard2.controller;

import com.keyboard2.dto.ItemDTO;
//import com.keyboard2.dto.ItemOptionDTO;
//import com.keyboard2.dto.OptionDTO;
import com.keyboard2.repository.ImageRepository;
import com.keyboard2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
public class ItemController {

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageRepository imageRepository;



//    @GetMapping("")
//    public String itemsView(Model model, OptionDTO optionDTO, ItemOptionDTO itemOptionDTO) {
//        List<ItemDTO> items = itemService.getAllItems();
//        model.addAttribute("items", items);
//        return "item/list";
//    }

    @GetMapping("/items")
    public String itemsList(Model model) {
        List<ItemDTO> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "item/list";
    }

    @GetMapping("/item/add")
    public String addView(ItemDTO itemDTO) {
        return "item/add";
    }

    @PostMapping("/item/add")
    public String itemAdd(ItemDTO itemDTO, MultipartFile imageFile, Model model) throws IOException {
        String fileName = imageFile.getOriginalFilename();
        String uploadDir = "src/main/resources/static/images/";

        FileCopyUtils.copy(imageFile.getBytes(), new File(uploadDir + fileName));

        itemDTO.setItemImage(fileName);
        itemService.addItem(itemDTO);

        return "item/add";
    }



    @GetMapping("/item/detail/{itemKey}")
    public String itemDetail(@PathVariable Long itemKey, Model model) {
        ItemDTO item = itemService.getItemDetail(itemKey);
        model.addAttribute("item", item);

        return "item/detail";
    }
}
