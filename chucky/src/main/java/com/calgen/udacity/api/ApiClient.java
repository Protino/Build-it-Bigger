package com.calgen.udacity.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gurupad Mamadapur on 23-Jan-17.
 */

public class ApiClient {
    public static final String API_ENDPOINT_URL = "http://api.icndb.com/";
    private Retrofit retrofit;

    public ApiClient() {
        retrofit = getRetrofit();
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_ENDPOINT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public ApiInterface getJokeInterface() {
        return retrofit.create(ApiInterface.class);
    }
}
