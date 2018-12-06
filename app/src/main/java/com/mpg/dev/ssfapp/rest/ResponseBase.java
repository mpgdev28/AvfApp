package com.mpg.dev.ssfapp.rest;

import com.google.gson.annotations.SerializedName;

public class ResponseBase {
    @SerializedName("Response")
    private String response;
    @SerializedName("Version")
    private String version;
    @SerializedName("Success")
    private boolean success;
    @SerializedName("DateTime")
    private String dateTime;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
