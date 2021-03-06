package com.mpg.dev.ssfapp.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SsfRequestManager {
    private final String LOG_TAG = SsfRequestManager.class.getName();
    private SsfRestService ssfRestService;
    private Gson gson;
    private static SsfRequestManager theInstance = new SsfRequestManager();
    private SsfRequestManager() {

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl("http://192.168.1.131")
                        .addConverterFactory(ScalarsConverterFactory.create()).build();

        ssfRestService = retrofit.create(SsfRestService.class);

         gson = new GsonBuilder().setLenient().create();
    }

    public static SsfRequestManager instance(){
        return theInstance;
    }

    public Flowable<SsfHomeResponse> getRooms(String req) {

        Call<String> rooms = ssfRestService.getRooms(req);

        return Flowable.create(emitter -> {
            Callback<String> restCallback = new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String body = response.body();

                    SsfHomeResponse homeResponse = gson.fromJson(body, SsfHomeResponse.class);

                    Log.d(LOG_TAG, body);
                    emitter.onNext(homeResponse);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(LOG_TAG, "FAILED BIG TIME");
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

                    SsfHomeResponse homeResponse = gson.fromJson(response.body(), SsfHomeResponse.class);
                    Log.d(LOG_TAG, response.body());
                    emitter.onNext(homeResponse);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    emitter.onError(t);
                }
            };

            devices.enqueue(restCallback);

        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<SsfHomeResponse> executeCommand(String req){

        Log.d(LOG_TAG, "Executing command = " + req);

        Call<String> execCall = ssfRestService.executeCommand(req);

        return Flowable.create(emitter -> {
            Callback<String> restCallback = new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    SsfHomeResponse homeResponse = gson.fromJson(response.body(), SsfHomeResponse.class);
                    Log.d(LOG_TAG, response.body());
                    emitter.onNext(homeResponse);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    emitter.onError(t);
                }
            };

            execCall.enqueue(restCallback);
        }, BackpressureStrategy.BUFFER);
    }
}
