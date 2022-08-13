package com.cabossarthi.osaarthi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookRidePostModal {

    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("bookingMode")
    @Expose
    private Integer bookingMode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pickupLatitude")
    @Expose
    private Integer pickupLatitude;
    @SerializedName("pickupLongitude")
    @Expose
    private Integer pickupLongitude;
    @SerializedName("dropLatitude")
    @Expose
    private Integer dropLatitude;
    @SerializedName("dropLongitude")
    @Expose
    private Integer dropLongitude;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("vehiclePriceId")
    @Expose
    private Integer vehiclePriceId;
    @SerializedName("totalFare")
    @Expose
    private Double totalFare;
    @SerializedName("isRain")
    @Expose
    private Boolean isRain;
    @SerializedName("bookingStartTime")
    @Expose
    private String bookingStartTime;
    @SerializedName("bookingEndTime")
    @Expose
    private Object bookingEndTime;
    @SerializedName("bookingScheduleTime")
    @Expose
    private Object bookingScheduleTime;
    @SerializedName("firebaseUserId")
    @Expose
    private String firebaseUserId;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Integer getBookingMode() {
        return bookingMode;
    }

    public void setBookingMode(Integer bookingMode) {
        this.bookingMode = bookingMode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(Integer pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public Integer getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(Integer pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public Integer getDropLatitude() {
        return dropLatitude;
    }

    public void setDropLatitude(Integer dropLatitude) {
        this.dropLatitude = dropLatitude;
    }

    public Integer getDropLongitude() {
        return dropLongitude;
    }

    public void setDropLongitude(Integer dropLongitude) {
        this.dropLongitude = dropLongitude;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getVehiclePriceId() {
        return vehiclePriceId;
    }

    public void setVehiclePriceId(Integer vehiclePriceId) {
        this.vehiclePriceId = vehiclePriceId;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public Boolean getIsRain() {
        return isRain;
    }

    public void setIsRain(Boolean isRain) {
        this.isRain = isRain;
    }

    public String getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(String bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public Object getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(Object bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public Object getBookingScheduleTime() {
        return bookingScheduleTime;
    }

    public void setBookingScheduleTime(Object bookingScheduleTime) {
        this.bookingScheduleTime = bookingScheduleTime;
    }

    public String getFirebaseUserId() {
        return firebaseUserId;
    }

    public void setFirebaseUserId(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

}
