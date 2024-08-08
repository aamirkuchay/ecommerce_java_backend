package com.ecommerce.beans;


import com.ecommerce.entity.ProductSKU;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ProductSKUSerializer extends JsonSerializer<ProductSKU> {


    @Override
    public void serialize(ProductSKU sku, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", sku.getId());
        gen.writeStringField("sku", sku.getSku());
        gen.writeNumberField("price", sku.getPrice());
        gen.writeEndObject();
    }
}
