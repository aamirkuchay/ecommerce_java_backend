package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductSKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSKURepository extends JpaRepository<ProductSKU,Long> {

    @Query("SELECT ps FROM ProductSKU ps WHERE ps.product = :product AND ps.sku IN :skus")
    Optional<ProductSKU> findByProductAndSkus(@Param("product") Product product, @Param("skus") List<ProductSKU> skus);

}
