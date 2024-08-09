package com.ecommerce.dto;

import com.ecommerce.entity.ProductAttribute;
import com.ecommerce.entity.ProductStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@Data
public class ProductDto {
    private String name;
    private String slug;
    private String description;
    private BigDecimal basePrice;
    private ProductStatus status;
    private List<Long> categories;
    private Long brandId;
    private String metaTitle;
    private String metaDescription;
    private boolean isFeatured;
    private List<ProductAttributeDto> attributes;
    private List<ProductImageDto> images;
    private List<ProductSKUDto> skus;



    @JsonCreator
    public ProductDto(
            @JsonProperty("name") String name,
            @JsonProperty("slug") String slug,
            @JsonProperty("description") String description,
            @JsonProperty("basePrice") BigDecimal basePrice,
            @JsonProperty("status") ProductStatus status,
            @JsonProperty("categories") List<Long> categories,
            @JsonProperty("brandId") Long brandId,
            @JsonProperty("metaTitle") String metaTitle,
            @JsonProperty("metaDescription") String metaDescription,
            @JsonProperty("isFeatured") boolean isFeatured,
            @JsonProperty("attributes") List<ProductAttributeDto> attributes,
            @JsonProperty("images") List<ProductImageDto> images,
            @JsonProperty("skus") List<ProductSKUDto> skus
    ) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.basePrice = basePrice;
        this.status = status;
        this.categories = categories;
        this.brandId = brandId;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.isFeatured = isFeatured;
        this.attributes = attributes;
        this.images = images;
        this.skus = skus;
    }






}
