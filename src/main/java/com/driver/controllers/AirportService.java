package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {
    AirportRepository airportrepository = new AirportRepository();

    public void addAirport(Airport airport) {

        airportrepository.addAirport(airport);
    }

    public String getLargestAirportName() {

        return  airportrepository.getLargestAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
      double max = airportrepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity , toCity);
      if(max==Integer.MAX_VALUE) return -1;
      return max;
    }


    public String addFlight(Flight flight) {
        airportrepository.addFlight(flight);
        return "SUCCESS";
    }

    public String addPassenger(Passenger passenger) {
        airportrepository.addPassenger(passenger);
        return "SUCCESS";
    }

    public String getAirportNameFromFlightId(Integer flightId) {

            return airportrepository.getAirportNameFromFlightId(flightId);

    }

    public String bookTicket(Integer flightId, Integer passengerId) {
        return airportrepository.bookTicket(flightId , passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportrepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportrepository.cancelATicket(flightId , passengerId);
    }

    public int calculateFlightFare(Integer flightId) {
        return airportrepository.calculateFlightFare(flightId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportrepository.getNumberOfPeopleOn(date,airportName);
    }
}
