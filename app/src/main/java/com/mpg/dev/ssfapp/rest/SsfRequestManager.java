package com.mpg.dev.ssfapp.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpg.dev.ssfapp.data.RoomInfo;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
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

    public Flowable<SsfHomeResponse> getRooms(String req) {
        req = "{\"Request\":\"GetRooms\"}";
        Call<String> rooms = ssfRestService.getRooms(req);

        return Flowable.create(emitter -> {
            Callback<String> restCallback = new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Gson gson = new GsonBuilder().setLenient().create();
                    String body = response.body();

                    SsfHomeResponse homeResponse = gson.fromJson(body, SsfHomeResponse.class);

                    Log.d("SSF_APP", body);
                    emitter.onNext(homeResponse);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("SSF_APP", "FAILED BIG TIME");
                    emitter.onError(t);
                }
            };

            rooms.enqueue(restCallback);

        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<SsfHomeResponse> getDevices(String req){
        Call<String> devices = ssfRestService.getDevices(req);
        return Flowable.create(emitter -> {
            Callback<String> restCallback = new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            };
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<SsfHomeResponse> executeCommand(String req){
        Call<String> execCall = ssfRestService.executeCommand(req);

        return Flowable.create(emitter -> {
            Callback<String> restCallback = new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            };
        }, BackpressureStrategy.BUFFER);

    }
}
