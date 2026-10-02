package com.example.product;

import java.math.BigDecimal;
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

    public void changeProductPrice(Product product, BigDecimal newPrice) {
        product.setPrice(newPrice);
    }

    public void addProductAmountAvailable(Product product, int howManyToAdd) {
        product.setAmountAvailable(product.getAmountAvailable() + howManyToAdd);
    }

    public void subtractProductAmountAvailable(Product product, int howManyToSubtract) {
        if (product.getAmountAvailable() < howManyToSubtract) {
            throw new IllegalArgumentException("Not enough products available: " + product.getAmountAvailable() + ", want to substract: " + howManyToSubtract);
        }
        product.setAmountAvailable(product.getAmountAvailable() - howManyToSubtract);
    }
}
