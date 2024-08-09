package com.ecommerce.repository;

import com.ecommerce.entity.Brand;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "JOIN p.categories c " +
            "WHERE (:category IS NULL OR c.name = :category) " +
            "AND (:brand IS NULL OR p.brand.name = :brand) " +
            "AND (:minPrice IS NULL OR p.basePrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.basePrice <= :maxPrice) " +
            "AND p.isDeleted = false")
    Page<Product> findByCriteria(@Param("category") String category,
                                 @Param("brand") String brand,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice,
                                 Pageable pageable);

    Page<Product> findByCategories(Category category, Pageable pageable);

    Page<Product> findByBrand(Brand brand, Pageable pageable);

    List<Product> findByFeaturedTrue(PageRequest of);


    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Product> searchByNameOrDescription(@Param("query") String query, Pageable pageable);

}
