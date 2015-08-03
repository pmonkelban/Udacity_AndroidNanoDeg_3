package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.jokeactivity.JokeActivity;


public class MainActivity extends ActionBarActivity implements JokesAsyncTask.AsyncResponse {

    // A Test Ad
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";

    InterstitialAd mInterstitialAd;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AD_UNIT_ID);

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                tellJoke();
            }
        });

        requestNewInterstitial();

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);  // Should be gone, but just in case.

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJokeButtonPressed(View view) {

        // Begin showing the progress bar.
        mProgressBar.setVisibility(View.VISIBLE);

        // Show the interstitial ad if available.
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            tellJoke();
        }
    }


    private void tellJoke() {
        JokesAsyncTask asyncTask = new JokesAsyncTask();
        asyncTask.mCaller = this;
        asyncTask.execute(new Pair<>(getApplicationContext(), ""));

    }

    @Override
    public void processFinish(String output) {

        Intent intent = new Intent(this, JokeActivity.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(JokeActivity.JOKE_EXTRA, output);
        startActivity(intent);

        // Hide the progress bar once the new Activity is started.
        mProgressBar.setVisibility(View.GONE);

    }

    private void requestNewInterstitial()  {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
