package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.*;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ProductSizeRepository;
import com.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private static final String IMAGE_DIRECTORY =  "C:\\usr\\ecommerce\\";

    @Override
    public Product saveProduct(ProductDto productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStatus(productRequest.getStatus());

        List<Category> categories = categoryRepository.findAllById(productRequest.getCategoryIds());
        product.setCategories(categories);

        List<ProductSize> sizes = productRequest.getSizes().stream()
                .map(sizeDTO -> new ProductSize(null, product, sizeDTO.getSize(), sizeDTO.getQuantity()))
                .collect(Collectors.toList());
        product.setSizes(sizes);

        List<ProductWeight> weights = productRequest.getWeights().stream()
                .map(weightDTO -> new ProductWeight(null, product, weightDTO.getWeight(), weightDTO.getQuantity()))
                .collect(Collectors.toList());
        product.setWeights(weights);

        List<ProductImage> images = productRequest.getImages().stream()
                .map(image -> {
                    String fileName = saveImage(image);
                    return new ProductImage(null, product, fileName);
                })
                .collect(Collectors.toList());
        product.setImages(images);

        return productRepository.save(product);
    }

    private String saveImage(MultipartFile image) {
        try {
            Files.createDirectories(Paths.get(IMAGE_DIRECTORY));
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            File file = new File(IMAGE_DIRECTORY + fileName);
            image.transferTo(file);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
