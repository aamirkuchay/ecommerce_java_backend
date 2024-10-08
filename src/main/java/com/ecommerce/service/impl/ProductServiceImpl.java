package com.ecommerce.service.impl;

import com.ecommerce.dto.*;

import com.ecommerce.entity.*;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.*;
import com.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
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

    private final RedisTemplate redisTemplate;

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
                .filter(attrDTO -> {
                    boolean isValid = attrDTO.getName() != null && !attrDTO.getName().trim().isEmpty() &&
                            attrDTO.getValue() != null && !attrDTO.getValue().trim().isEmpty();
                    if (!isValid) {
                        System.err.println("Invalid attribute: " + attrDTO);
                    }
                    return isValid;
                })
                .map(attrDTO -> {
                    ProductAttribute attribute = new ProductAttribute();
                    attribute.setProduct(savedProduct);
                    attribute.setName(attrDTO.getName());
                    attribute.setValue(attrDTO.getValue());
                    return attribute;
                })
                .collect(Collectors.toList());

        if (!attributes.isEmpty()) {
            productAttributeRepository.saveAll(attributes);
            savedProduct.setAttributes(attributes);
        }

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
                    List<ProductColor> colors = skuDTO.getColors().stream()
                            .map(colorDto -> productColorRepository.findById(colorDto.getId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Color " + colorDto.getId())))
                            .collect(Collectors.toList());
                    sku.setColors(colors);

                    // Set sizes
                    List<SKUSize> sizes = skuDTO.getSizes().stream()
                            .map(sizeDTO -> {
                                SKUSize size = new SKUSize();
                                size.setSku(sku);
                                size.setSize(sizeRepository.findById(sizeDTO.getSizeId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Size " + sizeDTO.getSizeId())));
                                size.setQuantity(sizeDTO.getQuantity());
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
                    sku.setSkuAttributes(attributesList);
                    return sku;
                })
                .collect(Collectors.toList());

        List<ProductSKU> savedSkus = productSKURepository.saveAll(skus);
        savedSkus.forEach(sku -> {
            skuSizeRepository.saveAll(sku.getSizes());
            skuWeightRepository.saveAll(sku.getWeights());
            skuAttributeRepository.saveAll(sku.getSkuAttributes());
        });
        savedProduct.setSkus(savedSkus);

        long totalQuantity = savedSkus.stream()
                .flatMap(sku -> Stream.concat(
                        sku.getSizes().stream().map(SKUSize::getQuantity),
                        sku.getWeights().stream().map(SKUWeight::getQuantity)
                ))
                .filter(Objects::nonNull)
                .mapToLong(quantity -> quantity.longValue())
                .sum();
        savedProduct.setTotalQuantity(totalQuantity);

        productRepository.save(savedProduct);

        return buildProductResponseDto(savedProduct);
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        return buildProductResponseDto(product);
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable, String category, String brand, Double minPrice, Double maxPrice) {
        Page<Product> productsPage = productRepository.findByCriteria(category, brand, minPrice, maxPrice, pageable);
        return productsPage.map(this::buildProductResponseDto);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        productImageRepository.deleteAll(product.getImages());
        productSKURepository.deleteAll(product.getSkus());
        productAttributeRepository.deleteAll(product.getAttributes());
        productRepository.delete(product);
    }

//    @Cacheable(value = "productsCache", key = "#categoryName.concat('-').concat(#pageable.pageNumber).concat('-').concat(#pageable.pageSize).concat('-').concat(#pageable.sort.toString())")
@Cacheable(value = "productsCache", key = "#categoryName.concat('-').concat(#pageable.pageNumber).concat('-').concat(#pageable.pageSize).concat('-').concat(#pageable.sort.toString())")
@Override
    public Page<ProductResponseDto> getProductsByCategory(String categoryName, Pageable pageable) {
        Category category = (Category) categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName));

        Page<Product> productPage = productRepository.findByCategories(category, pageable);

        return productPage.map(this::buildProductResponseDto);
    }

    @Override
    public Page<ProductResponseDto> getProductsByBrand(Long brandId, PageRequest pageRequest) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));
        Page<Product> productPage = productRepository.findByBrand(brand, pageRequest);
        return productPage.map(this::buildProductResponseDto);
    }

    @Override
    public List<ProductResponseDto> getFeaturedProducts(int limit) {
        List<Product> featuredProducts = productRepository.findByFeaturedTrue(PageRequest.of(0, limit));
        return featuredProducts.stream()
                .map(this::buildProductResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public Page<ProductResponseDto> searchProducts(String query, PageRequest pageable) {
        Page<Product> productPage = productRepository.searchByNameOrDescription(query, pageable);
        return productPage.map(this::buildProductResponseDto);
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
        dto.setTotalQuantity(product.getTotalQuantity());

        // Set categories with full objects
        dto.setCategories(product.getCategories().stream()
                .map(this::buildCategoryDto)
                .collect(Collectors.toList()));

        // Set brand with full object
        dto.setBrand(buildBrandDto(product.getBrand()));

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

                    // Set color
                    skuDTO.setColors(buildProductColorDtos(sku.getColors()));

                    // Set sizes with full objects
                    skuDTO.setSizes(sku.getSizes().stream()
                            .map(this::buildSKUSizeDto)
                            .collect(Collectors.toList()));

                    // Set weights
                    skuDTO.setWeights(sku.getWeights().stream()
                            .map(this::buildSKUWeightDto)
                            .collect(Collectors.toList()));

                    // Set SKU attributes
                    skuDTO.setAttributes(sku.getSkuAttributes().stream()
                            .map(attr -> new SKUAttributeDto(attr.getName(), attr.getValue()))
                            .collect(Collectors.toList()));

                    return skuDTO;
                })
                .collect(Collectors.toList()));

        return dto;
    }

    private CategoryDTO buildCategoryDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        return dto;
    }

    private BrandDto buildBrandDto(Brand brand) {
        BrandDto dto = new BrandDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        return dto;
    }

    private SKUSizeDTO buildSKUSizeDto(SKUSize skuSize) {
        SKUSizeDTO dto = new SKUSizeDTO();
        dto.setSizeId(skuSize.getId());
        dto.setSize(buildSizeDto(skuSize.getSize()));
        dto.setQuantity(skuSize.getQuantity());
        return dto;
    }

    private SizeDto buildSizeDto(Size size) {
        SizeDto dto = new SizeDto();
        dto.setId(size.getId());
        dto.setName(size.getName());
        return dto;
    }

    private SKUWeightDTO buildSKUWeightDto(SKUWeight skuWeight) {
        SKUWeightDTO dto = new SKUWeightDTO();
        dto.setWeightId(skuWeight.getId());
        dto.setWeight(buildWeightDto(skuWeight.getWeight()));
        dto.setQuantity(skuWeight.getQuantity());
        return dto;
    }

    private WeightDTO buildWeightDto(Weight weight) {
        WeightDTO dto = new WeightDTO();
        dto.setId(weight.getId());
        dto.setValue(weight.getValue());
        return dto;
    }

    private List<ProductColorDto> buildProductColorDtos(List<ProductColor> colors) {
        return colors.stream()
                .map(this::buildProductColorDto)
                .collect(Collectors.toList());
    }


    private ProductColorDto buildProductColorDto(ProductColor color) {
        ProductColorDto dto = new ProductColorDto();
        dto.setId(color.getId());
        dto.setName(color.getName());
        return dto;
    }


    private String generateSlug(String name) {
        return name.toLowerCase().replaceAll("\\s+", "-");
    }
        }




