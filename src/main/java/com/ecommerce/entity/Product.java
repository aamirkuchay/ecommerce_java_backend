package com.ecommerce.entity;

import com.ecommerce.exception.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(length = 60)
    private String metaTitle;

    @Column(length = 160)
    private String metaDescription;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private Double averageRating;

    @Column(nullable = false)
    private boolean isFeatured = false;

    private Long totalQuantity = 0L;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAttribute> attributes =  new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSKU> skus = new ArrayList<>();


    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }



    public void reduceQuantity(Long quantityToReduce) {
        if (this.totalQuantity < quantityToReduce) {
            throw new ResourceNotFoundException("Not enough quantity available");
        }
        this.totalQuantity -= quantityToReduce;
    }




}




