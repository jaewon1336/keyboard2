package com.keyboard2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.keyboard2.dto.*;
import com.keyboard2.entity.User;
import com.keyboard2.repository.ItemRepository;
import com.keyboard2.service.ItemService;
import com.keyboard2.service.OrderService;
import com.keyboard2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String order(@RequestParam("item[]") String encodedItem, Model model) {
        Gson gson = new Gson();
        OrderDTO2 orderDTO2 = gson.fromJson(encodedItem, OrderDTO2.class);
        ItemDTO itemDTO = itemService.getItemDetail(orderDTO2.getItemKey());

        model.addAttribute("itemDTO", itemDTO);
        model.addAttribute("itemQty", orderDTO2.getItemQty());
        model.addAttribute("totalPrice" , itemDTO.getItemPrice() * orderDTO2.getItemQty());

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

    @PostMapping("/create")
    public ResponseEntity<?> orderCreate(@RequestBody OrderDTO2 orderDTO2) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증된 사용자 정보를 얻어옴
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                // UserDetails 타입으로 형변환하여 사용자 정보에 접근
                UserDetails userDetails = (UserDetails) principal;
                User user = userService.getUser(userDetails.getUsername());

                orderService.createOrder(orderDTO2, user);
            } else {
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
