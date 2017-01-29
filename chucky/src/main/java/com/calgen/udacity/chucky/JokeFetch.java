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
    private ApiInterface apiInterface;
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
