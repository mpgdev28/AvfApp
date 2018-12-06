package com.mpg.dev.ssfapp.rest;

import com.google.gson.annotations.SerializedName;
import com.mpg.dev.ssfapp.data.RoomInfo;

import java.util.List;

public class SsfHomeResponse extends ResponseBase{

    @SerializedName("Results")
    private Results results;

    class Results {
        @SerializedName("Rooms")
        private List<RoomInfo> results;
    }
}
