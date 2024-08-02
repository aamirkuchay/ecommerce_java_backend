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
    private String description;
    private BigDecimal basePrice;
    private ProductStatus status;
    private String slug;
    private List<CategoryDto> categories;
    private BrandDto brand;
    private List<ProductSkuResponseDto> skus;
    private List<ProductImageDto> images;




}
