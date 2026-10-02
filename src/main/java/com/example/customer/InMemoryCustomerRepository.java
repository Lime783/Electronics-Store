package com.example.customer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class InMemoryCustomerRepository implements CustomerRepository {
    private final List<Customer> customers;

    public InMemoryCustomerRepository() {
        this.customers = new ArrayList<>();
    }

    @Override
    public void add(Customer customerToAdd) {
        customers.add(customerToAdd);
    }

    @Override
    public void deleteByID(UUID id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }

    @Override
    public Optional<Customer> findCustomerByID(UUID id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    @Override
    public Customer getCustomerByID(UUID id) {
        return findCustomerByID(id).orElseThrow();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }
}
