package com.ecommerce.dto;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductImage;
import com.ecommerce.entity.ProductStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private ProductStatus status;
    private List<String> categoryNames;
    private List<SizeDTO> sizes;
    private List<WeightDTO> weights;
    private List<String> images;






    public static ProductResponseDto fromProduct(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStatus(product.getStatus());
        dto.setCategoryNames(product.getCategories().stream().map(Category::getName).collect(Collectors.toList()));
        dto.setSizes(product.getSizes().stream().map(SizeDTO::fromProductSize).collect(Collectors.toList()));
        dto.setWeights(product.getWeights().stream().map(WeightDTO::fromProductWeight).collect(Collectors.toList()));
        dto.setImages(product.getImages().stream().map(ProductImage::getUrl).collect(Collectors.toList()));
        return dto;
    }
}
