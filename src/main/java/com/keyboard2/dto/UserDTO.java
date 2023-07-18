package com.keyboard2.dto;

import com.keyboard2.entity.Cart;
import com.keyboard2.entity.Order;
import com.keyboard2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userKey;

    private String userId;

    private String userPassword;

    private String userEmail;

    private String userPhone;

    private String userAddress;

    private String userGender;


    //    private List<Order> orders;
//
//    private List<Cart> carts;
}
