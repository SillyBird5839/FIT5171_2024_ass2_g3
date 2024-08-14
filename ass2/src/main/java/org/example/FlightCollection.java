package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FlightCollection {

    public static ArrayList<Flight> flights = new ArrayList<>();
    private static final Set<String> VALID_CITIES = new HashSet<>();


    public static ArrayList<Flight> getFlights() {
        return flights;
    }

    static {
        // 初始化有效城市列表
        VALID_CITIES.add("New York");
        VALID_CITIES.add("London");
        VALID_CITIES.add("Sydney");
        VALID_CITIES.add("Tokyo");
        VALID_CITIES.add("Paris");
        VALID_CITIES.add("Berlin");
        VALID_CITIES.add("Shanghai");
        VALID_CITIES.add("Beijing");
        VALID_CITIES.add("Los Angeles");
        VALID_CITIES.add("Moscow");
        VALID_CITIES.add("Melbourne");
    }

    public static void addFlights(ArrayList<Flight> newFlights) {
        if (newFlights == null) {
            throw new IllegalArgumentException("Cannot add null list of flights.");
        }
        for (Flight flight : newFlights) {
            if (isValidFlight(flight)) {
                flights.add(flight);
            } else {
                throw new IllegalArgumentException("Invalid flight data.");
            }
        }
    }

    private static boolean isValidFlight(Flight flight) {
        return flight != null && isValidCity(flight.getDepartTo()) && isValidCity(flight.getDepartFrom());
    }

    private static boolean isValidCity(String city) {
        return VALID_CITIES.contains(city);
    }

    public static Flight getFlightInfo(String cityto, String cityfrom) {
        for (Flight flight : flights) {
            if (flight.getDepartTo().equalsIgnoreCase(cityto) && flight.getDepartFrom().equalsIgnoreCase(cityfrom) ) {
                return flight;
            }
        }
        return null;
    }

    public static Flight getFlightInfo(String city) {
        for (Flight flight : flights) {
            if (flight.getDepartTo().equalsIgnoreCase(city) || flight.getDepartFrom().equalsIgnoreCase(city)) {
                return flight;
            }
        }
        return null;
    }

    public static Flight getFlightInfo(int flight_id) {
        for (Flight flight : flights) {
            if (flight.getFlightID() == flight_id) {
                return flight;
            }
        }
        return null;
    }

}
