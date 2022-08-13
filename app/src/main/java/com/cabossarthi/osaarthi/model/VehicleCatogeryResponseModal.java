package com.cabossarthi.osaarthi.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleCatogeryResponseModal {
    @SerializedName("sourceLat")
    @Expose
    private double sourceLat;
    @SerializedName("sourceLong")
    @Expose
    private double sourceLong;
    @SerializedName("sourceAddress")
    @Expose
    private String sourceAddress;
    @SerializedName("categoryImage")
    @Expose
    private String categoryImage;

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

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
