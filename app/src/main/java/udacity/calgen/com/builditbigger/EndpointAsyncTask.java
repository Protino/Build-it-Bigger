package udacity.calgen.com.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.calgen.udacity.api.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EndpointAsyncTask extends AsyncTask<Void, Void, String> {

    private static final JokeApi jokeApiService = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl(BuildConfig.API_ENDPOINT_ADDRESS)
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                    request.setDisableGZipContent(true);
                }
            }).build();

    private final String LOG_TAG = EndpointAsyncTask.class.getSimpleName();
    private EndPointCallback endpointCallback;

    @Override
    protected String doInBackground(Void... params) {
        try {
            return jokeApiService.getRandomJoke().execute().getJokeString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "doInBackground: ", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (endpointCallback != null) {
            endpointCallback.onComplete(result);
        }
    }

    /**
     * @param endpointCallback, implementation of {@link EndPointCallback}
     */
    public EndpointAsyncTask setCallbackListener(EndPointCallback endpointCallback) {
        this.endpointCallback = endpointCallback;
        return this;
    }

    public interface EndPointCallback {
        /**
         * Called when {@link EndpointAsyncTask} is completed
         *
         * @param result null in case of error else a joke string.
         */
        void onComplete(String result);
    }

}