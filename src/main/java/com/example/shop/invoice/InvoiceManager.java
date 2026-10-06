package com.example.shop.invoice;

import com.example.shop.order.Order;

public class InvoiceManager {
    public Invoice generateInvoice(Order order) {
        return new Invoice(order);
    }
}
