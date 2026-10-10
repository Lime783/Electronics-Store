package com.example.shop.cart;

import com.example.exceptions.DuplicateException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class InMemoryCartRepository implements CartRepository {
    private final List<Cart> carts;

    public InMemoryCartRepository() {
        carts = new ArrayList<>();
    }

    @Override
    public void add(Cart cart) {
        if (carts.contains(cart)) {
            throw new DuplicateException("Cart " + cart.getId() + " already exists");
        }
        carts.add(cart);
    }

    @Override
    public void deleteByID(UUID id) {
        carts.removeIf(product -> product.getId().equals(id));
    }

    @Override
    public Optional<Cart> findCartByID(UUID id) {
        for (Cart cart : carts) {
            if (cart.getId().equals(id)) {
                return Optional.of(cart);
            }
        }
        return Optional.empty();
    }

    @Override
    public Cart getCartByID(UUID id) {
        return findCartByID(id).orElseThrow();
    }

    @Override
    public List<Cart> getAllCarts() {
        return carts;
    }
}
