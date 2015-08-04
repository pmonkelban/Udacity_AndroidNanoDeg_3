package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.udacity.gradle.backend.myApi.MyApi;

import java.io.IOException;

public class JokesAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static final String TAG = JokesAsyncTask.class.getCanonicalName();

    private MyApi.Builder mBuilder;
    private AsyncResponse mCaller = null;

    private static MyApi myApiService = null;
    private Context context;


    public interface AsyncResponse  {
        void processFinish(String output);
    }

    public void setBuilder(MyApi.Builder builder)  {
        mBuilder = builder;
    }

    public void setCaller(AsyncResponse caller)  {
        mCaller = caller;
    }

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
