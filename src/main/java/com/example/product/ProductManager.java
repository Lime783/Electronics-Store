package com.example.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductManager {
    private final InMemoryProductRepository productRepository;

    public ProductManager(InMemoryProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProductToDataBase(Product product) {
        productRepository.add(product);
    }

    public void removeProductFromDataBase(Product product) {
        productRepository.deleteByID(product.getId());
    }

    public Optional<Product> findProductByID(UUID id) {
        return productRepository.findProductByID(id);
    }

    public Product getProductById(UUID id) {
        return productRepository.getProductByID(id);
    }

    public List<Product> getAllProductsFromDataBase() {
        return productRepository.getAllProducts();
    }
}
