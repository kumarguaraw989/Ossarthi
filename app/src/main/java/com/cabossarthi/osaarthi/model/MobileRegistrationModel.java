package com.cabossarthi.osaarthi.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MobileRegistrationModel {
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("firebaseToken")
    @Expose
    private String firebaseToken;
    @SerializedName("firebaseUserId")
    @Expose
    private String firebaseUserId;
    @SerializedName("browser")
    @Expose
    private String browser;
    @SerializedName("deviceID")
    @Expose
    private String deviceID;
    @SerializedName("deviceName")
    @Expose
    private String deviceName;
    @SerializedName("deviceOS")
    @Expose
    private String deviceOS;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getFirebaseUserId() {
        return firebaseUserId;
    }

    public void setFirebaseUserId(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
