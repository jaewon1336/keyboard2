package com.keyboard2.controller;


import com.keyboard2.dto.CartDTO;
import com.keyboard2.entity.User;
import com.keyboard2.service.CartService;
import com.keyboard2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> cartCreate(@RequestBody CartDTO cartDTO, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증된 사용자 정보를 얻어옴
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                // UserDetails 타입으로 형변환하여 사용자 정보에 접근
                UserDetails userDetails = (UserDetails) principal;
                User user = userService.getUser(userDetails.getUsername());

                cartService.createCart(cartDTO, user);
            } else {
            }
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("")
    public String cartView(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증된 사용자 정보를 얻어옴
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                // UserDetails 타입으로 형변환하여 사용자 정보에 접근
                UserDetails userDetails = (UserDetails) principal;
                CartDTO cartDTO = cartService.getCart(userDetails.getUsername());
                model.addAttribute("cartDTO",cartDTO);
            } else {
            }
        }



        return "cart/list";
    }
}
