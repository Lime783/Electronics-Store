package com.example.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerManager {
    private final InMemoryCustomerRepository customerRepository;

    public CustomerManager(InMemoryCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomerToDatabase(Customer customer) {
        customerRepository.add(customer);
    }

    public void removeCustomerFromDatabase(Customer customer) {
        customerRepository.deleteByID(customer.getId());
    }

    public Optional<Customer> findCustomerById(UUID id) {
        return customerRepository.findCustomerByID(id);
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.getCustomerByID(id);
    }

    public List<Customer> getAllCustomersFromDatabase() {
        return customerRepository.getAllCustomers();
    }

    public void changeCustomerEmail(Customer customer, String newEmail) {
        customer.setEmail(newEmail);
    }

    public void changeCustomerPhoneNumber(Customer customer, String newPhoneNumber) {
        customer.setPhoneNumber(newPhoneNumber);
    }

    public void changeCustomerPassword(Customer customer, String newPassword) {
        customer.setPassword(newPassword);
    }
}
