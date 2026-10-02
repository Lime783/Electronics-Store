package com.example.customer;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Customer {
    private static final String PHONE_PATTERN = "^\\+?[0-9][0-9\\s-]{7,19}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    public Customer(String firstName, String lastName, String email, String password, String phoneNumber) {
        requireAllData(firstName, lastName, email, password, phoneNumber);
        validateAllData(firstName, lastName, email, password, phoneNumber);

        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    private void requireAllData(String firstName, String lastName, String email, String password, String phone) {
        Objects.requireNonNull(firstName, "First name is required");
        Objects.requireNonNull(lastName, "Last name is required");
        Objects.requireNonNull(email, "Email is required");
        Objects.requireNonNull(password, "Password is required");
        Objects.requireNonNull(phone, "Phone number is required");
    }

    private void validateAllData(String firstName, String lastName, String email, String password, String phoneNumber) {
        validateFirstName(firstName);
        validateLastName(lastName);
        validateEmail(email);
        validatePassword(password);
        validatePhoneNumber(phoneNumber);
    }

    private static void validateFirstName(String firstName) {
        if (firstName.length() < 2){
            throw new IllegalArgumentException("First name must be at least 2 characters long: "  + firstName);
        }
    }

    private static void validateLastName(String lastName) {
        if (lastName.length() < 2){
            throw new IllegalArgumentException("Last name must be at least 2 characters long: " + lastName);
        }
    }

    private static void validateEmail(String email) {
        if (!(email.matches(EMAIL_PATTERN))){
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }

    private static void validatePassword(String password) {
        if (password.length() < 6){
            throw new IllegalArgumentException("Password must be at least 6 characters long: " + password);
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        if (!(phoneNumber.matches(PHONE_PATTERN))){
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
    }
}
