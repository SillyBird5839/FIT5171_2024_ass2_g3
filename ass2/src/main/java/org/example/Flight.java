package org.example;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Flight {
    private Integer flightID;
    private String departTo;
    private String departFrom;
    private String code;
    private String company;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    Airplane airplane;

//    public Flight(){}

    public Flight(Integer flight_id, String departTo, String departFrom, String code, String company, Timestamp dateFrom,Timestamp dateTo, Airplane airplane)
    {
        setFlightID(flight_id);
        setDepartTo(departTo);
        setDepartFrom(departFrom);
        setCode(code);
        setCompany(company);
        setDateFrom(dateFrom);
        setDateTo(dateTo);
        setAirplane(airplane);
        checkFlightUniqueness();
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(Integer flightid) {
        if (flightid == null) {
            throw new IllegalArgumentException("Flight id cannot be null.");
        }

        if (flightid > 0) {
            this.flightID = flightid;
        } else {
            throw new IllegalArgumentException("Flight ID must be positive.");
        }
    }

    public String getDepartTo() {
        return departTo;
    }

    public void setDepartTo(String departTo) {

        if (departTo != null && !departTo.trim().isEmpty()) {
            this.departTo = departTo;
        } else {
            throw new IllegalArgumentException("Departure to location cannot be empty.");
        }
    }

    public String getDepartFrom() {
        return departFrom;
    }

    public void setDepartFrom(String departFrom) {

        if (departFrom != null && !departFrom.trim().isEmpty()) {
            this.departFrom = departFrom;
        } else {
            throw new IllegalArgumentException("Departure from location cannot be empty.");
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {

        if (code != null && !code.trim().isEmpty()) {
            this.code = code;
        } else {
            throw new IllegalArgumentException("Flight code cannot be empty.");
        }
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {

        if (company != null && !company.trim().isEmpty()) {
            this.company = company;
        } else {
            throw new IllegalArgumentException("Company cannot be empty.");
        }
    }


    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        if (dateFrom == null) {
            throw new IllegalArgumentException("Departure date cannot be null.");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        sdf.setLenient(false); // 设置为严格的日期解析，不符合格式将抛出异常
        try {
            String formattedDate = sdf.format(dateFrom); // 将 Timestamp 格式化为字符串
            Date parsedDate = sdf.parse(formattedDate); // 尝试解析回 Date，检查格式一致性
            this.dateFrom = new Timestamp(parsedDate.getTime()); // 如果没有抛出异常，设置 this.dateTo
        } catch (ParseException e) {
            throw new IllegalArgumentException("Arrival date format must be 'DD/MM/YY HH:MM:SS'.");
        }
    }

    public Timestamp getDateTo() {
        return dateTo;
    }


    public void setDateTo(Timestamp dateTo) {
        if (dateTo == null) {
            throw new IllegalArgumentException("Arrival date cannot be null.");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        sdf.setLenient(false); // 设置为严格的日期解析，不符合格式将抛出异常
        try {
            String formattedDate = sdf.format(dateTo); // 将 Timestamp 格式化为字符串
            Date parsedDate = sdf.parse(formattedDate); // 尝试解析回 Date，检查格式一致性
            this.dateTo = new Timestamp(parsedDate.getTime()); // 如果没有抛出异常，设置 this.dateTo
        } catch (ParseException e) {
            throw new IllegalArgumentException("Arrival date format must be 'DD/MM/YY HH:MM:SS'.");
        }
    }
    public void setAirplane(Airplane airplane) {
        if (airplane == null) {
            throw new IllegalArgumentException("Airplane cannot be null.");
        }
        this.airplane = airplane;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    private void checkFlightUniqueness() {
        for (Flight flight : FlightCollection.getFlights()) {
            if (flight.getFlightID() == this.getFlightID() ||
                    (flight.getDepartFrom().equalsIgnoreCase(this.getDepartFrom()) &&
                            flight.getDepartTo().equalsIgnoreCase(this.getDepartTo()) &&
                            flight.getDateFrom().equals(this.getDateFrom()) &&
                            flight.getDateTo().equals(this.getDateTo()))) {
                throw new IllegalStateException("A flight with the same details already exists in the system.");
            }
        }
    }

    public String toString()
    {
        return "Flight{" + "airplane=" + getAirplane().toString() +
                ", date to='" +  getDateTo() + "', " +
                "date from='" + getDateFrom() + "', " +
                "depart from='" + getDepartFrom() + "', " +
                "depart to='" + getDepartTo() + "', " +
                "code='" + getCode() + "', " +
                "company='" + getCompany() + "'" +
                '}';
    }
}
