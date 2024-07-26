package com.ecommerce.service;

import com.ecommerce.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService  {
    Category saveCategory(Category category);

    Optional<Category> getCategoryById(Long id);

    Category updateCategory(Long id, Category categoryDetails);

    void deleteCategory(Long id);

    List<Category> getAllCategories();
}
