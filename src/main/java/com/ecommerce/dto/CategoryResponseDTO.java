package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String slug;
    private ParentCategoryDTO parent;
    private Set<Long> childrenIds;
}
