package com.ecommerce.service.impl;

import com.ecommerce.dto.*;

import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import com.ecommerce.service.FileStorageService;
import com.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductColorRepository productColorRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              BrandRepository brandRepository,
                              ProductColorRepository productColorRepository,
                              FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productColorRepository = productColorRepository;
        this.fileStorageService = fileStorageService;
    }

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    private static final String IMAGE_DIRECTORY = "C:\\usr\\ecommerce\\";

    @Override
    public ProductResponseDto createProduct(ProductDto productCreateDto, List<MultipartFile> images) {
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setDescription(productCreateDto.getDescription());
        product.setBasePrice(productCreateDto.getBasePrice());
        product.setStatus(productCreateDto.getStatus());
        product.setSlug(generateSlug(productCreateDto.getName()));

        Set<Category> categories = (Set<Category>) categoryRepository.findAllById(productCreateDto.getCategoryIds());
        product.setCategories(categories);

        Brand brand = brandRepository.findById(productCreateDto.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        product.setBrand(brand);

        List<ProductSKU> skus = createProductSKUs(product, productCreateDto.getSkus());
        product.setSkus(skus);

        List<ProductImage> productImages = saveProductImages(product, images);
        product.setImages(productImages);

        Product savedProduct = productRepository.save(product);
        return mapToProductResponseDto(savedProduct);
    }

    private ProductResponseDto mapToProductResponseDto(Product savedProduct) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(savedProduct.getId());
        responseDto.setName(savedProduct.getName());
        responseDto.setDescription(savedProduct.getDescription());
        responseDto.setBasePrice(savedProduct.getBasePrice());
        responseDto.setStatus(savedProduct.getStatus());
        responseDto.setSlug(savedProduct.getSlug());

        responseDto.setCategories(savedProduct.getCategories().stream()
                .map(this::mapToCategoryDto)
                .collect(Collectors.toList()));
        responseDto.setBrand(mapToBrandDto(savedProduct.getBrand()));
        responseDto.setImages(savedProduct.getImages().stream()
                .map(this::mapToProductImageDto)
                .collect(Collectors.toList()));

        return responseDto;
    }


    private CategoryDto mapToCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    private BrandDto mapToBrandDto(Brand brand) {
        BrandDto dto = new BrandDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        return dto;
    }


    private ProductSkuResponseDto mapToProductSkuResponseDto(ProductSKU sku) {
        ProductSkuResponseDto dto = new ProductSkuResponseDto();
        dto.setSku(sku.getSku());
        dto.setPrice(sku.getPrice());
        dto.setQuantity(sku.getQuantity());
        dto.setWeight(sku.getWeight());
        dto.setColorId(mapToProductColorDto(sku.getColor()));

        // Map attributes
        dto.setAttributes(sku.getAttributes().stream()
                .map(this::mapToSKUAttributeDto)
                .collect(Collectors.toList()));

        return dto;
    }

    private SKUAttributeDto mapToSKUAttributeDto(SKUAttribute attribute) {
        SKUAttributeDto dto = new SKUAttributeDto();
        dto.setName(attribute.getName());
        dto.setValue(attribute.getValue());
        return dto;
    }

    private ProductImageDto mapToProductImageDto(ProductImage image) {
        ProductImageDto dto = new ProductImageDto();
        dto.setUrl(image.getUrl());
        dto.setPrimary(image.isPrimary());
        return dto;
    }

    private Long mapToProductColorDto(ProductColor color) {
        ProductColorDto dto = new ProductColorDto();
        dto.setId(color.getId());
        dto.setName(color.getName());
        return dto.getId();
    }


    private List<ProductSKU> createProductSKUs(Product product, List<ProductSKUDto> skuDtos) {
        return skuDtos.stream().map(skuDto -> {
            ProductSKU sku = new ProductSKU();
            sku.setProduct(product);
            sku.setSku(skuDto.getSku());
            sku.setPrice(skuDto.getPrice());
            sku.setQuantity(skuDto.getQuantity());
            sku.setWeight(skuDto.getWeight());

            ProductColor color = productColorRepository.findById(skuDto.getColorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Color not found"));
            sku.setColor(color);

            List<SKUAttribute> attributes = createSKUAttributes(sku, skuDto.getAttributes());
            sku.setAttributes(attributes);

            return sku;
        }).collect(Collectors.toList());
    }

    private List<SKUAttribute> createSKUAttributes(ProductSKU sku, List<SKUAttributeDto> attributeDtos) {
        return attributeDtos.stream().map(attributeDto -> {
            SKUAttribute attribute = new SKUAttribute();
            attribute.setSku(sku);
            attribute.setName(attributeDto.getName());
            attribute.setValue(attributeDto.getValue());
            return attribute;
        }).collect(Collectors.toList());
    }

    private String generateSlug(String name) {
        return name.toLowerCase().replaceAll("\\s+", "-");
    }

    private List<ProductImage> saveProductImages(Product product, List<MultipartFile> images) {
        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);
            String fileName = fileStorageService.storeFile(image, product.getSlug());

            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setUrl(fileName);
            productImage.setPrimary(i == 0);
            productImages.add(productImage);
        }
        return productImages;
    }
    }

