package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightCollectionTest {
    private Flight flight1;
    private Flight flight2;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private Airplane airplane1;
    private Airplane airplane2;

    @BeforeEach
    void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateFrom = new Timestamp(sdf.parse("15/10/23 12:00:00").getTime());
        dateTo = new Timestamp(sdf.parse("15/10/23 15:00:00").getTime());
        airplane1 = new Airplane(1, "Boeing 747", 12, 150, 10);
        airplane2 = new Airplane(2, "Airbus A380", 20, 200, 5);

        // clear the collection
        FlightCollection.getFlights().clear();

        flight1 = new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane1);
        flight2 = new Flight(2, "New York", "London", "BA100", "British Airways", dateFrom, dateTo, airplane2);

    }

    @Test
    @DisplayName("Add Valid Flights")
    void testAddValidFlights() {
        ArrayList<Flight> newFlights = new ArrayList<>();
        newFlights.add(flight1);
        newFlights.add(flight2);
        FlightCollection.addFlights(newFlights);

        assertEquals(2, FlightCollection.getFlights().size());
    }

    // When adding a flight into the system, test if it conforms with the requirement as a flight and Flight Collection
    @Test
    @DisplayName("Add Null Flights List")
    void testAddNullFlightsList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> FlightCollection.addFlights(null));
        assertTrue(exception.getMessage().contains("Cannot add null list of flights."));
    }

    //  Valid city names must be used
    @Test
    @DisplayName("Add Invalid Flight")
    void testAddInvalidFlight() {
        Flight invalidFlight = new Flight(3, "InvalidCity", "Melbourne", "QF500", "Qantas", dateFrom, dateTo, airplane1);
        ArrayList<Flight> newFlights = new ArrayList<>();
        newFlights.add(invalidFlight);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> FlightCollection.addFlights(newFlights));
        assertTrue(exception.getMessage().contains("Invalid flight data."));
    }


    // When trying to get flight information, a valid flight is returned
    @Test
    @DisplayName("Get Flight by Cities")
    void testGetFlightByCities() {
        ArrayList<Flight> newFlights = new ArrayList<>();
        newFlights.add(flight1);
        newFlights.add(flight2);
        FlightCollection.addFlights(newFlights);
        Flight result = FlightCollection.getFlightInfo("Sydney", "Melbourne");
        assertNotNull(result);
        assertEquals(flight1, result);
    }

    @Test
    @DisplayName("Get Flight by City")
    void testGetFlightByCity() {
        ArrayList<Flight> newFlights = new ArrayList<>();
        newFlights.add(flight1);
        newFlights.add(flight2);
        FlightCollection.addFlights(newFlights);
        Flight result = FlightCollection.getFlightInfo("Sydney");
        assertNotNull(result);
        assertEquals(flight1, result);
    }

    @Test
    @DisplayName("Get Flight by ID")
    void testGetFlightByID() {
        ArrayList<Flight> newFlights = new ArrayList<>();
        newFlights.add(flight1);
        newFlights.add(flight2);
        FlightCollection.addFlights(newFlights);
        Flight result = FlightCollection.getFlightInfo(1);
        assertNotNull(result);
        assertEquals(flight1, result);
    }

    @Test
    @DisplayName("Get Non-existent Flight")
    void testGetNonExistentFlight() {
        Flight result = FlightCollection.getFlightInfo("NonExistentCity");
        assertNull(result);
        result = FlightCollection.getFlightInfo(999);
        assertNull(result);
    }


    // ass2 test
    @Test
    @DisplayName("Get Flight by City with no match")
    void testGetFlightByCityNoMatch() {
        ArrayList<Flight> newFlights = new ArrayList<>();
        newFlights.add(flight1);
        newFlights.add(flight2);
        FlightCollection.addFlights(newFlights);
        Flight result = FlightCollection.getFlightInfo("NotARealCity");
        assertNull(result, "Should return null when no city matches");
    }

}
