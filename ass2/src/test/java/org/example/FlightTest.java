package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class FlightTest {
    private Flight flight;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private Airplane airplane;

    @BeforeEach
    void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateFrom = new Timestamp(sdf.parse("15/10/23 12:00:00").getTime());
        dateTo = new Timestamp(sdf.parse("15/10/23 15:00:00").getTime());
        airplane = new Airplane(1, "Boeing 747", 12, 150, 10);
        FlightCollection.getFlights().clear();

        flight = new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane);
    }

    @Test
    @DisplayName("Valid Flight Creation")
    void testValidFlightCreation() {
        assertAll(
                () -> assertEquals("Sydney", flight.getDepartTo()),
                () -> assertEquals("Melbourne", flight.getDepartFrom()),
                () -> assertEquals("QF400", flight.getCode()),
                () -> assertEquals("Qantas", flight.getCompany()),
                () -> assertEquals(dateFrom, flight.getDateFrom()),
                () -> assertEquals(dateTo, flight.getDateTo()),
                () -> assertEquals(airplane, flight.getAirplane())
        );
    }

    // All fields are required
    @Test
    @DisplayName("Invalid Flight ID")
    void testInvalidFlightID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Flight(null, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane));
        assertTrue(exception.getMessage().contains("Flight id cannot be null"));
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Flight(0, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane));
        assertTrue(exception1.getMessage().contains("Flight ID must be positive"));
    }


    @Test
    @DisplayName("Invalid Departure Location")
    void testInvalidDepartureLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Flight(1, "", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane));
        assertTrue(exception.getMessage().contains("Departure to location cannot be empty"));
    }

    @Test
    @DisplayName("Invalid Arrival Location")
    void testInvalidArrivalLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Flight(1, "Sydney", "", "QF400", "Qantas", dateFrom, dateTo, airplane));
        assertTrue(exception.getMessage().contains("Departure from location cannot be empty"));
    }


    @Test
    @DisplayName("Null Airplane Object")
    void testNullAirplaneObject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, null);
        });
        assertTrue(exception.getMessage().contains("Airplane cannot be null"));
    }

    @Test
    @DisplayName("Null Code Object")
    void testNullCodeObject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(1, "Sydney", "Melbourne", "", "Qantas", dateFrom, dateTo, null);
        });
        assertTrue(exception.getMessage().contains("Flight code cannot be empty"));
    }

    @Test
    @DisplayName("Null Company Object")
    void testNullCompanyObject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(1, "Sydney", "Melbourne", "QF400", "", dateFrom, dateTo, null);
        });
        assertTrue(exception.getMessage().contains("Company cannot be empty"));
    }


    // Ensure the same flight is not already in the system
    @Test
    @DisplayName("Check Flight Uniqueness")
    void testFlightUniqueness() throws Exception {
        FlightCollection.addFlights(new ArrayList<>(List.of(flight)));
        Timestamp date = new Timestamp(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("15/10/23 12:00:00").getTime());
        Airplane airplane = new Airplane(2, "Airbus A380", 12, 200, 10);

        // 尝试添加重复的航班
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane);
        });
        assertTrue(exception.getMessage().contains("A flight with the same details already exists in the system."));
    }

    //2. Date must be in DD/MM/YY format.
    //3. Time must be in HH:MM:SS format.
    private Timestamp parseTimestamp(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        sdf.setLenient(false); // 强制执行精确的日期匹配
        Date date = sdf.parse(dateString);
        return new Timestamp(date.getTime());
    }

    @Test
    void testInvalidDateInput() {
        Airplane airplane = new Airplane(1, "Boeing 747", 20, 150, 10); // 举例的飞机对象
        String incorrectDateString = "15/10 12:00"; // 故意错误的日期格式

        Exception parseException = assertThrows(ParseException.class, () -> {
            Timestamp dateFrom = parseTimestamp(incorrectDateString);
            Timestamp dateTo = parseTimestamp("15/10/23 15:00:00"); // 正确的日期格式
            new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane);
        });

        assertTrue(parseException.getMessage().contains("Unparseable date"), "Should fail to parse incomplete date string");
    }


    // other tests
    @Test
    @DisplayName("Test toString Output")
    void testToStringOutput() {
        String expectedOutput = "Flight{airplane=Airplane{model='Boeing 747', business sits=12, economy sits=150, crew sits=10}, date to='" + dateTo + "', date from='" + dateFrom + "', depart from='Melbourne', depart to='Sydney', code='QF400', company='Qantas'}";
        assertEquals(expectedOutput, flight.toString());
    }



    // ass2 test
    @Test
    @DisplayName("Test Flight Uniqueness with Different IDs")
    void testFlightUniquenessWithDifferentIDs() {
        Airplane airplane = new Airplane(2, "Airbus A380", 12, 200, 10);

        // 添加一个具有相同详细信息但不同 ID 的航班
        Flight newFlight = new Flight(2, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane);
        FlightCollection.addFlights(new ArrayList<>(List.of(newFlight)));

        Exception exception = assertThrows(IllegalStateException.class, () -> new Flight(3, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane));
        assertTrue(exception.getMessage().contains("A flight with the same details already exists in the system."));
    }

}
