package com.example.cart;

import com.example.product.Product;
import com.example.product.smartphone.Smartphone;
import com.example.product.smartphone.components.Accessory;
import com.example.product.smartphone.components.BatteryCapacity;
import com.example.product.smartphone.components.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CartManagerTest {

    private InMemoryCartRepository inMemoryCartRepository;
    private CartManager cartManager;
    private Cart cart;

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
        // Given
        String name = "trapPhone";
        BigDecimal price = new BigDecimal("420.00");
        int amountAvailable = 10;
        Accessory accessory = Accessory.CASE;
        BatteryCapacity batteryCapacity = BatteryCapacity.CAPACITY_1000MAH;
        Color color = Color.RED;
        Product smartphone = new Smartphone(name, price, amountAvailable, accessory, batteryCapacity, color);

        // When
        cartManager.addProductToCart(smartphone, cart);

        // Then
        assertThat(smartphone).isEqualTo(cartManager.getProductFromCart(smartphone, cart));
    }

    @Test
    void shouldGetAllProductsFromCart() {
        // Given
        String name = "trapPhone";
        BigDecimal price = new BigDecimal("420.00");
        int amountAvailable = 10;
        Accessory accessory = Accessory.CASE;
        BatteryCapacity batteryCapacity = BatteryCapacity.CAPACITY_1000MAH;
        Color color = Color.RED;
        Product smartphone1 = new Smartphone(name, price, amountAvailable, accessory, batteryCapacity, color);
        Product smartphone2 = new Smartphone(name, price, amountAvailable, accessory, batteryCapacity, color);
        List<Product> smartphones = List.of(smartphone1, smartphone2);

        // When
        cartManager.addProductToCart(smartphone1, cart);
        cartManager.addProductToCart(smartphone2, cart);

        // Then
        assertThat(smartphones).isEqualTo(cartManager.getAllProductsFromCart(cart));
    }
}