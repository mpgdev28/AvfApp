package com.mpg.dev.ssfapp.rest;

import com.google.gson.annotations.SerializedName;
import com.mpg.dev.ssfapp.data.DeviceInfo;
import com.mpg.dev.ssfapp.data.RoomInfo;

import java.util.List;

public class SsfHomeResponse extends ResponseBase{

    @SerializedName("Results")
    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public class Results {
        @SerializedName("Rooms")
        private List<RoomInfo> rooms;

        @SerializedName("Devices")
        private List<DeviceInfo> devices;

        public List<RoomInfo> getRooms() {
            return rooms;
        }

        public void setRooms(List<RoomInfo> rooms) {
            this.rooms = rooms;
        }

        public List<DeviceInfo> getDevices() {
            return devices;
        }

        public void setDevices(List<DeviceInfo> devices) {
            this.devices = devices;
        }
    }
}
