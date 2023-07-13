package com.keyboard2.controller;


import com.keyboard2.dto.CartDTO;
import com.keyboard2.dto.CartItemDTO;
import com.keyboard2.entity.User;
import com.keyboard2.service.CartService;
import com.keyboard2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> cartCreate(@RequestBody CartDTO cartDTO, HttpSession session) {
//        System.out.println("cartDTO :: " + cartDTO);
        Long userKey = (Long) session.getAttribute("userKey");
        User user = userService.getUser(userKey);


        cartService.createCart(cartDTO, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("")
    public String cartView(HttpSession session, Model model) {
        Long userKey = (Long) session.getAttribute("userKey");

        CartDTO cartDTO = cartService.getCart(userKey);
        model.addAttribute("cartDTO",cartDTO);
        return "cart/list";
    }
}
