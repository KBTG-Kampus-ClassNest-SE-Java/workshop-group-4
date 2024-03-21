package com.kampus.kbazaar.product;

import jdk.jfr.Name;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldBeAbleToGetAllProducts() {
        // Mock data
        Product product1 = new Product(1L, "Google Pixel 5", "MOBILE-GOOGLE-PIXEL-5", 12990.00, 100);
        Product product2 = new Product(2L, "Coca-Cola", "BEV-COCA-COLA", 20.00, 150);
        List<Product> productList = Arrays.asList(product1, product2);

        // Mock repository method
        when(productRepository.findAll()).thenReturn(productList);

        // Call service method
        List<ProductResponse> result = productService.getAll();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("Google Pixel 5", result.get(0).name());
        assertEquals("MOBILE-GOOGLE-PIXEL-5", result.get(1).sku());
    }

    @Test
    void testShouldReturnEmptyListWhenNoProductFoundGetAllProducts() {
        // Mock repository method returning empty list
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        // Call service method
        List<ProductResponse> result = productService.getAll();

        // Assertions
        assertTrue(result.isEmpty());
    }

    @Test
    void testShouldBeAbleToGetProductBySku() {
        // Mock data
        Product product = new Product(1L, "Pens", "STATIONERY-PEN-BIC-BALLPOINT", 14.99, 100);

        // Mock repository method
        when(productRepository.findBySku("STATIONERY-PEN-BIC-BALLPOINT")).thenReturn(Optional.of(product));

        // Call service method
        ProductResponse result = productService.getBySku("STATIONERY-PEN-BIC-BALLPOINT");

        // Assertions
        assertEquals("Pens", result.name());
        assertEquals(14.99, result.price());
    }

    @Test
    void testShouldReturnNullWhenGetProductNonExistingSKU() {
        // Mock repository method returning empty optional
        when(productRepository.findBySku(anyString())).thenReturn(Optional.empty());

        // Call service method
        ProductResponse result = productService.getBySku("NonExistingSKU");

        // Assertions
        assertNull(result);
    }
}
