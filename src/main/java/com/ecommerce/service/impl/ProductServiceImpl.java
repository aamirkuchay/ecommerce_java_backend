package com.ecommerce.service.impl;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductImage;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductImageRepository productImageRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;


    private final String UPLOAD_DIR = "C:\\usr\\ecommerce";

    @Transactional
    public Product saveProduct(Product product, List<MultipartFile> images) throws IOException {

        Product savedProduct = productRepository.save(product);
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile image : images) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            File file = new File(UPLOAD_DIR + fileName);
            image.transferTo(file);

            ProductImage productImage = new ProductImage();
            productImage.setProduct(savedProduct);
            productImage.setUrl(fileName);
            productImages.add(productImage);
        }
        productImageRepository.saveAll(productImages);

        savedProduct.setImages(productImages);
        return productRepository.save(savedProduct);
    }
}
