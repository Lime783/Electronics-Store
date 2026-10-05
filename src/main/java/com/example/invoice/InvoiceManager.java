package com.example.invoice;

import com.example.cart.Cart;

public class InvoiceManager {
    public Invoice generateInvoice(Cart cart) {
        return new Invoice(cart);
    }
}
