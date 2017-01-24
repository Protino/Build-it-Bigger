package com.calgen.udacity;

import com.calgen.udacity.api.ApiClient;
import com.calgen.udacity.api.JokeResponse;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by Gurupad Mamadapur on 23-Jan-17.
 */

public class RetrofitTest {

    @Test
    public void testGetJokes() throws IOException {
        ApiClient apiClient = new ApiClient();
        Call<JokeResponse> call = apiClient.getJokeInterface().getRandomJokes(5);
        Response<JokeResponse> response = call.execute();

        assertEquals("Incorrect response : ", 5, response.body().getJokeList().size());
    }
}
