package com.calgen.udacity.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Gurupad Mamadapur on 23-Jan-17.
 */

public interface ApiInterface {

    @GET("jokes/random")
    Call<JokeResponse> getJokes();
}
