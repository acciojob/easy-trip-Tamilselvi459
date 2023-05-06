package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    HashMap<String , Airport>  airportmap = new HashMap<>();
    HashMap<Integer , Flight> flightMap = new HashMap<>();

    HashMap<Integer , Passenger> passengerMap = new HashMap<>();
    HashMap<Integer , List<Integer>> flightpassPair = new HashMap<>();
    HashMap<Integer , Integer> count = new HashMap<>();


    public void addAirport(Airport airport) {
        airportmap.put(airport.getAirportName() , airport);


    }
    public String find(String a , String b){
        if(a.compareTo(b) == 0) return a;
        else if(a.compareTo(b) == -1) return a;
        else  return b;
    }

    public String getLargestAirportName() {
        int max = 0;
        String ans = "";
        for(String name : airportmap.keySet()){
            int count = airportmap.get(name).getNoOfTerminals();
            if(count>max){
                ans = name;
                max = count;
            }
          else  if(count==max) {
             return find(ans , name);
            }
        }
        return ans;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double max = Integer.MAX_VALUE;
        for(int id : flightMap.keySet()){
            if(flightMap.get(id).getFromCity().equals(fromCity) && flightMap.get(id).getToCity().equals(toCity)){
                if(max>flightMap.get(id).getDuration()) {
                    max = flightMap.get(id).getDuration();
                }
            }
        }
        return max;
    }


    public void addFlight(Flight flight) {
        List<Integer> ans = new ArrayList<>();
        flightpassPair.put(flight.getFlightId() , ans);
        flightMap.put(flight.getFlightId(),flight);
    }

    public void addPassenger(Passenger passenger) {
        passengerMap.put(passenger.getPassengerId() , passenger);

    }

    public String getAirportNameFromFlightId(Integer flightId) {
        if(flightMap.containsKey(flightId)) {
            City city = flightMap.get(flightId).getFromCity();
            for (String name : airportmap.keySet()) {
                if (airportmap.get(name).getCity().equals(city))
                    return name;
            }
        }
        return null;
    }

    public String bookTicket(Integer flightId, Integer passengerId) {
        List<Integer> ans = flightpassPair.get(flightId);
        if(ans.size()>=flightMap.get(flightId).getMaxCapacity()) return "FAILURE";
        if(ans.contains(passengerId)) return "FAILURE";
        ans.add(passengerId);
        flightpassPair.put(flightId , ans);
        count.put(passengerId,count.getOrDefault(passengerId,0)+1);
        return "SUCCESS";

    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {

        if(count.containsKey(passengerId)) return count.get(passengerId);
        return 0;
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if(flightpassPair.containsKey(flightId)){
            if(flightpassPair.get(flightId).contains(passengerId)){
                flightpassPair.get(flightId).remove(passengerId);
                count.remove(passengerId);
                return "SUCCESS";
            }
            return "FAILURE";
        }
        return "FAILURE";
    }

    public int calculateFlightFare(Integer flightId) {
        if(flightpassPair.containsKey(flightId)) {
            int counts = flightpassPair.get(flightId).size();
            counts = counts * 50;
            return counts+3000;
        }
        return 0;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        if(Objects.nonNull(date)){
        int count = 0;
        for(int id : flightMap.keySet()){
            Date d = flightMap.get(id).getFlightDate();
            if(d.equals(date)){
             if(airportmap.containsKey(airportName)) {
                 City city = airportmap.get(airportName).getCity();
                 if (city.equals(flightMap.get(id).getFromCity()) || city.equals(flightMap.get(id).getToCity())) {

                     count += flightpassPair.get(id).size();
                 }
             }
            }
        }
        return count;
        }
        return 0;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        int counts = flightpassPair.get(flightId).size();
        return (counts-1)*50 + (3000);

    }
}
