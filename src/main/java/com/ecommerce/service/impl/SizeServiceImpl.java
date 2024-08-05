package com.ecommerce.service.impl;


import com.ecommerce.entity.Brand;
import com.ecommerce.entity.Size;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.SizeRepository;
import com.ecommerce.service.SizeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    @Override
    public Size saveSize(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public Optional<Size> getSizeById(Long id) {
        return sizeRepository.findById(id);
    }

    @Override
    public Size updateSize(Long id, Size size) {
        Size size1 = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size  not found with id " + id));
        size1.setName(size.getName());
        return sizeRepository.save(size1);
    }

    @Override
    public void deleteSize(Long id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size  not found with id " + id));
        sizeRepository.delete(size);

    }

    @Override
    public Page<Size> getAllSizes(int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        return sizeRepository.findAll(pageable);
    }
}
