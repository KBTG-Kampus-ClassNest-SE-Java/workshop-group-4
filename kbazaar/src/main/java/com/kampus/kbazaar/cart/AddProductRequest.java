package com.kampus.kbazaar.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductRequest {
    @NotNull private String productSku;
    @NotNull private Integer quantity;
}
