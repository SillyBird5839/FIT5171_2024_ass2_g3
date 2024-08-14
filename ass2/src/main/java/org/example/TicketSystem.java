package org.example;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketSystem {

    static final Logger log = LoggerFactory.getLogger(TicketSystem.class);
    Scanner in;

    public TicketSystem() {
        this.in = new Scanner(System.in);
    }

    // 创建一个子类专门用来测试
    public TicketSystem(Scanner scanner) {
        this.in = scanner;
    }
    // 展示已购买的票信息
    public void showTicket(Ticket ticket) {
        if (ticket != null) {
            log.info("You have bought a ticket for flight " + ticket.getFlight().getDepartFrom() + " - " + ticket.getFlight().getDepartTo() + "\n\nDetails:");
            log.info(ticket.toString());
        } else {

            log.error("Error: Ticket details are incomplete or ticket is null.");
            throw new IllegalArgumentException("Ticket details are incomplete or ticket is null.");
        }
    }

    // 购买一张票
    public void buyTicket(int ticket_id) {

            Ticket validTicket = TicketCollection.getTicketInfo(ticket_id);
            if (validTicket == null || validTicket.ticketStatus()) {
                log.info("This ticket does not exist or is already booked.");
                return;
            }

            Passenger passenger = inputPassengerDetails();
            if (passenger == null) {
                log.info("Ticket booking cancelled.");
                return; // 如果乘客信息验证失败，直接返回
            }

            log.info("Do you want to proceed with the purchase? Enter YES to proceed or NO to cancel:");
            String decision = in.nextLine().trim();
            if (!decision.equalsIgnoreCase("YES")) {
                log.info("Ticket booking cancelled.");
                return;
            }

            validTicket.setPassenger(passenger);
            validTicket.saleByAge(passenger.getAge()); //changes price of the ticket according to the age category of passenger
            validTicket.setTicketStatus(true);  // 标记票为已购买
            log.info("Ticket booked successfully!");
            updateAirplaneSeats(validTicket);
            showTicket(validTicket);
    }

    // 更新飞机座位
    private void updateAirplaneSeats(Ticket ticket) {
        Airplane airplane = ticket.getFlight().getAirplane();
        if (ticket.getClassVip()) {
            airplane.setBusinessSitsNumber(airplane.getBusinessSitsNumber() - 1);
        } else {
            airplane.setEconomySitsNumber(airplane.getEconomySitsNumber() - 1);
        }
    }

    // 选择票
    public void chooseTicket(String cityto, String cityfrom) {

            Flight flight = FlightCollection.getFlightInfo(cityto, cityfrom);
            if (flight != null) {
                TicketCollection.getAllTickets(); // 应该筛选只显示相关票
                log.info("\nEnter ID of ticket you want to choose:");
                int ticket_id = Integer.parseInt(in.nextLine());
                buyTicket(ticket_id);
            } else {
                log.info("No direct flights available from " + cityto + " to " + cityto);
                handleNoDirectFlight(cityto, cityfrom);
            }

    }

    // 处理无直飞情况
    private void handleNoDirectFlight(String cityto, String cityfrom) {
        boolean keepSearching = true;
        int attempts = 0;

        while (keepSearching && attempts < 3) {  // Allow up to 3 attempts to find a connection
            Flight departToFlight = FlightCollection.getFlightInfo(cityto);
            if (departToFlight != null) {
                String connectCity = departToFlight.getDepartFrom();
                Flight connectingFlight = FlightCollection.getFlightInfo(connectCity, cityfrom);
                if (connectingFlight != null) {
                    log.info("A connecting flight is available via " + connectCity + ". Would you like to book? YES/NO:");
                    String decision = in.nextLine().trim();
                    if (decision.equalsIgnoreCase("YES")) {
                        buyTicket(departToFlight.getFlightID(), connectingFlight.getFlightID());
                        keepSearching = false;
                    } else {
                        log.info("Continue searching for another connection? YES/NO:");
                        decision = in.nextLine().trim();
                        keepSearching = decision.equalsIgnoreCase("YES");
                    }
                } else {
                    log.info("No connecting flights available via " + connectCity);
                    keepSearching = false;
                }
            } else {
                log.info("No flights available to the destination city " + cityto);
                keepSearching = false;
            }
            attempts++;
        }
        if (attempts >= 3) {
            log.info("Maximum attempts reached. No available flights found.");
        }
    }

    // 购买转机票
    public void buyTicket(int ticket_id_first, int ticket_id_second) {
        Ticket validTicketFirst = TicketCollection.getTicketInfo(ticket_id_first);
        Ticket validTicketSecond = TicketCollection.getTicketInfo(ticket_id_second);

        if (validTicketFirst == null || validTicketSecond == null) {
            log.info("One or both tickets do not exist.");
            return;
        }

        // 购买第一张票
        buyTicket(ticket_id_first);
        // 购买第二张票
        buyTicket(ticket_id_second);
    }

    // 输入乘客详情并验证
    private Passenger inputPassengerDetails() {
        log.info("Enter your First Name: ");
        String firstName = in.nextLine();
        log.info("Enter your Second Name:");
        String secondName = in.nextLine();
        log.info("Enter your age:");
        int age = -1;
        try {
            age = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            log.info("Invalid age. Please enter a valid number.");
            return null;
        }
        log.info("Enter your gender: ");
        String gender = in.nextLine();
        log.info("Enter your e-mail address:");
        String email = in.nextLine();
        log.info("Enter your phone number:");
        String phoneNumber = in.nextLine();
        log.info("Enter your passport number:");
        String passportNumber = in.nextLine();
        log.info("Enter your cardNumber number:");
        String cardNumber = in.nextLine();
        log.info("Enter your securityCode number:");
        String securityCode_string = in.nextLine();
        int securityCode = Integer.parseInt(securityCode_string);

        if (validateName(firstName) && validateName(secondName) && validateAge(age) &&
                validateEmail(email) && validatePhoneNumber(phoneNumber) && validatePassportNumber(passportNumber) && isValidCreditCard(cardNumber) && String.valueOf(securityCode).matches("\\d{3,4}")) {
            return new Passenger(firstName, secondName, age, gender, email, phoneNumber, passportNumber, cardNumber, securityCode);
        } else {
            log.info("Invalid input data. Please try again.");
            return null;
        }
    }

    private boolean validateName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean validateAge(int age) {
        return age > 0;
    }

    private boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^(04|05)\\d{8}$|^\\+614\\d{8}$");
    }


    private boolean validatePassportNumber(String passportNumber) {
        return passportNumber.length() <= 9;
    }

    private boolean isValidCreditCard(String number) {
        int[] digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = number.charAt(i) - '0';
        }
        for (int i = digits.length - 2; i >= 0; i -= 2) {
            digits[i] *= 2;
            if (digits[i] > 9) digits[i] -= 9;
        }
        int sum = 0;
        for (int digit : digits) {
            sum += digit;
        }
        return sum % 10 == 0;
    }


}
