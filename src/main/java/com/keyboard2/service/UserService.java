package com.keyboard2.service;

import com.keyboard2.dto.UserDTO;
import com.keyboard2.entity.User;

public interface UserService {
    public void UserRegister(UserDTO userDTO);
    public UserDTO UserLogin(UserDTO userDTO);

    public User getUser(Long userKey);
}
