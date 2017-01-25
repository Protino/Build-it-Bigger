package com.calgen.udacity;

import com.calgen.udacity.chucky.api.ApiInterface;
import com.calgen.udacity.chucky.api.ServiceGenerator;
import com.squareup.okhttp.OkHttpClient;

import org.junit.Test;

import java.io.IOException;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static org.junit.Assert.assertEquals;

/**
 * Created by Gurupad Mamadapur on 23-Jan-17.
 */

public class RetrofitTest {

    @Test
    public void testGetJokes() throws IOException {

        //Manually building the RestAdapter because UrlFetchClient need appengine module
        // and doesn't work during testing.
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServiceGenerator.API_ENDPOINT_URL)
                .setClient(new OkClient(new OkHttpClient()))
                .build();
        ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        assertEquals("Incorrect response : ", 5, apiInterface.getRandomJokesSync(5).getJokeList().size());
    }
}
