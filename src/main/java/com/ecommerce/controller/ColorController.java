package com.ecommerce.controller;


import com.ecommerce.entity.ProductColor;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/colors")
public class ColorController {


    @Autowired
    private ColorService colorService;


    @PostMapping
    public ProductColor createColor(@RequestBody ProductColor color) {
        return colorService.saveColor(color);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductColor> getColorById(@PathVariable Long id) {
        Optional<ProductColor> color = colorService.getColorById(id);
        return color.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductColor> updateColor(@PathVariable Long id, @RequestBody ProductColor color) {
        try {
            ProductColor updatedColor = colorService.updateColor(id, color);
            return ResponseEntity.ok(updatedColor);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        try {
            colorService.deleteColor(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Page<ProductColor> getAllColors(@RequestParam(defaultValue = "0") int page) {
        return colorService.getAllColors(page);
    }


}
