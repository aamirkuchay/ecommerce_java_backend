package com.ecommerce.dto;

import com.ecommerce.entity.ProductStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



@Data
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






}
