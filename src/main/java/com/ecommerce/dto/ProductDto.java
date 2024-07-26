package com.ecommerce.dto;

import com.ecommerce.entity.ProductStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private ProductStatus status;
    private List<Long> categoryIds;
    private List<SizeDTO> sizes;
    private List<WeightDTO> weights;
    private List<MultipartFile> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<SizeDTO> getSizes() {
        return sizes;
    }

    public void setSizes(List<SizeDTO> sizes) {
        this.sizes = sizes;
    }

    public List<WeightDTO> getWeights() {
        return weights;
    }

    public void setWeights(List<WeightDTO> weights) {
        this.weights = weights;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }



}
