package com.ecommerce.service;

import com.ecommerce.entity.Size;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SizeService {
    Size saveSize(Size size);

    Optional<Size> getSizeById(Long id);

    Size updateSize(Long id, Size size);

    void deleteSize(Long id);

    Page<Size> getAllSizes(int page);
}
