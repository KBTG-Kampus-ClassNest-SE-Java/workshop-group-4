package com.kampus.kbazaar.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CartRequestDto {
    @NotBlank(message = "is required.")
    private String code;
}
