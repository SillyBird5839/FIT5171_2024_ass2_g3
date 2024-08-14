package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TicketCollectionTest {
    private Ticket ticket1, ticket2;
    private Passenger passenger;
    private Flight flight;
    private Airplane airplane;

    @BeforeEach
    void setUp() {
        TicketCollection.getTickets().clear(); // 确保票据集合为空
        passenger = new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123);
        airplane = new Airplane(1, "Boeing 747", 12, 150, 10);
        flight = new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 10000), airplane);

        ticket1 = new Ticket(1, 1000, flight, true, passenger);
        ticket2 = new Ticket(2, 2000, flight, false, passenger);
    }

    @Test
    @DisplayName("Add valid tickets to collection")
    void testAddValidTickets() {
        ArrayList<Ticket> newTickets = new ArrayList<>();
        newTickets.add(ticket1);
        newTickets.add(ticket2);
        TicketCollection.addTickets(newTickets);
        assertEquals(2, TicketCollection.getTickets().size(), "Should have 2 tickets in the collection.");
    }

    // Whenever a ticket is being added to the TicketCollection, it must be validated
    @Test
    @DisplayName("Reject adding null list of tickets")
    void testAddNullTickets() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TicketCollection.addTickets(null));
        assertTrue(exception.getMessage().contains("Cannot add null list of tickets."));
    }


    // When trying to get a ticket, the correct ticket is returned
    @Test
    @DisplayName("Retrieve a valid ticket by ID")
    void testGetValidTicketById() {
        ArrayList<Ticket> newTickets = new ArrayList<>();
        newTickets.add(ticket1);
        TicketCollection.addTickets(newTickets);
        Ticket retrievedTicket = TicketCollection.getTicketInfo(1);
        assertNotNull(retrievedTicket, "Should retrieve a ticket.");
        assertEquals(ticket1, retrievedTicket, "Retrieved ticket should match the added ticket.");
    }

    @Test
    @DisplayName("Return null for non-existing ticket ID")
    void testGetInvalidTicketById() {
        ArrayList<Ticket> newTickets = new ArrayList<>();
        newTickets.add(ticket1);
        TicketCollection.addTickets(newTickets);
        Ticket retrievedTicket = TicketCollection.getTicketInfo(99);
        assertNull(retrievedTicket, "Should return null for a non-existing ticket ID.");
    }


    // ass2 test

    @Test
    @DisplayName("Toggle Ticket Status")
    void testToggleTicketStatus() {
        ticket1.setTicketStatus(true); // Change status to true
        assertTrue(ticket1.ticketStatus());
        ticket1.setTicketStatus(false); // Change back to false
        assertFalse(ticket1.ticketStatus());
    }

}
