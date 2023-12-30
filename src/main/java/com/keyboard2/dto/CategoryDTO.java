package com.keyboard2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryKey;

    private String categoryName;
}
