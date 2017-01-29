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

    //@formatter:off
    @BindView(R.id.progressBar) public ProgressBar progressBar;
    @BindView(R.id.jokeButton) public Button jokeButton;
    @BindView(R.id.adView) public AdView adView;
    @BindString(R.string.interstitial_ad_unit_id) public String interstitialAdUnitId;
    @BindString(R.string.joke_fetch_error) public String errorString;
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
            public void onAdClosed() {
                super.onAdClosed();
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
