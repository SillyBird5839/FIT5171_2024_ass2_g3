package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;





import static org.junit.jupiter.api.Assertions.assertTrue;



public class TicketSystemTest {
    TicketSystem ticketSystem;
    Flight flight1, flight2;
    Ticket ticket1, ticket2;
    Passenger passenger;



    @BeforeEach
    void setUp() throws Exception {
        // Setting up flights and tickets
        TicketCollection.getTickets().clear();
        FlightCollection.getFlights().clear();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp now = new Timestamp(sdf.parse("2023-07-12 12:00:00").getTime());
        Timestamp later = new Timestamp(sdf.parse("2023-07-12 15:00:00").getTime());

        flight1 = new Flight(1, "New York", "London", "BA100", "British Airways", now, later, new Airplane(1, "Boeing 747", 20, 150, 10));
        flight2 = new Flight(2, "London", "Berlin", "LH250", "Lufthansa", now, later, new Airplane(2, "Airbus A320", 20, 180, 8));
        ArrayList<Flight> newFlights = new ArrayList<>();
        newFlights.add(flight1);
        newFlights.add(flight2);
        FlightCollection.addFlights(newFlights);


        passenger = new Passenger("default", "default", 25, "Man", "default.default@default.com", "0400000000", "00000000", "4485364739527352", 111);
        ticket1 = new Ticket(1, 1000, flight1, false, passenger);
        ticket2 = new Ticket(2, 500, flight2, false, passenger);
        ArrayList<Ticket> newTickets = new ArrayList<>();
        newTickets.add(ticket1);
        newTickets.add(ticket2);
        TicketCollection.addTickets(newTickets);
    }

    @Test
    @DisplayName("Test successful transfer ticket purchase")
    void testTransferTicketPurchase() {
        // Specific input for this test
        String input = "YES\nJohn\nDoe\n25\nMan\njohn.doe@example.com\n0412345678\nA1234567\n4485364739527352\n123\nYES\nJohn\nDoe\n25\nMan\njohn.doe@example.com\n0412345678\nA1234567\n4485364739527352\n123\nYES\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.chooseTicket("New York", "Berlin");
        assertEquals(true, ticket1.ticketStatus(), "Ticket should be marked as booked for transfer.");
        assertEquals(true, ticket2.ticketStatus(), "Ticket should be marked as booked for transfer.");
        assertNotNull(ticket1.getPassenger(), "Passenger should be set for ticket1 in transfer.");
        assertNotNull(ticket2.getPassenger(), "Passenger should be set for ticket2 in transfer.");
    }

    @Test
    @DisplayName("Test successful direct flight ticket purchase")
    void testDirectFlightTicketPurchase() {
        // Specific input for this test
        String input = "1\nJohn\nDoe\n25\nMan\njohn.doe@example.com\n0412345678\nA1234567\n4485364739527352\n123\nYES\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.chooseTicket("New York", "London");
        assertEquals(true, ticket1.ticketStatus(), "Ticket should be marked as booked for direct flight.");
        assertNotNull(ticket1.getPassenger(), "Passenger should be set for ticket1 in direct flight.");
    }


    // When choosing a ticket, a valid city is used
    @Test
    @DisplayName("Test invalid city selection")
    void testInvalidCitySelection() {
        String input = "John\nDoe\n25\nMan\njohn.doe@example.com\n0412345678\nA1234567\n4485364739527352\n123\nNO\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.chooseTicket("Mars", "Venus");
        assertEquals(false, ticket1.ticketStatus(), "Ticket should not be booked for non-existent cities.");
    }

    
    // If a passenger chooses an already booked ticket it should display an error message.
    @Test
    @DisplayName("Test booking an already booked ticket")
    void testAlreadyBookedTicket() {
        ticket1.setTicketStatus(true); // Manually mark the ticket as booked
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.buyTicket(1);
        assertEquals(true, ticket1.ticketStatus(), "Ticket status should remain booked.");
    }

    // Appropriate checks have been implemented to validate passenger information
    @Test
    @DisplayName("Test invalid passenger information handling")
    void testInvalidPassengerInformation() {
        // Specific input simulating invalid age input followed by cancellation of booking
        String input = "1\nJohn\nDoe\ninvalidAge\n"; // 'invalidAge' should trigger the validation error
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); // Redirect system out to capture output for verification

        ticketSystem.chooseTicket("New York", "London"); // Try to buy ticket for an existing flight

        String output = outContent.toString();
        assertFalse(ticket1.ticketStatus(), "Ticket should not be marked as booked after invalid input.");
    }

    // Appropriate checks have been implemented to validate flight information
    @Test
    @DisplayName("Test invalid flight number")
    void testInvalidFlightNumber() {
        String input = "999\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.chooseTicket("New York", "London"); // 假设999是一个无效的航班号
        assertFalse(ticket1.ticketStatus(), "Invalid flight number should not change ticket status.");
    }


    // Appropriate checks have been implemented to validate ticket information
    @Test
    @DisplayName("Test invalid ticket information")
    void testInvalidTicketInformation() {
        String input = "999\n"; // Assuming 999 is an invalid ticket ID
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.buyTicket(999);
        assertEquals(false, ticket1.ticketStatus(), "Invalid ticket should not change ticket status.");
    }


    @Test
    @DisplayName("Display correct ticket details on successful booking")
    void displayCorrectTicketDetailsOnSuccessfulBooking() {
        // 测试正确显示购票后的详细信息
        String input = "1\nJohn\nDoe\n25\nMan\njohn.doe@example.com\n0412345678\nA1234567\n4485364739527352\n123\nYES\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.chooseTicket("New York", "London");
        ticketSystem.showTicket(ticket1);
        String expectedTicketDetails = "Ticket{\nPrice=1120KZT, \nFlight{airplane=Airplane{model='Boeing 747', business sits=20, economy sits=149, crew sits=10}, date to='2023-07-12 15:00:00.0', date from='2023-07-12 12:00:00.0', depart from='London', depart to='New York', code='BA100', company='British Airways'}\nVip status=false\nPassenger{ Fullname= John Doe, email='john.doe@example.com', phoneNumber='0412345678', passport='A1234567'}\nTicket was purchased=true\n}";
        assertEquals(expectedTicketDetails, ticket1.toString(), "Ticket details should match the expected format and content.");
    }


    // A correct value is displayed to the passenger when buying a ticket 0
    @Test
    @DisplayName("Test correct ticket price 0 display")
    void testCorrectTicketPrice0Display() {
        String input = "1\nJohn\nDoe\n60\nMan\njohn.doe@example.com\n0412345678\nA1234567\n4485364739527352\n123\nYES\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.chooseTicket("New York", "London");
        assertEquals(0, ticket1.getPrice(), "Ticket price should be displayed correctly.");
    }

    // A correct value is displayed to the passenger when buying a ticket 112
    @Test
    @DisplayName("Test correct ticket price 112 display")
    void testCorrectTicketPrice112Display() {
        String input = "1\nJohn\nDoe\n25\nMan\njohn.doe@example.com\n0412345678\nA1234567\n4485364739527352\n123\nYES\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ticketSystem = new TicketSystem(new Scanner(System.in));

        ticketSystem.chooseTicket("New York", "London");
        assertEquals(1120, ticket1.getPrice(), "Ticket price should be displayed correctly.");
    }


    // ass2 tests

    @Test
    @DisplayName("Test showing null ticket throws exception")
    void testShowNullTicket() {
        this.ticketSystem = new TicketSystem(new Scanner(System.in));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ticketSystem.showTicket(null));
        assertEquals("Ticket details are incomplete or ticket is null.", exception.getMessage());
    }



}