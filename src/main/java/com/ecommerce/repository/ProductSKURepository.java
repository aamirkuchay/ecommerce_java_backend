package com.ecommerce.repository;

import com.ecommerce.entity.ProductSKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSKURepository extends JpaRepository<ProductSKU,Long> {
}
