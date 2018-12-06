package com.mpg.dev.ssfapp.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpg.dev.ssfapp.data.RoomInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SsfRequestManager {

    private SsfRestService ssfRestService;

    public SsfRequestManager() {

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl("http://192.168.1.131")
                        .addConverterFactory(ScalarsConverterFactory.create()).build();

        ssfRestService = retrofit.create(SsfRestService.class);
    }

    public void getRooms() {
        String req = "{\"Request\":\"GetRooms\"}";
        Call<String> rooms = ssfRestService.getRooms(req);

        rooms.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Gson gson = new GsonBuilder().setLenient().create();
                String body = response.body();

                SsfHomeResponse homeResponse = gson.fromJson(body, SsfHomeResponse.class);

                Log.d("SSF_APP", body);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("SSF_APP", "FAILED BIG TIME");
            }
        });

        /*rooms.enqueue(new Callback<SsfHomeResponse>() {
            @Override
            public void onResponse(Call<SsfHomeResponse> call, Response<SsfHomeResponse> response) {
                SsfHomeResponse body = response.body();

                Log.d("SSF_APP", body.getResponse());
            }

            @Override
            public void onFailure(Call<SsfHomeResponse> call, Throwable t) {
                Log.d("SSF_APP", "FAILED BIG TIME");
            }
        });*/
    }
}
