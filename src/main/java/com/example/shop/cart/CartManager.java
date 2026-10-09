package com.example.shop.cart;

import com.example.shop.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Cart getCartById(UUID id) {
        return inMemoryCartRepository.getCartByID(id);
    }

    public List<Cart> getAllCartsFromDataBase(){
        return inMemoryCartRepository.getAllCarts();
    }

    public void addProductToCart(Product product, Cart cart) {
        product.subtractAmountAvailable(1);
        cart.getProducts().add(product);
        cart.setValue(cart.getValue().add(product.getPrice()));
    }

    public void removeProductFromCart(Product product, Cart cart) {
        product.addAmountAvailable(1);
        cart.getProducts().remove(product);
        cart.setValue(cart.getValue().subtract(product.getPrice()));
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
