package com.mpg.dev.ssfapp.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SsfRestService {

    @POST("cws/api/home")
    Call<String> getRooms(@Body String req);

    @POST("cws/api/home")
    Call<String> getDevices(@Body String req);

    @POST("cws/api/home")
    Call<String> executeCommand(@Body String req);


}
