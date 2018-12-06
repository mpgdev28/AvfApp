package com.mpg.dev.ssfapp.data;

import com.google.gson.annotations.SerializedName;

public class RoomInfo {

    @SerializedName("Name")
    private String name;
    @SerializedName("RoomId")
    private String id;
    private RoomType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    enum RoomType {
        Living,
        Dining,
        Bed,
        Kitchen,
        Master,
        Family
    }
}
