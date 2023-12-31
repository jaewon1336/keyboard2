package com.keyboard2.service;

import com.keyboard2.dto.OrderDTO;
import com.keyboard2.dto.UserDTO;
import com.keyboard2.entity.User;
import com.keyboard2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public UserDTO getUserDTO(String userId) {
        Optional<User> userEntity = userRepository.findById(userId);
        User user = userEntity.get();

        List<OrderDTO> orderDTOS = user.getOrders().stream()
                .map(order -> {
                    OrderDTO orderDTO = entityToDto(order);
                    return  orderDTO;
                }).collect(Collectors.toList());



        return UserDTO.builder()
                .userId(user.getUserId())
                .userPassword(user.getUserPassword())
                .userAddress(user.getUserAddress())
                .userPhone(user.getUserPhone())
                .userGender(user.getUserGender())
                .userEmail(user.getUserEmail())
                .orderDTOS(orderDTOS)
                .build();


    }




}
