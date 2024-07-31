package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.SizeDTO;
import com.ecommerce.dto.WeightDTO;
import com.ecommerce.entity.*;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ProductSizeRepository;
import com.ecommerce.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String IMAGE_DIRECTORY = "C:\\usr\\ecommerce\\";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Product saveProduct(ProductDto productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStatus(productRequest.getStatus());

        List<Category> categories = categoryRepository.findAllById(productRequest.getCategoryIds());
        product.setCategories(categories);

        List<ProductSize> sizes = productRequest.getSizes().stream()
                .map(sizeDto -> {
                    ProductSize size = new ProductSize(product, sizeDto.getSize(), sizeDto.getQuantity());
                    List<ProductColor> colors = sizeDto.getColors().stream()
                            .map(colorDto -> new ProductColor(size, colorDto.getColor(), colorDto.getQuantity()))
                            .collect(Collectors.toList());
                    size.setColors(colors);
                    return size;
                })
                .collect(Collectors.toList());
        product.setSizes(sizes);

        List<ProductWeight> weights = productRequest.getWeights().stream()
                .map(weightDto -> new ProductWeight(product, weightDto.getWeight(), weightDto.getQuantity()))
                .collect(Collectors.toList());
        product.setWeights(weights);

        List<ProductImage> images = productRequest.getImages().stream()
                .map(image -> {
                    String fileName = saveImage(image);
                    return new ProductImage(product, fileName);
                })
                .collect(Collectors.toList());
        product.setImages(images);

        return productRepository.save(product);
    }

    private String saveImage(MultipartFile image) {
        try {
            Path directoryPath = Paths.get(IMAGE_DIRECTORY);
            Files.createDirectories(directoryPath);
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = directoryPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
