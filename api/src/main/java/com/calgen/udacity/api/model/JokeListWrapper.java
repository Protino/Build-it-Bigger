package com.calgen.udacity.api.model;

import com.googlecode.objectify.annotation.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gurupad Mamadapur on 24-Jan-17.
 */
@Entity
public class JokeListWrapper {
    private List<Joke> jokeList;

    public JokeListWrapper() {
        jokeList = new ArrayList<>();
    }

    public JokeListWrapper(List<Joke> jokeList) {
        this.jokeList = jokeList;
    }

    public List<Joke> getJokeList() {
        return jokeList;
    }

    public void setJokeList(List<Joke> jokeList) {
        this.jokeList = jokeList;
    }
}
