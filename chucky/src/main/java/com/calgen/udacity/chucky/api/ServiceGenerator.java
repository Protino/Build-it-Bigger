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
