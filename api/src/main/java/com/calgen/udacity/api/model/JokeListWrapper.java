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
