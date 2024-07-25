package com.ecommerce.dto;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.ProductImage;
import com.ecommerce.entity.ProductStatus;
import lombok.Data;

import java.util.List;


@Data
public class ProductDto {

    private Long id;


    private String name;


    private String description;


    private Double price;


    private String photo;


    private ProductStatus status;


    private List<Category> categories;


    private List<String> sizes;


    private List<Double> weights;

    private List<ProductImage> images;
}
