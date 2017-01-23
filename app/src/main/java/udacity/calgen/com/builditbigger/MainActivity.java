package udacity.calgen.com.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.calgen.udacity.JokeFetch;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements JokeFetch.JokeListsInterface {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    //@formatter:off
    @BindView(R.id.adView) AdView adView;
    private JokeFetch jokeFetch;
    private String joke;
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

    @Override
    protected void onStart() {
        super.onStart();
        jokeFetch = new JokeFetch(this);
    }
    //Lifecycle end

    public void tellJoke(View view) throws IOException {
        jokeFetch.getRandomChuckNorrisJoke();
    }

    @Override
    public void onSuccess(String joke) {
        Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable throwable) {
        Log.e(LOG_TAG, "onFailure: ", throwable);
    }
}