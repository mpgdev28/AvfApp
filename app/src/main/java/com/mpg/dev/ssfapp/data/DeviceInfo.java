package com.mpg.dev.ssfapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceInfo {

    @SerializedName("Name")
    private String name;
    @SerializedName("DeviceId")
    private String id;
    @SerializedName("Type")
    private DeviceType type;
    @SerializedName("Commands")
    private List<Command> commands;

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

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    enum DeviceType{
        Switch,
        AvDisplay,
        CableBox
    }

    class Command {
        @SerializedName("Name")
        private String name;
        @SerializedName("Command")
        private String command;
        @SerializedName("Type")
        private int type;


    }

}
