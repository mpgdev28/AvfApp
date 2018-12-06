package com.mpg.dev.ssfapp.rest;

import com.mpg.dev.ssfapp.data.RoomInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SsfRestService {

    @POST("cws/api/home")
    Call<String> getRooms(@Body String req);
}
