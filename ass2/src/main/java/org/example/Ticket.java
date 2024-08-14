package org.example;

public class Ticket
{
    private int ticket_id;
    private Integer price;
    Flight flight;
    private boolean classVip; //indicates if this is bussiness class ticket or not
    private boolean status; //indicates status of ticket: if it is bought by someone or not
    Passenger passenger;


    public Ticket(int ticket_id,Integer price, Flight flight, boolean classVip, Passenger passenger)
    {
        setTicket_id(ticket_id);
        setFlight(flight);
        setClassVip(classVip);
        setPassenger(passenger);
        setPrice(price);
        setTicketStatus(false);
    }

    public Ticket() {}

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        if (ticket_id <= 0) {
            throw new IllegalArgumentException("Ticket ID must be positive.");
        }
        this.ticket_id = ticket_id;
    }

    public int getPrice() { return price; }

    public void setPrice(Integer price)
    {
        if (price == null){
            throw new IllegalArgumentException("Price cannot be null.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive.");
        }
        this.price = price;
        saleByAge(passenger.getAge()); //changes price of the ticket according to the age category of passenger
        serviceTax( ); //calculate servicetax
    }

    public void saleByAge(int age)
    {
        int price = getPrice();
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        if(age < 15)
        {
            price-= (int) (price*0.5);//50% sale for children under 15
            this.price=price;
        } else if(age>=60){
            this.price=0; //100% sale for elder people
        }
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null.");
        }
        this.flight = flight;
    }

    public boolean getClassVip() {
        return classVip;
    }

    public void setClassVip(boolean classVip) {
        this.classVip = classVip;
    }

    public boolean ticketStatus()
    {
        return status;
    }

    public void setTicketStatus(boolean status)
    {
        this.status = status;
    }

    public void serviceTax(){
        this.price = (int) Math.round(this.price * 1.12); // 12% service tax
    } //12% service tax

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger cannot be null.");
        }
        this.passenger = passenger;
    }

    public String toString()
    {
        return"Ticket{" +'\n'+
                "Price=" + getPrice() + "KZT, " + '\n' +
                getFlight() +'\n'+ "Vip status=" + getClassVip() + '\n' +
                getPassenger()+'\n'+ "Ticket was purchased=" + ticketStatus() + "\n}";
    }
}
