package udacity.calgen.com.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.calgen.udacity.JokeFetch;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.calgen.com.joker.JokeActivity;

public class MainActivity extends AppCompatActivity implements JokeFetch.JokeListsInterface {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    //@formatter:off
    @BindView(R.id.adView) AdView adView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.jokeButton) Button jokeButton;
    private JokeFetch jokeFetch;
    //@formatter:on
    //Lifecycle start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        jokeFetch = new JokeFetch(this);
    }
    //Lifecycle end

    @OnClick(R.id.jokeButton)
    public void tellJoke(View view) {
        hideProgressBar(false);
        try {
            jokeFetch.getRandomChuckNorrisJoke();
        } catch (IOException e) {
            Log.e(LOG_TAG, "tellJoke: ", e);
            Toast.makeText(this, R.string.JOKE_FETCH_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(String joke) {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, joke);
        startActivity(intent);
        hideProgressBar(true);
    }

    @Override
    public void onFailure(Throwable throwable) {
        Log.e(LOG_TAG, "onFailure: ", throwable);
        Toast.makeText(this, R.string.JOKE_FETCH_ERROR, Toast.LENGTH_LONG).show();
    }

    private void hideProgressBar(boolean visibility) {
        progressBar.setVisibility(visibility ? View.GONE : View.VISIBLE);
        jokeButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}