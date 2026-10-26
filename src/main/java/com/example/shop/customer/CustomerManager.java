package com.example.shop.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerManager {
    private final CustomerRepository customerRepository;

    public CustomerManager(CustomerRepository customerRepository) {
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
        customer.changeEmail(newEmail);
    }

    public void changeCustomerPhoneNumber(Customer customer, String newPhoneNumber) {
        customer.changePhoneNumber(newPhoneNumber);
    }

    public void changeCustomerPassword(Customer customer, String newPassword) {
        customer.changePassword(newPassword);
    }
}
