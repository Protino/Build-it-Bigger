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
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                    request.setDisableGZipContent(BuildConfig.DEBUG);
                }
            })
            .setRootUrl(BuildConfig.API_ENDPOINT_ADDRESS).build();

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