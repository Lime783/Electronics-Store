package com.example.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    void add(Customer customer);

    void deleteByID(UUID id);

    Optional<Customer> findCustomerByID(UUID id);

    Customer getCustomerByID(UUID id);

    List<Customer> getAllCustomers();
}
