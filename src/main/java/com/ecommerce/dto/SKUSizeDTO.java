package com.ecommerce.dto;

import lombok.*;

@NoArgsConstructor
@Data @AllArgsConstructor
public class SKUSizeDTO {
    private Long sizeId;
    private SizeDto size;
    private Integer quantity;



}
