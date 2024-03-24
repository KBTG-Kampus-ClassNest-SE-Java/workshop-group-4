package com.kampus.kbazaar.promotion;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApplicableToType {
    ENTIRE_CART("ENTIRE_CART"),
    SPECIFIC_PRODUCTS("SPECIFIC_PRODUCTS");

    private String value;

    ApplicableToType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }
}
