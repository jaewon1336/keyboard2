package com.keyboard2.dto;

import com.keyboard2.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private int totalPages;
    private List<ItemDTO> items;
}

