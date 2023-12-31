package com.keyboard2.controller;

import com.keyboard2.dto.UserDTO;
import com.keyboard2.entity.User;
import com.keyboard2.repository.UserRepository;
import com.keyboard2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerView(UserDTO userDTO) {
        return "user/register";
    }



    @PostMapping("/register")
    public String register(UserDTO userDTO, User user) {
        user.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginView(UserDTO userDTO) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(UserDTO userDTO, HttpSession session) {
        UserDTO loginUser =  userService.UserLogin(userDTO);
        if (loginUser != null) {
            String userId = loginUser.getUserId();
            Long userKey = loginUser.getUserKey();

            session.setAttribute("userId", userId);
            session.setAttribute("userKey", userKey);
            session.setMaxInactiveInterval(3600);
        }
        return "/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증된 사용자 정보를 얻어옴
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                // UserDetails 타입으로 형변환하여 사용자 정보에 접근
                UserDetails userDetails = (UserDetails) principal;
                UserDTO userDTO = userService.getUserDTO(userDetails.getUsername());

                model.addAttribute("userDTO", userDTO);
            }

        }

        return "user/profile";
    }


}
