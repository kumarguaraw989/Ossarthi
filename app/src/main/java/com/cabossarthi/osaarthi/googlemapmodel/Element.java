
package com.cabossarthi.osaarthi.googlemapmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Element {

    @SerializedName("distance")
    @Expose
    private Distance distance;
    @SerializedName("duration")
    @Expose
    private Duration duration;
    @SerializedName("duration_in_traffic")
    @Expose
    private DurationInTraffic durationInTraffic;
    @SerializedName("status")
    @Expose
    private String status;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public DurationInTraffic getDurationInTraffic() {
        return durationInTraffic;
    }

    public void setDurationInTraffic(DurationInTraffic durationInTraffic) {
        this.durationInTraffic = durationInTraffic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
