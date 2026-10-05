package com.example.product;

import com.example.product.smartphone.Smartphone;
import com.example.product.smartphone.components.Accessory;
import com.example.product.smartphone.components.BatteryCapacity;
import com.example.product.smartphone.components.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {

    private ProductManager productManager;
    private InMemoryProductRepository productRepository;
    private static final Product correctSmartphone = new Smartphone("trapPhone", new BigDecimal("4200"), 1, Accessory.CASE, BatteryCapacity.CAPACITY_1000MAH, Color.BLACK);

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        productManager = new ProductManager(productRepository);
    }

    @Test
    void shouldAddProductToDatabase() {
        // When
        productManager.addProductToDataBase(correctSmartphone);

        // Then
        assertThat(correctSmartphone).isEqualTo(productManager.getProductById(correctSmartphone.getId()));
    }

    @Test
    void shouldGetProductFromDatabase() {
        // Given
        productManager.addProductToDataBase(correctSmartphone);

        // When
        Product product = productManager.getProductById(correctSmartphone.getId());

        // Then
        assertThat(product).isEqualTo(correctSmartphone);
    }

    @Test
    void shouldReturnEmptyOptionalWhenProductNotFoundInDatabase() {
        // When
        Optional<Product> product = productManager.findProductByID(correctSmartphone.getId());

        // Then
        assertThat(product).isEmpty();
    }

    @Test
    void shouldRemoveProductFromDatabase() {
        // Given
        productManager.addProductToDataBase(correctSmartphone);

        // When
        productManager.removeProductFromDataBase(correctSmartphone);

        // Then
        assertThat(productManager.findProductByID(correctSmartphone.getId())).isEmpty();
    }
}