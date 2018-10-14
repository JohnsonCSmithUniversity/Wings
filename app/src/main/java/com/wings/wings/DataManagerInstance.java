package com.wings.wings;

import java.util.ArrayList;

public class DataManagerInstance {
    private static DataManagerInstance instance;
    Reservation reservation;
    User user;
    Flight flight;
    Airport airport;
    private DataManagerInstance(){
        //fillPostArrayList();
    }

    public static DataManagerInstance getInstance(){
        if(instance == null){
            instance = new DataManagerInstance();
        }
        return  instance;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}
