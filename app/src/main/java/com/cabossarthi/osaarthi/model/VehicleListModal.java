package com.cabossarthi.osaarthi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleListModal {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("classID")
    @Expose
    private Integer classID;
    @SerializedName("className")
    @Expose
    private String className;
    @SerializedName("baseRate")
    @Expose
    private Double baseRate;
    @SerializedName("maxPassenger")
    @Expose
    private Integer maxPassenger;
    @SerializedName("rain")
    @Expose
    private Boolean rain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Integer getMaxPassenger() {
        return maxPassenger;
    }

    public void setMaxPassenger(Integer maxPassenger) {
        this.maxPassenger = maxPassenger;
    }

    public Boolean getRain() {
        return rain;
    }

    public void setRain(Boolean rain) {
        this.rain = rain;
    }

}
