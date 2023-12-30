package com.keyboard2.dto;

import com.keyboard2.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.Set;

@Data
public class MemberDTO extends User {

    private String userId;
    private String userPassword;


    public MemberDTO(String userId,
                     String userPassword,
                     Collection<? extends GrantedAuthority> authorities) {
        super(userId, userPassword, authorities);
        this.userId = userId;
        this.userPassword = userPassword;
    }



}
