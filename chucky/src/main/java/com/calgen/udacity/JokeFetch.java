package com.calgen.udacity;

import com.calgen.udacity.api.ApiClient;
import com.calgen.udacity.api.Joke;
import com.calgen.udacity.api.JokeResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeFetch {
    private static final Random random = new Random();
    private static final int NUMBER_OF_JOKES = 20;
    private static final int THRESHOLD = 5;
    private static ApiClient apiClient;
    private JokeListsInterface jokeListsInterface;
    private Call<JokeResponse> call;
    private List<String> jokeList;

    public JokeFetch(JokeListsInterface jokeListsInterface) {
        apiClient = new ApiClient();
        this.jokeListsInterface = jokeListsInterface;
        jokeList = new ArrayList<>();
        fetchFromNetwork();
    }

    public void getRandomChuckNorrisJoke() throws IOException {
        if (jokeList.isEmpty()) {
            fetchFromNetwork();
            if (!jokeList.isEmpty()) {
                jokeListsInterface.onSuccess(jokeList.remove(random.nextInt(jokeList.size() - 1)));
            }
        } else {
            jokeListsInterface.onSuccess(jokeList.remove(random.nextInt(jokeList.size() - 1)));
            if (jokeList.size() == THRESHOLD) fetchFromNetwork();
        }
    }

    private void fetchFromNetwork() {
        call = apiClient.getJokeInterface().getRandomJokes(NUMBER_OF_JOKES);
        call.enqueue(new Callback<JokeResponse>() {
            @Override
            public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
                for (Joke joke : response.body().getJokeList()) {
                    jokeList.add(joke.getJokeString());
                }
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
