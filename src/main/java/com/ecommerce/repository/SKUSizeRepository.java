package com.ecommerce.repository;

import com.ecommerce.entity.SKUSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SKUSizeRepository extends JpaRepository<SKUSize,Long> {
}
