package com.calgen.udacity.chucky.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Joke {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("joke")
    @Expose
    private String jokeString;
    @SerializedName("categories")
    @Expose
    private List<Object> categories = null;

    public Joke() {
    }

    public Joke(long id, String jokeString, List<Object> categories) {
        super();
        this.id = id;
        this.jokeString = formatJoke(jokeString);
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJokeString() {
        return formatJoke(jokeString);
    }

    public void setJokeString(String jokeString) {
        this.jokeString = formatJoke(jokeString);
    }

    private String formatJoke(String joke) {
        return joke.replace("&quot;", "\"");
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

}
