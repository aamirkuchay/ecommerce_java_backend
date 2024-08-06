package com.ecommerce.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private String name;
    private String slug;
    private Long parentId;
}
