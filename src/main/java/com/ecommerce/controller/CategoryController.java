package com.ecommerce.controller;

import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponseDTO;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> addCategory(@RequestBody CategoryDTO categoryDTO,
                                                           @RequestParam(required = false) Long parentId) {
        CategoryResponseDTO savedCategory = categoryService.addCategory(categoryDTO, parentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PostMapping("/{parentId}/subcategories")
    public ResponseEntity<CategoryResponseDTO> addSubcategory(@PathVariable Long parentId,
                                                              @RequestBody CategoryDTO categoryDTO) {
        CategoryResponseDTO savedSubcategory = categoryService.addSubcategory(parentId, categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubcategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllRootCategories() {
        List<CategoryResponseDTO> rootCategories = categoryService.getAllRootCategories();
        return ResponseEntity.ok(rootCategories);
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<CategoryResponseDTO>> getAllSubcategories(@PathVariable Long categoryId) {
        List<CategoryResponseDTO> subcategories = categoryService.getAllSubcategories(categoryId);
        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponseDTO category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }








}

