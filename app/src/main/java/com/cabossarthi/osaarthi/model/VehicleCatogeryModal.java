package com.cabossarthi.osaarthi.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class VehicleCatogeryModal {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("vehicleCatList")
    @Expose
    private List<VehicleCat> vehicleCatList = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<VehicleCat> getVehicleCatList() {
        return vehicleCatList;
    }

    public void setVehicleCatList(List<VehicleCat> vehicleCatList) {
        this.vehicleCatList = vehicleCatList;
    }
    public static class VehicleCat {

        @SerializedName("vehicleCategoryId")
        @Expose
        private Integer vehicleCategoryId;
        @SerializedName("vahicleClassName")
        @Expose
        private String vahicleClassName;
        @SerializedName("vehicleCategoryName")
        @Expose
        private String vehicleCategoryName;

        public Integer getVehicleCategoryId() {
            return vehicleCategoryId;
        }

        public void setVehicleCategoryId(Integer vehicleCategoryId) {
            this.vehicleCategoryId = vehicleCategoryId;
        }

        public String getVahicleClassName() {
            return vahicleClassName;
        }

        public void setVahicleClassName(String vahicleClassName) {
            this.vahicleClassName = vahicleClassName;
        }

        public String getVehicleCategoryName() {
            return vehicleCategoryName;
        }

        public void setVehicleCategoryName(String vehicleCategoryName) {
            this.vehicleCategoryName = vehicleCategoryName;
        }

    }

}

