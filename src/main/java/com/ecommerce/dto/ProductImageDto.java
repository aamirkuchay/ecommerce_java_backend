package com.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductImageDto {

    private static final String IMAGE_BASE_URL = "http://localhost:8085/api/images/";

    private String url;
    private boolean isPrimary;

          public ProductImageDto(String filename, boolean isPrimary) {
            this.url = IMAGE_BASE_URL + filename;
            this.isPrimary = isPrimary;
        }

}
