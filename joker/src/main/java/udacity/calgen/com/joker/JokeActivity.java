package udacity.calgen.com.joker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    //Lifecycle start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        String joke = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        ((TextView) findViewById(R.id.joke)).setText(joke);
    }
    //Lifecycle end
}
