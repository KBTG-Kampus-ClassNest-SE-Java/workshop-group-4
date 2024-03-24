package com.kampus.kbazaar.product;

import java.util.List;

public record GetProductResponse(
        List<Product> contents, int pageNumber, int pageSize, int totalPages, int totalElements) {}
