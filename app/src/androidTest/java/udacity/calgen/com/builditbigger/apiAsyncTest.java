package udacity.calgen.com.builditbigger;

import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.test.runner.AndroidJUnit4;

import com.calgen.udacity.api.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class apiAsyncTest {

    @Test
    @UiThread
    public void useAppContext() throws Exception {

        final CountDownLatch signal = new CountDownLatch(1);

        final AsyncTask<Void, Void, String> testTask = new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(Void... params) {
                JokeApi jokeApiService = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl(BuildConfig.API_ENDPOINT_ADDRESS)
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        }).build();
                try {
                    return jokeApiService.getRandomJoke().execute().getJokeString();
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Assert.assertNotNull("Null response, Make sure local gae server is running", result);
                signal.countDown();
            }
        };
        testTask.execute();
        Assert.assertTrue(signal.await(60, TimeUnit.SECONDS));
    }
}
