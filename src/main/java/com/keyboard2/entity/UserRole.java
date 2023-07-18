package com.keyboard2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRoleKey;

    private String roleName;



}
