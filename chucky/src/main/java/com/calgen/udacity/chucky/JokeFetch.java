package com.calgen.udacity.chucky;

import com.calgen.udacity.chucky.api.ApiInterface;
import com.calgen.udacity.chucky.api.Joke;
import com.calgen.udacity.chucky.api.JokeResponse;
import com.calgen.udacity.chucky.api.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit.Callback;
import retrofit.RetrofitError;

public class JokeFetch {
    private static final Random random = new Random();
    private static final int NUMBER_OF_JOKES = 20;
    private static final int THRESHOLD = 5;
    private static ApiInterface apiInterface;
    private JokeListsInterface jokeListsInterface;
    private List<String> jokeList;

    public JokeFetch() {
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        jokeList = new ArrayList<>();
    }

    public JokeFetch(JokeListsInterface jokeListsInterface) {
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        this.jokeListsInterface = jokeListsInterface;
        jokeList = new ArrayList<>();
        fetchFromNetwork(false);
    }


    /**
     * Requires to implement {@link JokeListsInterface}
     *
     * @throws IOException
     */
    public void getRandomChuckNorrisJoke() throws IOException {
        if (jokeList.isEmpty()) {
            fetchFromNetwork(true);
        } else {
            notifySuccess();
            if (jokeList.size() == THRESHOLD) fetchFromNetwork(false);
        }
    }

    private void notifySuccess() {
        if (!jokeList.isEmpty()) {
            String joke = jokeList.remove(random.nextInt(jokeList.size() - 1));
            jokeListsInterface.onSuccess(joke);
        }
    }

    private void fetchFromNetwork(final boolean notify) {
        apiInterface.getRandomJokesAsync(NUMBER_OF_JOKES, new Callback<JokeResponse>() {
            @Override
            public void success(JokeResponse jokeResponse, retrofit.client.Response response) {
                for (Joke joke : jokeResponse.getJokeList()) {
                    jokeList.add(joke.getJokeString());
                }
                if (notify) notifySuccess();
            }

            @Override
            public void failure(RetrofitError error) {
                jokeListsInterface.onFailure(error);
            }
        });
    }

    public List<Joke> getJokesList(int numberOfJokes) {
        try {
            return apiInterface.getRandomJokesSync(numberOfJokes).getJokeList();
        } catch (RetrofitError e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
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
