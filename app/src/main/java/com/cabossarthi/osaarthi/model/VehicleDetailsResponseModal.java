package com.cabossarthi.osaarthi.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VehicleDetailsResponseModal {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("vehiclePricesList")
    @Expose
    private List<VehiclePrices> vehiclePricesList = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<VehiclePrices> getVehiclePricesList() {
        return vehiclePricesList;
    }

    public void setVehiclePricesList(List<VehiclePrices> vehiclePricesList) {
        this.vehiclePricesList = vehiclePricesList;
    }
    public static class VehiclePrices {

        @SerializedName("vehiclePriceId")
        @Expose
        private Integer vehiclePriceId;
        @SerializedName("vehicleClassID")
        @Expose
        private Integer vehicleClassID;
        @SerializedName("className")
        @Expose
        private String className;
        @SerializedName("baseRate")
        @Expose
        private double baseRate;
        @SerializedName("totalFare")
        @Expose
        private Double totalFare;
        @SerializedName("maxPassenger")
        @Expose
        private Integer maxPassenger;
        @SerializedName("isRain")
        @Expose
        private Boolean isRain;
        @SerializedName("isAvailable")
        @Expose
        private Boolean isAvailable;

        public Integer getVehiclePriceId() {
            return vehiclePriceId;
        }

        public void setVehiclePriceId(Integer vehiclePriceId) {
            this.vehiclePriceId = vehiclePriceId;
        }

        public Integer getVehicleClassID() {
            return vehicleClassID;
        }

        public void setVehicleClassID(Integer vehicleClassID) {
            this.vehicleClassID = vehicleClassID;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public double getBaseRate() {
            return baseRate;
        }

        public void setBaseRate(Integer baseRate) {
            this.baseRate = baseRate;
        }

        public Double getTotalFare() {
            return totalFare;
        }

        public void setTotalFare(Double totalFare) {
            this.totalFare = totalFare;
        }

        public Integer getMaxPassenger() {
            return maxPassenger;
        }

        public void setMaxPassenger(Integer maxPassenger) {
            this.maxPassenger = maxPassenger;
        }

        public Boolean getIsRain() {
            return isRain;
        }

        public void setIsRain(Boolean isRain) {
            this.isRain = isRain;
        }

        public Boolean getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
        }
    }
}