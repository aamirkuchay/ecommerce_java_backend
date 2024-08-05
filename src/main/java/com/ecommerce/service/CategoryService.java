package com.ecommerce.service;

import com.ecommerce.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService  {
    Category saveCategory(Category category);

    Optional<Category> getCategoryById(Long id);

    Category updateCategory(Long id, Category categoryDetails);

    void deleteCategory(Long id);

    Page<Category> getAllCategories(int page);
}
