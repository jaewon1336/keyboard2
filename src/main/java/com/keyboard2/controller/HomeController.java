package com.keyboard2.controller;

import com.keyboard2.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Member;

@Controller
public class HomeController {

    @GetMapping("/")
    public String Home() {

        return "/home";
    }

}
