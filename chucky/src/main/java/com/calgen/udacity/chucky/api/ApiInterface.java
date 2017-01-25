package com.calgen.udacity.chucky.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Gurupad Mamadapur on 23-Jan-17.
 */

public interface ApiInterface {

    @GET("/jokes/random/{number}/")
    JokeResponse getRandomJokesSync(@Path("number") int numberOfJokes);

    @GET("/jokes/random/{number}/")
    void getRandomJokesAsync(@Path("number") int numberOfJokes, Callback<JokeResponse> callback);
}
