package com.calgen.udacity.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Gurupad Mamadapur on 25-Jan-17.
 */

@Entity
public class Properties {

    public static final long ID = 1881;
    public static final int NUMBER_OF_JOKES = 50;
    public static final long UPDATE_INTERVAL = 60 * 60 * 24 * 1000;

    @Id
    private long id;
    private long timestamp;


    public Properties(long id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
