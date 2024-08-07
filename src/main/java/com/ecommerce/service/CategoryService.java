package com.ecommerce.service;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponseDTO;
import com.ecommerce.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService  {

    CategoryResponseDTO addCategory(CategoryDTO categoryDTO, Long parentId);
    CategoryResponseDTO addSubcategory(Long parentId, CategoryDTO categoryDTO);
    Page<CategoryResponseDTO> getAllRootCategories(int page);
    List<CategoryResponseDTO> getAllSubcategories(Long categoryId);
    CategoryResponseDTO getCategoryById(Long id);

}
