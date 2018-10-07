package com.wings.wings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flight {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("departureTime")
    @Expose
    private String departureTime;
    @SerializedName("flightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("aircraftType")
    @Expose
    private String aircraftType;
    @SerializedName("arrivalTime")
    @Expose
    private String arrivalTime;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("flightStatus")
    @Expose
    private String flightStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

}
