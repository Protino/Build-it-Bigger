package udacity.calgen.com.builditbigger;

import com.calgen.udacity.JokeFetch;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ChuckyTest {

    @Test
    public void testJokeFetch() throws IOException {
        assertNotNull("Invalid joke response", new JokeFetch().getRandomChuckNorrisJoke());
    }
}