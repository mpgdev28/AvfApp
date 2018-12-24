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
    public Params params;

    public class Params {
        @SerializedName("Ir")
        public String irCommand;
        @SerializedName("Route")
        public String routeCommand;
    }

    public static SsfHomeRequest createInstance(String req, String roomId, String type, String irCommand, String routeCommand){
        SsfHomeRequest request = new SsfHomeRequest();
        request.request = req;
        request.roomId = roomId;
        request.deviceType = type;
        Params params = request.new Params();
        params.irCommand = irCommand;
        params.routeCommand = routeCommand;
        request.params = params;

        return request;
    }
}
