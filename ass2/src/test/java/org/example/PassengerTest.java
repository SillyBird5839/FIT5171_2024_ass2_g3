package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        // 正确初始化一个Passenger对象用于大部分测试
        passenger = new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123);
    }

    @Test
    @DisplayName("Valid Passenger Creation")
    void testValidPassengerCreation() {
        assertAll(
                () -> assertEquals("John", passenger.getFirstName()),
                () -> assertEquals("Doe", passenger.getSecondName()),
                () -> assertEquals(30, passenger.getAge()),
                () -> assertEquals("Man", passenger.getGender()),
                () -> assertEquals("john.doe@example.com", passenger.getEmail()),
                () -> assertEquals("0412345678", passenger.getPhoneNumber()),
                () -> assertEquals("A1234567", passenger.getPassport()),
                () -> assertEquals("4485364739527352", passenger.getCardNumber()),
                () -> assertEquals(123, passenger.getSecurityCode())
        );
    }


    // All fields of a passenger are required
    // When a passenger is being added, it must include the passenger’s first name, last name, age, an gender following the person who is becoming a passenger
    @Test
    @DisplayName("Null Input")
    void testNullInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123));
        assertTrue(exception.getMessage().contains("First name must start with a letter"));
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123));
        assertTrue(exception1.getMessage().contains("Second name must start with a letter"));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", null, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123));
        assertTrue(exception2.getMessage().contains("Age cannot be null"));
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123));
        assertTrue(exception3.getMessage().contains("Invalid gender provided"));
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "", "0412345678", "A1234567", "4485364739527352", 123));
        assertTrue(exception4.getMessage().contains("Email address is invalid"));
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "", "A1234567", "4485364739527352", 123));
        assertTrue(exception5.getMessage().contains("Phone number is invalid"));
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "", "4485364739527352", 123));
        assertTrue(exception6.getMessage().contains("Passport number must be 9 characters or less"));
        Exception exception7 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", null, 123));
        assertTrue(exception7.getMessage().contains("Card number cannot be null"));
        Exception exception8 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", null));
        assertTrue(exception8.getMessage().contains("securityCode cannot be null"));
    }


    // Phone numbers follow a pattern. Within Australia, mobile phone numbers begin with 04 or 05 – the Australian national trunk code" 0, plus the mobile indicator 4 or 5, then followed by eight digits. This is generally written as 04XX XXX XXX within Australia or as +61 4XX XXX XXX for an international audience
    @Test
    @DisplayName("Invalid Phone Number")
    void testInvalidPhoneNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0123456789", "A1234567", "4485364739527352", 123));
        assertTrue(exception.getMessage().contains("Phone number is invalid"));
    }

    //  The email follows a valid pattern “abc@domain.com”.s
    @Test
    @DisplayName("Invalid Email")
    void testInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "invalid_email", "0412345678", "A1234567", "4485364739527352", 123));
        assertTrue(exception.getMessage().contains("Email address is invalid"));
    }


    // The passport number should not be more than 9 characters long
    @Test
    @DisplayName("Invalid Passport Number")
    void testInvalidPassportNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567890", "4485364739527352", 123));
        assertTrue(exception.getMessage().contains("Passport number must be 9 characters or less"));
    }


    // other tests
    @Test
    @DisplayName("Invalid Credit Card Number")
    void testInvalidCreditCardNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "invalidcard", 123));
        assertTrue(exception.getMessage().contains("Invalid credit card number"));
    }

    @Test
    @DisplayName("Invalid Security Code")
    void testInvalidSecurityCode() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 12));
        assertTrue(exception1.getMessage().contains("Security code must be 3 or 4 digits"));
    }

    @Test
    @DisplayName("Test toString method for correct format")
    void testToStringMethod() {
        Passenger passenger = new Passenger("Alice", "Wonderland", 28, "Woman", "alice@example.com", "0412345678", "B123456", "4485364739527352", 123);
        String expectedString = "Passenger{ Fullname= Alice Wonderland, email='alice@example.com', phoneNumber='0412345678', passport='B123456'}";
        assertEquals(expectedString, passenger.toString(), "toString method does not return the correct format.");
    }
}
