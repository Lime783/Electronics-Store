package com.example.shop.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    void add(Product product);

    void deleteByID(UUID id);

    Optional<Product> findProductByID(UUID id);

    Product getProductByID(UUID id);

    List<Product> getAllProducts();
}
