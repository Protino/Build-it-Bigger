package com.calgen.udacity;

import com.calgen.udacity.api.ApiClient;
import com.calgen.udacity.api.JokeResponse;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Gurupad Mamadapur on 23-Jan-17.
 */

public class RetrofitTest {

    @Test
    public void testGetJokes() throws IOException {
        ApiClient apiClient = new ApiClient();
        Call<JokeResponse> call = apiClient.getJokeInterface().getJokes();
        Response<JokeResponse> response = call.execute();

        assertNotNull("Incorrect response : ", response.body().getJoke());
    }
}
