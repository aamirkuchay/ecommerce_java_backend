package com.ecommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String name;

    public UserDTO(long id, String name) {
        this. id = id;
        this.name= name;
    }
}
