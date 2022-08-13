package com.cabossarthi.osaarthi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookRideResponseModal {
    @SerializedName("resultStatus")
    @Expose
    private Integer resultStatus;
    @SerializedName("resultMessage")
    @Expose
    private String resultMessage;

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
