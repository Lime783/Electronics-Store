package com.example.shop.cart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository {
    void add(Cart cart);

    void deleteByID(UUID id);

    Optional<Cart> findCartByID(UUID id);

    Cart getCartByID(UUID id);

    List<Cart> getAllCarts();
}
