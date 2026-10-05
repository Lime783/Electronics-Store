package com.example.invoice;

import com.example.order.Order;

public class InvoiceManager {
    public Invoice generateInvoice(Order order) {
        return new Invoice(order);
    }
}
