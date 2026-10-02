package com.example.cart;

import com.example.product.Product;

import java.util.List;
import java.util.Optional;

public class CartManager {
    private final InMemoryCartRepository inMemoryCartRepository;

    public CartManager(InMemoryCartRepository inMemoryCartRepository) {
        this.inMemoryCartRepository = inMemoryCartRepository;
    }

    public void addCartToDataBase(Cart cart){
        inMemoryCartRepository.add(cart);
    }

    public void removeCartFromDataBase(Cart cart){
        inMemoryCartRepository.deleteByID(cart.getId());
    }

    public List<Cart> getAllCartsFromDataBase(){
        return inMemoryCartRepository.getAllCarts();
    }

    public void addProductToCart(Product product, Cart cart) {
        cart.getProducts().add(product);
    }

    public void removeProductFromCart(Product product, Cart cart) {
        cart.getProducts().remove(product);
    }

    public Optional<Product> findProducFromCart(Product product, Cart cart) {
        return cart.getProducts().stream()
                .filter(productToFind -> productToFind.getId().equals(product.getId()))
                .findFirst();
    }

    public Product getProductFromCart(Product product, Cart cart) {
        return findProducFromCart(product, cart).orElseThrow();
    }

    public List<Product> getAllProductsFromCart(Cart cart) {
        return cart.getProducts();
    }

    public void listAllProductsFromCart(Cart cart) {
        getAllProductsFromCart(cart)
                .forEach(System.out::println);
    }
}
