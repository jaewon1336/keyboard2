package com.keyboard2.controller;

import com.keyboard2.dto.OptionDTO;
import com.keyboard2.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/option")
public class OptionController {

    @Autowired
    private OptionService optionService;

    @PostMapping("/add")
    public String optionAdd(OptionDTO optionDTO) {
        optionService.addOption(optionDTO);
        return null;
    }

//    @GetMapping("/delete/{optionKey}")
//    public String optionDelete(@PathVariable Long optionKey) {
//        optionService.deleteOption(optionKey);
//        return "redirect:/item";
//    }
//
//    @GetMapping("/itemOptionDelete/{itemOptionKey}")
//    public String itemOptionDelete(@PathVariable Long itemOptionKey) {
//        optionService.deleteItemOption(itemOptionKey);
//        return "redirect:/item";
//    }


}
