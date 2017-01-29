/*
 * Copyright 2016 Gurupad Mamadapur
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
