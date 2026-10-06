package com.example.shop.customer;

import com.example.shop.customer.Customer;
import com.example.shop.customer.CustomerManager;
import com.example.shop.customer.InMemoryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class CustomerTest {

    private CustomerManager customerManager;
    private InMemoryCustomerRepository customerRepository;

    private static final String firstName = "Jan";
    private static final String lastName = "Chrzan";
    private static final String email = "Jan@Chrzan.pl";
    private static final String password = "123456";
    private static final String phoneNumber = "123456789";

    @BeforeEach
    void setUp() {
        customerRepository = new InMemoryCustomerRepository();
        customerManager = new CustomerManager(customerRepository);
    }

    @Test
    void shouldCreateCustomerSuccessfully() {
        // Given
        Customer customer = new Customer(firstName, lastName, email, password, phoneNumber);

        // When
        customerManager.addCustomerToDatabase(customer);

        // Then
        assertThat(customer).isEqualTo(customerManager.getCustomerById(customer.getId()));
    }

    @Test
    void shouldThrowExceptionWhenCustomerHasInvalidFirstName() {
        assertThatThrownBy(() -> new Customer("a", lastName, email, password, phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("First name must be at least ");
    }

    @Test
    void shouldThrowExceptionWhenCustomerHasInvalidLastName() {
        assertThatThrownBy(() -> new Customer(firstName, "a", email, password, phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Last name must be at least ");
    }

    @Test
    void shouldThrowExceptionWhenCustomerHasInvalidEmail() {
        assertThatThrownBy(() -> new Customer(firstName, lastName, "Bad@Email", password, phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid email");
    }

    @Test
    void shouldThrowExceptionWhenCustomerHasInvalidPassword() {
        assertThatThrownBy(() -> new Customer(firstName, lastName, email, "short", phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password must be at least ");
    }

    @Test
    void shouldThrowExceptionWhenCustomerHasInvalidPhoneNumber() {
        assertThatThrownBy(() -> new Customer(firstName, lastName, email, password, "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid phone number");
    }
}