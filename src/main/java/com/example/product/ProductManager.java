package com.example.product;

import java.util.List;

public class ProductManager {
    private final InMemoryProductRepository productRepository;

    public ProductManager() {
        productRepository = new InMemoryProductRepository();
    }

    public void addProductToDataBase(Product product) {
        productRepository.add(product);
    }

    public void removeProductFromDataBase(Product product) {
        productRepository.deleteByID(product.getId());
    }

    public List<Product> getAllProductsFromDataBase() {
        return productRepository.getAllProducts();
    }
}
