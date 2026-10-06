package com.example.shop.cart;

import com.example.shop.cart.Cart;
import com.example.shop.cart.CartManager;
import com.example.shop.cart.InMemoryCartRepository;
import com.example.shop.product.Product;
import com.example.shop.product.smartphone.Smartphone;
import com.example.shop.product.smartphone.components.Accessory;
import com.example.shop.product.smartphone.components.BatteryCapacity;
import com.example.shop.product.smartphone.components.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Nested
    class DataBaseTests {

        @Test
        void shouldAddCartToDataBase() {
            // When
            cartManager.addCartToDataBase(cart);

            // Then
            assertThat(cart).isEqualTo(inMemoryCartRepository.getCartByID(cart.getId()));
        }

        @Test
        void shouldGetCartFromDataBase() {
            // Given
            cartManager.addCartToDataBase(cart);

            // When
            Cart cartTest = cartManager.getCartById(cart.getId());

            // Then
            assertThat(cartTest).isEqualTo(cart);
        }

        @Test
        void shouldGetAllCartsFromDataBase() {
            // Given
            List<Cart> carts = new ArrayList<>(List.of(cart, cart));

            // When
            cartManager.addCartToDataBase(cart);
            cartManager.addCartToDataBase(cart);

            // Then
            assertThat(carts).isEqualTo(inMemoryCartRepository.getAllCarts());
        }

        @Test
        void shouldReturnEmptyOptionalWhenCartDoesNotExistInDataBase() {
            assertThat(inMemoryCartRepository.findCartByID(new UUID(0L, 0L))).isEmpty();
        }

        @Test
        void shouldRemoveCartFromDataBase() {
            // Given
            cartManager.addCartToDataBase(cart);

            // When
            cartManager.removeCartFromDataBase(cart);

            // Then
            assertThat(inMemoryCartRepository.findCartByID(cart.getId())).isEmpty();
        }
    }

    @Nested
    class CartAndProductsTests {

        @Test
        void shouldAddProductToCart() {
            // When
            cartManager.addProductToCart(correctSmartphone, cart);

            // Then
            assertThat(correctSmartphone).isEqualTo(cartManager.getProductFromCart(correctSmartphone, cart));
        }

        @Test
        void shouldRemoveProductFromCart() {
            // Given
            cartManager.addProductToCart(correctSmartphone, cart);

            // When
            cartManager.removeProductFromCart(correctSmartphone, cart);

            // Then
            assertThat(cartManager.findProducFromCart(correctSmartphone, cart)).isEmpty();
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
    }
}