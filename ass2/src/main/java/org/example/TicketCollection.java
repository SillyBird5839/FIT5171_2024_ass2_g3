package org.example;

import java.util.ArrayList;

public class TicketCollection {

    public static ArrayList<Ticket> tickets = new ArrayList<>();

    public static ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public static void addTickets(ArrayList<Ticket> tickets_db) {
        if (tickets_db == null) {
            throw new IllegalArgumentException("Cannot add null list of tickets.");
        }
        for (Ticket ticket : tickets_db) {
            if (isValidTicket(ticket)) {
                tickets.add(ticket);
            } else {
                throw new IllegalArgumentException("Invalid ticket data.");
            }
        }
    }
//    private static boolean isValidTicket(Ticket ticket) {
//        if (ticket == null) return false;
//        if (ticket.getPrice() < 0) return false;
//        if (ticket.getFlight() == null) return false;
//        if (ticket.getPassenger() == null) return false;
//        if (ticket.ticketStatus() != true && ticket.ticketStatus() != false) return false;
//        return true;
//    }
private static boolean isValidTicket(Ticket ticket) {
    if (ticket == null) return false;
    if (ticket.getPrice() < 0) return false;  // Ensure that price is non-null and non-negative
    if (ticket.getFlight() == null) return false;
    if (ticket.getPassenger() == null) return false;
    return ticket.ticketStatus() == true || ticket.ticketStatus() == false;  // Simplified to directly return the evaluation
}


    public static void getAllTickets() {
        //display all available tickets from the Ticket collection
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
    public static Ticket getTicketInfo(int ticket_id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicket_id() == ticket_id) {
                return ticket;
            }
        }
        //SELECT a ticket where ticket id = ticket_id
        return null;

    }


}
