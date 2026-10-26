package com.example.shop.product;

import com.example.exceptions.DuplicateException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    private final ConcurrentHashMap<UUID, Product> products;

    public InMemoryProductRepository() {
        products = new ConcurrentHashMap<>();
    }

    public void add(Product productToAdd) {
        checkForDuplicates(productToAdd);
        products.put(productToAdd.getId(), productToAdd);
    }

    private void checkForDuplicates(Product productToAdd) {
        Optional<Product> duplicate = products.values().stream()
                .filter(product -> product.getId().equals(productToAdd.getId()))
                .findFirst();

        if (duplicate.isPresent()) {
            throw new DuplicateException(productToAdd.getName() + " already exists");
        }
    }

    @Override
    public void deleteByID(UUID id) {
        products.remove(id);
    }

    @Override
    public Optional<Product> findProductByID(UUID id) {
        return products.values().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product getProductByID(UUID id) {
        return findProductByID(id).orElseThrow();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }
}