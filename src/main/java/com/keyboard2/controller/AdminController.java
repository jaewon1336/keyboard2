package com.keyboard2.controller;

import com.keyboard2.dto.ItemDTO;
import com.keyboard2.dto.MemberDTO;
import com.keyboard2.service.ItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ItemService itemService;


    @GetMapping("")
    public String exAdmin(@AuthenticationPrincipal MemberDTO authMember){


        return "admin/home";
    }

    @GetMapping("/item/add")
    public String addView(ItemDTO itemDTO) {
        return "admin/itemAdd";
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





}
