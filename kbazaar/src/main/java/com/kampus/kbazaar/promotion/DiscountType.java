package com.kampus.kbazaar.promotion;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DiscountType {
    FIXED_AMOUNT("FIXED_AMOUNT"),
    PERCENTAGE("PERCENTAGE"),

    BUY1_GET1("buy1_get1"),
    BUY2_GET1("buy2_get1");

    private String value;

    DiscountType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }
}
