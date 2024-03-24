package com.kampus.kbazaar.cart;

import java.util.Set;

public record ApplyPromotionRequest(String code, Set<String> productSkus) {}
