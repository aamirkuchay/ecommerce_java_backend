package com.ecommerce.beans;

import com.ecommerce.entity.ProductSKU;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public SimpleModule productSKUSerializerModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(ProductSKU.class, new ProductSKUSerializer());
        return module;
    }
}
