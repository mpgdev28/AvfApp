package com.mpg.dev.ssfapp.data;

import android.content.Context;

import com.mpg.dev.avfapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private static DataManager mDataManager;
    private static Object syncObj = new Object();

    private Map<String, List<DeviceInfo>> mRoomToDevices;
    private Map<String, String> mButtonToCommand;
    private Context mContext;

    private DataManager(Context context){
        mRoomToDevices = new HashMap<>();
        mButtonToCommand = new HashMap<>();
        mContext = context;
    }

    public static DataManager instance(Context context){

        synchronized (syncObj){
            if(mDataManager == null) {
                mDataManager = new DataManager(context);
            }
        }

        return mDataManager;
    }

    public String getCommandForButton(String buttonId){
        return mButtonToCommand.get(buttonId);
    }

    public void setDevicesForRoom(String roomid, List<DeviceInfo> devices){
        mRoomToDevices.put(roomid, devices);
        for(DeviceInfo deviceInfo : devices){

            String[] commands = new String[0];
            switch (deviceInfo.getType()){

                case CableBox:
                    commands = mContext.getResources().getStringArray(R.array.CableBox_commands);
                    break;
                case AvDisplay:
                    commands = mContext.getResources().getStringArray(R.array.AvDisplay_commands);
                    break;
            }

            for(String command : commands){
                String[] pair = command.split(":");
                mButtonToCommand.put(pair[0], pair[1]);
            }
        }
    }

    public List<DeviceInfo> getDevices(String roomid){
        return mRoomToDevices.get(roomid);
    }

}
