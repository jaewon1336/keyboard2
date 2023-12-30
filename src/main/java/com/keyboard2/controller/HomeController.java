package com.keyboard2.controller;

import com.keyboard2.dto.ItemDTO;
import com.keyboard2.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.keyboard2.service.ItemService;

import java.lang.reflect.Member;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String Home(Model model) {
        List<ItemDTO> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "/home";
    }

}
