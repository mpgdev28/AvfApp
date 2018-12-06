package com.mpg.dev.ssfapp.rest;

import com.google.gson.annotations.SerializedName;

public class SsfHomeRequest {
    @SerializedName("Request")
    public String request;

    @SerializedName("RoomId")
    public String roomId;

    @SerializedName("Type")
    public String deviceType;

    @SerializedName("Params")
    public Param params;

    class Param {


    }


}
