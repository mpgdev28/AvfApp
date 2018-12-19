package com.mpg.dev.ssfapp.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private static DataManager mDataManager;
    private static Object syncObj = new Object();

    private Map<String, List<DeviceInfo>> mRoomToDevices;

    private DataManager(){
        mRoomToDevices = new HashMap<>();
    }

    public static DataManager instance(){

        synchronized (syncObj){
            if(mDataManager == null) {
                mDataManager = new DataManager();
            }
        }

        return mDataManager;
    }

}
