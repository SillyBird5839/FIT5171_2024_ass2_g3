package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AirplaneTest {

    @Test
    void testValidAirplaneCreation() {
        Airplane airplane = new Airplane(1, "Boeing 747", 12, 150, 10);
        assertAll("Verify all properties",
                () -> assertEquals(1, airplane.getAirplaneID()),
                () -> assertEquals("Boeing 747", airplane.getAirplaneModel()),
                () -> assertEquals(12, airplane.getBusinessSitsNumber()),
                () -> assertEquals(150, airplane.getEconomySitsNumber()),
                () -> assertEquals(10, airplane.getCrewSitsNumber())
        );
    }

    // Ensure all fields/details for an airplane like airplaneID, businessSitsNumber, crewSitsNumber, etc. are tested
    @Test
    void testInvalidAirplaneID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(-1, "Boeing 747", 50, 150, 10);
        });
        assertTrue(exception.getMessage().contains("Airplane ID must be positive"));
    }

    @Test
    void testInvalidAirplaneModel() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, null, 50, 150, 10);
        });
        assertTrue(exception.getMessage().contains("Airplane model cannot be null or empty"));
    }

    @Test
    void testNullSeatsNumberAndID() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", null, 150, 10);
        });
        assertTrue(exception1.getMessage().contains("BusinessSitsNumber cannot be null"));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 15, null, 10);
        });
        assertTrue(exception2.getMessage().contains("EconomySitsNumber cannot be null"));
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 15, 270, null);
        });
        assertTrue(exception3.getMessage().contains("cannot be null"));
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(null, "Boeing 747", 15, 150, 15);
        });
        assertTrue(exception4.getMessage().contains("airplaneID cannot be null"));
    }

    // Ensure all fields/details for an airplane like airplaneID, businessSitsNumber, crewSitsNumber, etc. are tested
    // Seat number must be in the range [1, 300]
    // 0-20 for Business sits, 0-10 for crew sits, 0-270 for economy sits
    @Test
    void testInvalidSeatsNumber() {
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 25, 150, 10);
        });
        assertTrue(exception2.getMessage().contains("must be between 0 and 20"));
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 15, 310, 10);
        });
        assertTrue(exception3.getMessage().contains("must be between 0 and 270"));
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 15, 150, 15);
        });
        assertTrue(exception4.getMessage().contains("must be between 1 and 10"));
    }


    //other tests
    @Test
    void testToStringMethod() {
        Airplane airplane = new Airplane(1, "Boeing 747", 12, 150, 10);
        String expectedString = "Airplane{model='Boeing 747', business sits=12, economy sits=150, crew sits=10}";
        assertEquals(expectedString, airplane.toString());
    }

    // ass2 test
    @Test
    void testAirplaneBoundarySeats() {
        assertThrows(IllegalArgumentException.class, () -> new Airplane(1, "Model", 21, 271, 11),
                "Should throw an exception for out-of-bound seats numbers.");
    }

    @Test
    void testAirplaneNegativeSeats() {
        assertThrows(IllegalArgumentException.class, () -> new Airplane(1, "Model", -1, -1, -1),
                "Should throw an exception for negative seats numbers.");
    }

}
