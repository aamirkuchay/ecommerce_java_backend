package com.ecommerce.service.impl;


import com.ecommerce.dto.CategoryDTO;
import com.ecommerce.dto.CategoryResponseDTO;
import com.ecommerce.dto.ParentCategoryDTO;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public CategoryResponseDTO addCategory(CategoryDTO categoryDTO, Long parentId) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setSlug(categoryDTO.getSlug());

        if (parentId != null) {
            Category parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found" + parentId));
            category.setParent(parentCategory);
        }

        Category savedCategory = categoryRepository.save(category);
        return convertToResponseDTO(savedCategory);
    }

    @Override
    public CategoryResponseDTO addSubcategory(Long parentId, CategoryDTO categoryDTO) {
        Category parentCategory = categoryRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent category not found" + parentId));

        Category subcategory = new Category();
        subcategory.setName(categoryDTO.getName());
        subcategory.setSlug(categoryDTO.getSlug());
        subcategory.setParent(parentCategory);

        Category savedSubcategory = categoryRepository.save(subcategory);
        return convertToResponseDTO(savedSubcategory);
    }

    @Override
    public Page<CategoryResponseDTO> getAllRootCategories(int page) {
        Pageable pageable = PageRequest.of(page , 10);
        Page<Category> rootCategories = categoryRepository.findByParentIsNull(pageable);
        return rootCategories.map(this::convertToResponseDTO);
//        return rootCategories.stream()
//                .map(this::convertToResponseDTO)
//                .collect(Collectors.toList());
    }


    @Override
    public List<CategoryResponseDTO> getAllSubcategories(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found" + categoryId));
        return category.getChildren().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given id " + id));
        return convertToResponseDTO(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with given id"+ id));
        categoryRepository.delete(category);
    }

















    private CategoryResponseDTO convertToResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());

        if (category.getParent() != null) {
            ParentCategoryDTO parentDTO = new ParentCategoryDTO();
            parentDTO.setId(category.getParent().getId());
            parentDTO.setName(category.getParent().getName());
            parentDTO.setSlug(category.getParent().getSlug());
            dto.setParent(parentDTO);
        }

        dto.setChildrenIds(category.getChildren().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        return dto;
    }

}

