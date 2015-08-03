package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.backend.myApi.MyApi;
import com.udacity.gradle.jokeactivity.JokeActivity;


public class MainActivity extends ActionBarActivity implements JokesAsyncTask.AsyncResponse {

    ProgressBar mProgressBar;


    /*
    * Builder to connect to the Google Cloud Engine.
    */
    MyApi.Builder mBuilder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl("https://udacity-jokes.appspot.com/_ah/api");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        tellJoke();
    }

    private void tellJoke() {
        JokesAsyncTask asyncTask = new JokesAsyncTask(mBuilder);
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
}
