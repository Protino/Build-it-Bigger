
package com.calgen.udacity.api;

import java.util.List;

public class Value {

    private long id;
    private String joke;
    private List<Object> categories = null;

    public Value() {
    }

    public Value(long id, String joke, List<Object> categories) {
        super();
        this.id = id;
        this.joke = joke;
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

}
