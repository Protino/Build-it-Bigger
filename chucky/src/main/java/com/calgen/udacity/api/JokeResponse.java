
package com.calgen.udacity.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JokeResponse implements Serializable {

    @SerializedName("type")
    @Expose
    private String responseCode;
    @SerializedName("value")
    @Expose
    private Joke joke;

    public JokeResponse() {
    }

    public JokeResponse(String type, Joke joke) {
        super();
        this.responseCode = type;
        this.joke = joke;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Joke getJoke() {
        return joke;
    }

    public void setJoke(Joke joke) {
        this.joke = joke;
    }

}
