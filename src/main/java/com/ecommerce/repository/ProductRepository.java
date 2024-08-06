package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryName IS NULL OR :categoryName MEMBER OF p.categories) AND " +
            "(:brand IS NULL OR p.brand.name = :brand) AND " +
            "(:minPrice IS NULL OR p.basePrice >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.basePrice <= :maxPrice)")
    Page<Product> findByCriteria(Pageable pageable,
                                 @Param("categoryName") String categoryName,
                                 @Param("brand") String brand,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice);
    }
