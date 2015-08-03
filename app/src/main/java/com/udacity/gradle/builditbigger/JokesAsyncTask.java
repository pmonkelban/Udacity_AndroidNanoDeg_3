package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.udacity.gradle.backend.myApi.MyApi;

import java.io.IOException;

public class JokesAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private final MyApi.Builder mBuilder;


    public interface AsyncResponse  {
        void processFinish(String output);
    }

    public JokesAsyncTask(MyApi.Builder builder)  {
        mBuilder = builder;
    }

    private static final String TAG = JokesAsyncTask.class.getCanonicalName();

    public AsyncResponse mCaller = null;

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params)  {

        if (myApiService == null)  {
            myApiService = mBuilder.build();
        }

        context = params[0].first;

        try  {
            return myApiService.tellJoke().execute().getData();

        } catch (IOException e)  {
            Log.e(TAG, "Error: " + e.getMessage());
            return context.getResources().getString(R.string.jokes_unavailable);

        }
    }

    @Override
    protected void onPostExecute(String result)  {
        mCaller.processFinish(result);
    }

}
