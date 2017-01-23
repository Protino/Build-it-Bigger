package com.calgen.udacity;

import com.calgen.udacity.api.ApiClient;
import com.calgen.udacity.api.JokeResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeFetch {
    private static ApiClient apiClient;
    private JokeListsInterface jokeListsInterface;
    private Call<JokeResponse> call;

    public JokeFetch(JokeListsInterface jokeListsInterface) {
        apiClient = new ApiClient();
        this.jokeListsInterface = jokeListsInterface;
    }

    public void getRandomChuckNorrisJoke() throws IOException {
        call = apiClient.getJokeInterface().getJokes();
        call.enqueue(new Callback<JokeResponse>() {
            @Override
            public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
                jokeListsInterface.onSuccess(response.body().getJoke().getJokeString());
            }

            @Override
            public void onFailure(Call<JokeResponse> call, Throwable t) {
                jokeListsInterface.onFailure(t);
            }
        });
    }

    public interface JokeListsInterface {
        /**
         * Called when a successful retrieval of a joke is made
         *
         * @param jokeString valid chuck norris joke
         */
        void onSuccess(String jokeString);

        /**
         * Called when an unsuccessful retrieval of joke occurs,
         * due to network error or when an unexpected exception occurs
         *
         * @param throwable
         */
        void onFailure(Throwable throwable);
    }
}
