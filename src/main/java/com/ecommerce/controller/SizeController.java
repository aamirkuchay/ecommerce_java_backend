package com.ecommerce.controller;

import com.ecommerce.entity.ProductColor;
import com.ecommerce.entity.Size;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.ColorService;
import com.ecommerce.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {



    @Autowired
    private SizeService sizeService;


    @PostMapping
    public Size createSize(@RequestBody Size size) {
        return sizeService.saveSize(size);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Size> getSizeById(@PathVariable Long id) {
        Optional<Size> color = sizeService.getSizeById(id);
        return color.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable Long id, @RequestBody Size size) {
        try {
            Size updatedColor = sizeService.updateSize(id, size);
            return ResponseEntity.ok(updatedColor);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Long id) {
        try {
            sizeService.deleteSize(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Page<Size> getAllSizes(@RequestParam(defaultValue = "0") int page) {
        return sizeService.getAllSizes(page);
    }

}
