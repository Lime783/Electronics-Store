package com.example.customer;

import java.util.List;
import java.util.Optional;

public class CustomerManager {
    private final InMemoryCustomerRepository customerRepository;

    public CustomerManager() {
        customerRepository = new InMemoryCustomerRepository();
    }

    public void addCustomerToDatabase(Customer customer) {
        customerRepository.add(customer);
    }

    public void removeCustomerFromDatabase(Customer customer) {
        customerRepository.deleteByID(customer.getId());
    }

    public Optional<Customer> findCustomerById(Customer customer) {
        return customerRepository.findCustomerByID(customer.getId());
    }

    public Customer getCustomerById(Customer customer) {
        return customerRepository.getCustomerByID(customer.getId());
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
