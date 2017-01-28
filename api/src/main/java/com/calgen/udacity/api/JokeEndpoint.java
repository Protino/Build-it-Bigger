package com.calgen.udacity.api;

import com.calgen.udacity.api.model.Joke;
import com.calgen.udacity.api.model.JokeListWrapper;
import com.calgen.udacity.api.model.Properties;
import com.calgen.udacity.chucky.JokeFetch;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.cmd.QueryKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Api(
        name = "jokeApi",
        version = "v1",
        resource = "joke",
        namespace = @ApiNamespace(
                ownerDomain = "api.udacity.calgen.com",
                ownerName = "api.udacity.calgen.com"
        )
)
public class JokeEndpoint {

    private static final Logger logger = Logger.getLogger(JokeEndpoint.class.getName());
    private static final int DEFAULT_LIST_LIMIT = 20;
    private static final Random random = new Random();

    static {
        ObjectifyService.register(Joke.class);
        ObjectifyService.register(Properties.class);
        Properties properties = new Properties(Properties.ID, System.currentTimeMillis());
        ofy().save().entity(properties).now();
    }

    public JokeEndpoint() {
        updateJokesList();
    }

    private void updateJokesList() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastInsert = ofy().load().type(Properties.class).id(Properties.ID).now().getTimestamp();
        if ((timeSinceLastInsert - currentTime) > Properties.UPDATE_INTERVAL
                || list(null, null).getItems().isEmpty()) {
            JokeFetch jokeFetch = new JokeFetch();
            deleteAll();
            insertList(parse(jokeFetch.getJokesList(Properties.NUMBER_OF_JOKES)));
            ofy().save().entity(new Properties(Properties.ID,currentTime)).now();
        }
    }

    private JokeListWrapper parse(List<com.calgen.udacity.chucky.api.Joke> jokesList) {
        List<Joke> jokeList = new ArrayList<>();
        for (com.calgen.udacity.chucky.api.Joke joke : jokesList) {
            jokeList.add(new Joke(joke.getId(), joke.getJokeString()));
        }
        return new JokeListWrapper(jokeList);
    }

    public void deleteAll() {
        ofy().delete().keys(ofy().load().type(Joke.class).keys()).now();
    }

    /**
     * Returns the {@link Joke} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Joke} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "joke/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Joke get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting Joke with ID: " + id);
        Joke joke = ofy().load().type(Joke.class).id(id).now();
        if (joke == null) {
            throw new NotFoundException("Could not find Joke with ID: " + id);
        }
        return joke;
    }

    @ApiMethod(
            name = "getProperties",
            path = "properties",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Properties getProperties(){
        return ofy().load().type(Properties.class).id(Properties.ID).now();
    }

    /**
     * @return A randomly chosen {@link Joke} object
     */
    @ApiMethod(
            name = "getRandomJoke",
            path = "joke/random",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Joke getRandomJoke() {
        logger.info("Getting random joke");
        QueryKeys<Joke> keys = ofy().load().type(Joke.class).keys();
        QueryResultIterator<Key<Joke>> iterator = keys.iterator();
        List<Key> keyList = new ArrayList<>();
        while (iterator.hasNext())
            keyList.add(iterator.next());
        Key key = keyList.get(random.nextInt(keyList.size() - 1));
        return (Joke) ofy().load().key(key).now();
    }

    /**
     * Inserts a new {@code Joke}.
     */
    @ApiMethod(
            name = "insert",
            path = "joke",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Joke insert(Joke joke) {
        ofy().save().entity(joke).now();
        logger.info("Created Joke with ID: " + joke.getId());
        return ofy().load().entity(joke).now();
    }

    /**
     * Inserts a new list of {@code Joke} objects.
     */
    @ApiMethod(
            name = "insertList",
            path = "jokeList",
            httpMethod = ApiMethod.HttpMethod.POST)
    public JokeListWrapper insertList(JokeListWrapper jokeList) {
        for (Joke joke : jokeList.getJokeList()) {
            //Doesn't matter if it is a duplicate, as it'll simply overwrite
            ofy().save().entity(joke).now();
        }
        return jokeList;
    }


    /**
     * Updates an existing {@code Joke}.
     *
     * @param id   the ID of the entity to be updated
     * @param joke the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Joke}
     */
    @ApiMethod(
            name = "update",
            path = "joke/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Joke update(@Named("id") long id, Joke joke) throws NotFoundException {
        checkExists(id);
        ofy().save().entity(joke).now();
        logger.info("Updated Joke: " + joke);
        return ofy().load().entity(joke).now();
    }

    /**
     * Deletes the specified {@code Joke}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Joke}
     */
    @ApiMethod(
            name = "remove",
            path = "joke/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Joke.class).id(id).now();
        logger.info("Deleted Joke with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "joke",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Joke> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Joke> query = ofy().load().type(Joke.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Joke> queryIterator = query.iterator();
        List<Joke> jokeList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            jokeList.add(queryIterator.next());
        }
        return CollectionResponse.<Joke>builder().setItems(jokeList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Joke.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Joke with ID: " + id);
        }
    }
}