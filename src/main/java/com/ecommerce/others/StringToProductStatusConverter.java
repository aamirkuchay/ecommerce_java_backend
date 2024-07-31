package com.ecommerce.others;

import com.ecommerce.entity.ProductStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToProductStatusConverter implements Converter<String, ProductStatus> {
    @Override
    public ProductStatus convert(String source) {
        try {
            return ProductStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}