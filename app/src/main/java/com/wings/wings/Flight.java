package com.wings.wings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flight {

    @SerializedName("flightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("originCode")
    @Expose
    private String originCode;
    @SerializedName("originCity")
    @Expose
    private String originCity;
    @SerializedName("destinationCode")
    @Expose
    private String destinationCode;
    @SerializedName("destinationCity")
    @Expose
    private String destinationCity;
    @SerializedName("estimatedDeparture")
    @Expose
    private String estimatedDeparture;
    @SerializedName("scheduledDeparture")
    @Expose
    private String scheduledDeparture;
    @SerializedName("estimatedArrival")
    @Expose
    private String estimatedArrival;
    @SerializedName("scheduledArrival")
    @Expose
    private String scheduledArrival;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getEstimatedDeparture() {
        return estimatedDeparture;
    }

    public void setEstimatedDeparture(String estimatedDeparture) {
        this.estimatedDeparture = estimatedDeparture;
    }

    public String getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(String scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public String getEstimatedArrival() {
        return estimatedArrival;
    }

    public void setEstimatedArrival(String estimatedArrival) {
        this.estimatedArrival = estimatedArrival;
    }

    public String getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(String scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

}
