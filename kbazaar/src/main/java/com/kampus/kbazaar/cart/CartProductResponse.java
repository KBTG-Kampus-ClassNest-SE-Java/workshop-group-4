package com.kampus.kbazaar.cart;

import java.math.BigDecimal;

public record CartProductResponse(String sku, String name, Integer quantity, BigDecimal price) {}
