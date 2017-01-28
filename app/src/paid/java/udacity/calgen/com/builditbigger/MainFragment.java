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
    @BindString(R.string.joke_fetch_error) String errorString;
    //@formatter:on

    //Lifecycle start
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    //Lifecycle end


    @OnClick(R.id.jokeButton)
    public void tellJoke(View view) {
        hideProgressBar(false);
        new EndpointAsyncTask().setCallbackListener(this).execute();
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

    private void hideProgressBar(boolean visibility) {
        progressBar.setVisibility(visibility ? View.GONE : View.VISIBLE);
        jokeButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
