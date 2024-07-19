package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductSize;
import com.ecommerce.entity.ProductWeight;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ProductSizeRepository;
import com.ecommerce.repository.ProductWeightRepository;
import com.ecommerce.service.ProductService;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ProductWeightRepository productWeightRepository;
    @Autowired
    private ProductRepository productRepository;

    private final String UPLOAD_DIR = "C:\\usr\\ecommerce";


    @Override
    public Product saveProduct(ProductDto productDto) throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setWeight(productDto.getWeight());

        MultipartFile photoFile = productDto.getPhoto();
        if (photoFile != null && !photoFile.isEmpty()) {
            Tika tika = new Tika();
            String mimeType = tika.detect(photoFile.getBytes());
            if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/png") && !mimeType.equals("image/jpg")) {
                throw new IOException("Invalid file type. Only JPEG and PNG files are allowed.");
            }
            String photoFileName = System.currentTimeMillis() + "_" + photoFile.getOriginalFilename();
            Files.copy(photoFile.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + photoFileName));
            product.setPhoto(photoFileName);
        }

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        if (productDto.getSizes() != null) {
            productDto.getSizes().forEach(size -> {
                ProductSize productSize = new ProductSize();
                productSize.setSize(size.getSize());
                productSize.setProduct(savedProduct);
                productSizeRepository.save(productSize);
            });
        }

        if (productDto.getWeights() != null) {
            productDto.getWeights().forEach(weight -> {
                ProductWeight productWeight = new ProductWeight();
                productWeight.setWeight(weight.getWeight());
                productWeight.setProduct(savedProduct);
                productWeightRepository.save(productWeight);
            });
        }

        return savedProduct;
    }
}
