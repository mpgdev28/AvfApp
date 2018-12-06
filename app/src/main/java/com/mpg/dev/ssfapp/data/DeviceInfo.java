package com.mpg.dev.ssfapp.data;

public class DeviceInfo {

    private String name;
    private String id;
    private DeviceType type;

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

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    enum DeviceType{
        Switch,
        AvDisplay,
        CableBox
    }
}
