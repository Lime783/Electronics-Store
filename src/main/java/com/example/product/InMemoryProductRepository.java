package com.example.product;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products;

    public InMemoryProductRepository() {
        products = new ArrayList<>();
    }

    public void add(Product productToAdd) {
        products.add(productToAdd);
    }

    @Override
    public void deleteByID(UUID id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    @Override
    public Optional<Product> findProductByID(UUID id) {
        return products.stream()
                .filter(productToFind -> productToFind.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product getProductByID(UUID id) {
        return findProductByID(id).orElseThrow();
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }
}
