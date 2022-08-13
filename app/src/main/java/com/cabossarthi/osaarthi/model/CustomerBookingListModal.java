package com.cabossarthi.osaarthi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerBookingListModal {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("customerRideLists")
    @Expose
    private List<CustomerRideList> customerRideLists = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CustomerRideList> getCustomerRideLists() {
        return customerRideLists;
    }

    public void setCustomerRideLists(List<CustomerRideList> customerRideLists) {
        this.customerRideLists = customerRideLists;
    }

    public static class  CustomerRideList{
        @SerializedName("bookingID")
        @Expose
        private Integer bookingID;
        @SerializedName("bookingModeName")
        @Expose
        private String bookingModeName;
        @SerializedName("className")
        @Expose
        private Object className;
        @SerializedName("vahicleClassName")
        @Expose
        private Object vahicleClassName;
        @SerializedName("bookingStartTime")
        @Expose
        private String bookingStartTime;
        @SerializedName("bookingEndTime")
        @Expose
        private String bookingEndTime;
        @SerializedName("bookingScheduleTime")
        @Expose
        private String bookingScheduleTime;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("pickupLatitude")
        @Expose
        private Double pickupLatitude;
        @SerializedName("pickupLongitude")
        @Expose
        private Double pickupLongitude;
        @SerializedName("dropLatitude")
        @Expose
        private Double dropLatitude;
        @SerializedName("dropLongitude")
        @Expose
        private Double dropLongitude;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("source")
        @Expose
        private String source;
        @SerializedName("totalFare")
        @Expose
        private Double totalFare;
        @SerializedName("destination")
        @Expose
        private String destination;
        @SerializedName("isRain")
        @Expose
        private Boolean isRain;

        public Integer getBookingID() {
            return bookingID;
        }

        public void setBookingID(Integer bookingID) {
            this.bookingID = bookingID;
        }

        public String getBookingModeName() {
            return bookingModeName;
        }

        public void setBookingModeName(String bookingModeName) {
            this.bookingModeName = bookingModeName;
        }

        public Object getClassName() {
            return className;
        }

        public void setClassName(Object className) {
            this.className = className;
        }

        public Object getVahicleClassName() {
            return vahicleClassName;
        }

        public void setVahicleClassName(Object vahicleClassName) {
            this.vahicleClassName = vahicleClassName;
        }

        public String getBookingStartTime() {
            return bookingStartTime;
        }

        public void setBookingStartTime(String bookingStartTime) {
            this.bookingStartTime = bookingStartTime;
        }

        public String getBookingEndTime() {
            return bookingEndTime;
        }

        public void setBookingEndTime(String bookingEndTime) {
            this.bookingEndTime = bookingEndTime;
        }

        public String getBookingScheduleTime() {
            return bookingScheduleTime;
        }

        public void setBookingScheduleTime(String bookingScheduleTime) {
            this.bookingScheduleTime = bookingScheduleTime;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Double getPickupLatitude() {
            return pickupLatitude;
        }

        public void setPickupLatitude(Double pickupLatitude) {
            this.pickupLatitude = pickupLatitude;
        }

        public Double getPickupLongitude() {
            return pickupLongitude;
        }

        public void setPickupLongitude(Double pickupLongitude) {
            this.pickupLongitude = pickupLongitude;
        }

        public Double getDropLatitude() {
            return dropLatitude;
        }

        public void setDropLatitude(Double dropLatitude) {
            this.dropLatitude = dropLatitude;
        }

        public Double getDropLongitude() {
            return dropLongitude;
        }

        public void setDropLongitude(Double dropLongitude) {
            this.dropLongitude = dropLongitude;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Double getTotalFare() {
            return totalFare;
        }

        public void setTotalFare(Double totalFare) {
            this.totalFare = totalFare;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public Boolean getIsRain() {
            return isRain;
        }

        public void setIsRain(Boolean isRain) {
            this.isRain = isRain;
        }

    }
}
