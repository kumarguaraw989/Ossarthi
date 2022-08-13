package com.cabossarthi.osaarthi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendDataToVehicleApi {
    @SerializedName("passangers")
    @Expose
    private Integer passangers;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("long")
    @Expose
    private Integer _long;

    public Integer getPassangers() {
        return passangers;
    }

    public void setPassangers(Integer passangers) {
        this.passangers = passangers;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getLong() {
        return _long;
    }

    public void setLong(Integer _long) {
        this._long = _long;
    }
}
