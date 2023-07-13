package com.keyboard2.entity;
import lombok.*;

import javax.persistence.*;
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {})
@Table(name = "TBL_CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryKey;

    private String categoryName;

}
