package com.keyboard2.service;

import com.keyboard2.dto.UserDTO;
import com.keyboard2.entity.User;
import com.keyboard2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public void UserRegister(UserDTO userDTO) {

        User user = User.builder()
//                .userId(userDTO.getUserId())
                .userPassword(userDTO.getUserPassword())
                .userEmail(userDTO.getUserEmail())
                .userPhone(userDTO.getUserPhone())
                .userAddress(userDTO.getUserAddress())
                .userGender(userDTO.getUserGender())
                .build();

        userRepository.save(user);

    }

    @Override
    public UserDTO UserLogin(UserDTO userDTO) {
        Optional<User> entity = userRepository.findByUserIdAndUserPassword(userDTO.getUserId(), userDTO.getUserPassword());
        User user = entity.get();

        return UserDTO.builder()
//                .userKey(user.getUserKey())
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .userEmail(user.getUserEmail())
                .userAddress(user.getUserAddress())
                .userGender(user.getUserGender())
                .userPhone(user.getUserPhone())
                .build();
    }

    @Override
    public User getUser(String userId) {
        Optional<User> userEntity = userRepository.findById(userId);
        User user = userEntity.get();

        return user;
    }
}
