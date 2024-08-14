package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testValidPersonCreation() {
        Person person = new Person("John", "Doe", 30, "Man");
        assertAll("Valid person creation",
                () -> assertEquals("John", person.getFirstName()),
                () -> assertEquals("Doe", person.getSecondName()),
                () -> assertEquals(30, person.getAge()),
                () -> assertEquals("Man", person.getGender())
        );
    }

    // All fields of a Person class are required to create a person
    @Test
    void testNullInput() {
        Exception exception0 = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", null, "Man"));
        assertTrue(exception0.getMessage().contains("Age cannot be null"));
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", 30, ""));
        assertTrue(exception1.getMessage().contains("Invalid gender provided"));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Person("", "Doe", 30, "Man"));
        assertTrue(exception2.getMessage().contains("First name must start with a letter"));
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new Person("John", "", 30, "Man"));
        assertTrue(exception3.getMessage().contains("Second name must start with a letter"));
    }

    // The gender field has following options ‘Woman’, ‘Man’, ’Non-binary | gender diverse’, ‘Prefer not to say’ and ‘Other'
    @Test
    void testInvalidGender() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", 30, "Alien"));
        assertTrue(exception.getMessage().contains("Invalid gender provided"));
    }

    //  The first name and last name should not start with a number or symbol and can contain only lower-case and upper-case alphabet letters
    @Test
    void testInvalidFirstName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Person("1John", "Doe", 30, "Man"));
        assertTrue(exception.getMessage().contains("First name must start with a letter"));
    }

    @Test
    void testInvalidSecondName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Person("John", "2Doe", 30, "Man"));
        assertTrue(exception.getMessage().contains("Second name must start with a letter"));
    }


    @Test
    void testToStringOutput() {
        Person person = new Person("John", "Doe", 30, "Man");
        String expectedOutput = "Person{firstName='John', secondName='Doe', age=30, gender='Man'}";
        assertEquals(expectedOutput, person.toString());
    }

    @Test
    void testInvalidAge() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", -1, "Man"));
        assertTrue(exception1.getMessage().contains("Age must be between 0 and 150"));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", 151, "Man"));
        assertTrue(exception2.getMessage().contains("Age must be between 0 and 150"));
    }

    // ass2 test
    @Test
    void testAgeBoundaries() {
        // 测试年龄低于有效范围
        Exception exceptionLowerBound = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", -1, "Man"));
        assertTrue(exceptionLowerBound.getMessage().contains("Age must be between 0 and 150"));

        // 测试年龄高于有效范围
        Exception exceptionUpperBound = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", 151, "Man"));
        assertTrue(exceptionUpperBound.getMessage().contains("Age must be between 0 and 150"));

        // 测试边界值
        Person personLowerEdge = new Person("John", "Doe", 0, "Man");
        assertEquals(0, personLowerEdge.getAge(), "Age at lower boundary should be valid");

        Person personUpperEdge = new Person("John", "Doe", 150, "Man");
        assertEquals(150, personUpperEdge.getAge(), "Age at upper boundary should be valid");
    }


}
