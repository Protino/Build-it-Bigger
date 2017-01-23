
package com.calgen.udacity.api;

import java.io.Serializable;

public class Model implements Serializable {

    private String type;
    private Value value;

    public Model() {
    }

    public Model(String type, Value value) {
        super();
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
