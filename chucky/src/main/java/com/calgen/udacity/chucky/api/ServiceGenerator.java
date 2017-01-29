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

package com.calgen.udacity.chucky.api;

import retrofit.RestAdapter;
import retrofit.appengine.UrlFetchClient;

/**
 * Created by Gurupad Mamadapur on 23-Jan-17.
 */

public class ServiceGenerator {
    public static final String API_ENDPOINT_URL = "http://api.icndb.com";

    private static RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(API_ENDPOINT_URL)
            .setClient(new UrlFetchClient())
            .build();

    /**
     * Caution! - Do not use this method if you want to test the service
     */
    public static <S> S createService(Class<S> serviceClass) {
        return restAdapter.create(serviceClass);
    }
}
