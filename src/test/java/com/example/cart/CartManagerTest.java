package com.example.cart;

import com.example.product.Product;
import com.example.product.smartphone.Smartphone;
import com.example.product.smartphone.components.Accessory;
import com.example.product.smartphone.components.BatteryCapacity;
import com.example.product.smartphone.components.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CartManagerTest {

    private InMemoryCartRepository inMemoryCartRepository;
    private CartManager cartManager;
    private Cart cart;
    private static Product correctSmartphone;

    @BeforeAll
    static void init() {
        correctSmartphone = new Smartphone("trapPhone",
                new BigDecimal("420.00"),
                10,
                Accessory.CASE,
                BatteryCapacity.CAPACITY_1000MAH,
                Color.RED);
    }

    @BeforeEach
    void setUp() {
        cart = new Cart();
        inMemoryCartRepository = new InMemoryCartRepository();
        cartManager = new CartManager(inMemoryCartRepository);
    }

    @Test
    void shouldAddCartToDataBase() {
        // When
        cartManager.addCartToDataBase(cart);

        // Then
        assertThat(cart).isEqualTo(inMemoryCartRepository.getCartByID(cart.getId()));
    }

    @Test
    void shouldAddProductToCart() {
        // When
        cartManager.addProductToCart(correctSmartphone, cart);

        // Then
        assertThat(correctSmartphone).isEqualTo(cartManager.getProductFromCart(correctSmartphone, cart));
    }

    @Test
    void shouldGetAllProductsFromCart() {
        // Given
        List<Product> smartphones = List.of(correctSmartphone, correctSmartphone);

        // When
        cartManager.addProductToCart(correctSmartphone, cart);
        cartManager.addProductToCart(correctSmartphone, cart);

        // Then
        assertThat(smartphones).isEqualTo(cartManager.getAllProductsFromCart(cart));
    }

    @Test
    void shouldAddAllProductValuesSuccessfully() {
        // Given
        List<Product> smartphones = List.of(correctSmartphone, correctSmartphone);
        BigDecimal value = smartphones.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // When
        cartManager.addProductToCart(correctSmartphone, cart);
        cartManager.addProductToCart(correctSmartphone, cart);

        // Then
        assertThat(value).isEqualTo(cart.getValue());
    }

    @Test
    void shouldReturnEmptyOptionalWhenCartDoesNotExistInDataBase() {
        assertThat(inMemoryCartRepository.findCartByID(new UUID(0L, 0L))).isEmpty();
    }
}