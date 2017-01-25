package udacity.calgen.com.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.calgen.udacity.api.jokeApi.JokeApi;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.calgen.com.joker.JokeActivity;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    //@formatter:off
    @BindView(R.id.adView) AdView adView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.jokeButton) Button jokeButton;
    //@formatter:on

    //Lifecycle start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    //Lifecycle end

    @OnClick(R.id.jokeButton)
    public void tellJoke(View view) {
        hideProgressBar(false);
        new EndpointAsyncTask(this).execute();
    }

    private void hideProgressBar(boolean visibility) {
        progressBar.setVisibility(visibility ? View.GONE : View.VISIBLE);
        jokeButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }


    private class EndpointAsyncTask extends AsyncTask<Void, Void, String> {

        private static final String ENDPOINT_URL = "https://sunshine-149409.appspot.com/_ah/api/";
        private final String LOG_TAG = EndpointAsyncTask.class.getSimpleName();
        private JokeApi jokeApiService;
        private Context context;

        public EndpointAsyncTask(Context context) {
            this.context = context;
            if (jokeApiService == null) {
                jokeApiService = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl(ENDPOINT_URL)
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        }).build();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideProgressBar(false);
        }

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
            hideProgressBar(true);
            if (result != null) {
                Intent intent = new Intent(context, JokeActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, result);
                startActivity(intent);
            } else {
                Toast.makeText(context, R.string.JOKE_FETCH_ERROR, Toast.LENGTH_LONG).show();
            }
        }
    }
}