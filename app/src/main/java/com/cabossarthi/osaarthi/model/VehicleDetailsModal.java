package com.cabossarthi.osaarthi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleDetailsModal {
    @SerializedName("sourceLat")
    @Expose
    private double sourceLat;
    @SerializedName("sourceLong")
    @Expose
    private double sourceLong;
    @SerializedName("destinationLat")
    @Expose
    private double destinationLat;
    @SerializedName("destinationLong")
    @Expose
    private double destinationLong;
    @SerializedName("distance")
    @Expose
    private double distance;
    @SerializedName("sourceAddress")
    @Expose
    private String sourceAddress;
    @SerializedName("destinationAddress")
    @Expose
    private String destinationAddress;
    @SerializedName("VehicleCategoryId")
    @Expose
    private Integer vehicleCategoryId;

    public double getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(double sourceLat) {
        this.sourceLat = sourceLat;
    }

    public double getSourceLong() {
        return sourceLong;
    }

    public void setSourceLong(double sourceLong) {
        this.sourceLong = sourceLong;
    }

    public double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public double getDestinationLong() {
        return destinationLong;
    }

    public void setDestinationLong(double destinationLong) {
        this.destinationLong = destinationLong;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Integer getVehicleCategoryId() {
        return vehicleCategoryId;
    }

    public void setVehicleCategoryId(Integer vehicleCategoryId) {
        this.vehicleCategoryId = vehicleCategoryId;
    }

}
