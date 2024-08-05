package com.ecommerce.service.impl;

import com.ecommerce.dto.*;

import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import com.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.HashSet;
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
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductImageRepository productImageRepository;
    private final SizeRepository sizeRepository;
    private final WeightRepository weightRepository;
    private final ProductSKURepository productSKURepository;
    private final SKUSizeRepository skuSizeRepository;
    private final SKUWeightRepository skuWeightRepository;
    private final SKUAttributeRepository skuAttributeRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              BrandRepository brandRepository,
                              ProductColorRepository productColorRepository,
                              ProductAttributeRepository productAttributeRepository,
                              ProductImageRepository productImageRepository,
                              SizeRepository sizeRepository,
                              WeightRepository weightRepository,
                              ProductSKURepository productSKURepository,
                              SKUWeightRepository skuWeightRepository,
                              SKUSizeRepository skuSizeRepository,
                              SKUAttributeRepository skuAttributeRepository,
                              FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productColorRepository = productColorRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.productImageRepository = productImageRepository;
        this.sizeRepository = sizeRepository;
        this.weightRepository = weightRepository;
        this.productSKURepository = productSKURepository;
        this.skuWeightRepository = skuWeightRepository;
        this.skuSizeRepository = skuSizeRepository;
        this.skuAttributeRepository = skuAttributeRepository;
        this.fileStorageService = fileStorageService;
    }


    private static final String IMAGE_DIRECTORY = "C:\\usr\\ecommerce\\";



    @Override
    public ProductResponseDto createProduct(ProductDto productDTO, List<MultipartFile> images) {

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setSlug(generateSlug(productDTO.getSlug()));
        product.setDescription(productDTO.getDescription());
        product.setBasePrice(productDTO.getBasePrice());
        product.setStatus(productDTO.getStatus());
        product.setMetaTitle(productDTO.getMetaTitle());
        product.setMetaDescription(productDTO.getMetaDescription());
        product.setFeatured(productDTO.isFeatured());

        // Set categories
        Set<Category> categories = productDTO.getCategories().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category " + categoryId)))
                .collect(Collectors.toSet());
        product.setCategories(categories);

        // Set brand
        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand " + productDTO.getBrandId()));
        product.setBrand(brand);

        // Save product
        Product savedProduct = productRepository.save(product);

        // Save attributes
        List<ProductAttribute> attributes = productDTO.getAttributes().stream()
                .filter(attrDTO -> attrDTO.getName() != null && !attrDTO.getName().trim().isEmpty() &&
                        attrDTO.getValue() != null && !attrDTO.getValue().trim().isEmpty())
                .map(attrDTO -> {
                    ProductAttribute attribute = new ProductAttribute();
                    attribute.setProduct(savedProduct);
                    attribute.setName(attrDTO.getName());
                    attribute.setValue(attrDTO.getValue());
                    return attribute;
                })
                .collect(Collectors.toList());
        productAttributeRepository.saveAll(attributes);
        savedProduct.setAttributes(attributes);

        // Save images
        List<ProductImage> productImages = images.stream()
                .map(image -> {
                    String imageUrl = fileStorageService.storeFile(image, savedProduct.getSlug());
                    ProductImage productImage = new ProductImage();
                    productImage.setProduct(savedProduct);
                    productImage.setUrl(imageUrl);
                    productImage.setPrimary(false);
                    return productImage;
                })
                .collect(Collectors.toList());
        productImageRepository.saveAll(productImages);
        savedProduct.setImages(productImages);

        // Save SKUs
        List<ProductSKU> skus = productDTO.getSkus().stream()
                .map(skuDTO -> {
                    ProductSKU sku = new ProductSKU();
                    sku.setProduct(savedProduct);
                    sku.setSku(skuDTO.getSku());
                    sku.setPrice(skuDTO.getPrice());

                    // Set color
                    ProductColor color = productColorRepository.findById(skuDTO.getColorId())
                            .orElseThrow(() -> new ResourceNotFoundException("Color " + skuDTO.getColorId()));
                    sku.setColor(color);

                    // Set sizes
                    List<SKUSize> sizes = skuDTO.getSizes().stream()
                            .map(sizeDTO -> {
                                SKUSize size = new SKUSize();
                                size.setSku(sku);
                                size.setSize(sizeRepository.findById(sizeDTO.getSizeId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Size " + sizeDTO.getSizeId())));
                                size.setQuantity(sizeDTO.getQuantity()); // Ensure this is not null
                                return size;
                            })
                            .collect(Collectors.toList());
                    sku.setSizes(sizes);

                    // Set weights
                    List<SKUWeight> weights = skuDTO.getWeights().stream()
                            .map(weightDTO -> {
                                SKUWeight weight = new SKUWeight();
                                weight.setSku(sku);
                                weight.setWeight(weightRepository.findById(weightDTO.getWeightId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Weight " + weightDTO.getWeightId())));
                                weight.setQuantity(weightDTO.getQuantity()); // Ensure this is not null
                                return weight;
                            })
                            .collect(Collectors.toList());
                    sku.setWeights(weights);

                    // Set attributes
                    List<SKUAttribute> attributesList = skuDTO.getAttributes().stream()
                            .filter(attrDTO -> attrDTO.getName() != null && !attrDTO.getName().trim().isEmpty() &&
                                    attrDTO.getValue() != null && !attrDTO.getValue().trim().isEmpty())
                            .map(attrDTO -> {
                                SKUAttribute attribute = new SKUAttribute();
                                attribute.setSku(sku);
                                attribute.setName(attrDTO.getName());
                                attribute.setValue(attrDTO.getValue());
                                return attribute;
                            })
                            .collect(Collectors.toList());
                    sku.setAttributes(attributesList);
                    return sku;
                })
                .collect(Collectors.toList());

        List<ProductSKU> savedSkus = productSKURepository.saveAll(skus);
        savedSkus.forEach(sku -> {
            skuSizeRepository.saveAll(sku.getSizes());
            skuWeightRepository.saveAll(sku.getWeights());
            skuAttributeRepository.saveAll(sku.getAttributes());
        });
        savedProduct.setSkus(savedSkus);
        // Save and build response DTO
        productRepository.save(savedProduct);

        return buildProductResponseDto(savedProduct);
    }



    private ProductResponseDto buildProductResponseDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSlug(product.getSlug());
        dto.setDescription(product.getDescription());
        dto.setBasePrice(product.getBasePrice());
        dto.setStatus(product.getStatus());
        dto.setMetaTitle(product.getMetaTitle());
        dto.setMetaDescription(product.getMetaDescription());
        dto.setFeatured(product.isFeatured());
        dto.setCategories(product.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        dto.setBrandId(product.getBrand().getId());

        // Set attributes
        dto.setAttributes(product.getAttributes().stream()
                .map(attr -> new ProductAttributeDto(attr.getName(), attr.getValue()))
                .collect(Collectors.toList()));

        // Set images
        dto.setImages(product.getImages().stream()
                .map(img -> new ProductImageDto(img.getUrl(), img.isPrimary()))
                .collect(Collectors.toList()));

        // Set SKUs
        dto.setSkus(product.getSkus().stream()
                .map(sku -> {
                    ProductSKUDto skuDTO = new ProductSKUDto();
                    skuDTO.setSku(sku.getSku());
                    skuDTO.setPrice(sku.getPrice());
                    skuDTO.setColorId(sku.getColor().getId());

                    skuDTO.setSizes(sku.getSizes().stream()
                            .map(size -> new SKUSizeDTO(size.getSize().getId(), size.getQuantity()))
                            .collect(Collectors.toList()));

                    skuDTO.setWeights(sku.getWeights().stream()
                            .map(weight -> new SKUWeightDTO(weight.getWeight().getId(), weight.getQuantity()))
                            .collect(Collectors.toList()));

                    skuDTO.setAttributes(sku.getAttributes().stream()
                            .map(attr -> new SKUAttributeDto(attr.getName(), attr.getValue()))
                            .collect(Collectors.toList()));

                    return skuDTO;
                })
                .collect(Collectors.toList()));

        return dto;
    }


    private String generateSlug(String name) {
        return name.toLowerCase().replaceAll("\\s+", "-");
    }
        }




