//package com.ecommerce.controller;
//
//import com.ecommerce.entity.Category;
//import com.ecommerce.exception.ResourceNotFoundException;
//import com.ecommerce.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/categories")
//public class CategoryController {
//
//    @Autowired
//    private CategoryService categoryService;
//
//
//    @PostMapping
//    public Category createCategory(@RequestBody Category category) {
//        return categoryService.saveCategory(category);
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
//        Optional<Category> category = categoryService.getCategoryById(id);
//        return category.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
//        try {
//            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
//            return ResponseEntity.ok(updatedCategory);
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
//        try {
//            categoryService.deleteCategory(id);
//            return ResponseEntity.noContent().build();
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping
//    public List<Category> getAllCategories() {
//        return categoryService.getAllCategories();
//    }
//}
//
