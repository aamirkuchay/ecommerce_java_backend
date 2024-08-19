package com.ecommerce.dto;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductImage;
import com.ecommerce.entity.ProductStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private BigDecimal basePrice;
    private ProductStatus status;
    private String metaTitle;
    private String metaDescription;
    private boolean isFeatured;
    private List<CategoryDTO> categories;
    private BrandDto brand;
    private Long totalQuantity = 0L;
    private List<ProductAttributeDto> attributes;
    private List<ProductImageDto> images;
    private List<ProductSKUDto> skus;





}
