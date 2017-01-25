package com.calgen.udacity.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Joke {

    @Id
    private long id;
    private String jokeString;

    public Joke() {}

    public Joke(long id, String jokeString) {
        super();
        this.id = id;
        this.jokeString = jokeString;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJokeString() {
        return jokeString;
    }

    public void setJokeString(String jokeString) {
        this.jokeString = jokeString;
    }
}
