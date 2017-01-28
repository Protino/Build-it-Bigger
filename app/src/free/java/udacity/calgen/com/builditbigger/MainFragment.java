package udacity.calgen.com.builditbigger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.calgen.com.joker.JokeActivity;

public class MainFragment extends Fragment implements EndpointAsyncTask.EndPointCallback {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    //@formatter:off
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.jokeButton) Button jokeButton;
    @BindView(R.id.adView) AdView adView;
    @BindString(R.string.interstitial_ad_unit_id) String interstitialAdUnitId;
    @BindString(R.string.joke_fetch_error) String errorString;
    //@formatter:on

    private InterstitialAd interstitialAd;

    //Lifecycle start
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        setUpAds();
        return rootView;
    }
    //Lifecycle end


    @OnClick(R.id.jokeButton)
    public void tellJoke(View view) {
        hideProgressBar(false);
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
        new EndpointAsyncTask().setCallbackListener(this).execute();
    }

    private void setUpAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(interstitialAdUnitId);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                requestNewInterstitial();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    private void hideProgressBar(boolean visibility) {
        progressBar.setVisibility(visibility ? View.GONE : View.VISIBLE);
        jokeButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onComplete(String result) {
        if (result != null) {
            Intent intent = new Intent(getContext(), JokeActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, result);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), errorString, Toast.LENGTH_LONG).show();
        }
        hideProgressBar(true);
    }
}
