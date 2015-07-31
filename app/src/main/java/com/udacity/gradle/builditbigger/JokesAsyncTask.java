package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.backend.myApi.MyApi;

import java.io.IOException;

public class JokesAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    public interface AsyncResponse  {
        void processFinish(String output);
    }

    private static final String TAG = JokesAsyncTask.class.getCanonicalName();

    public AsyncResponse mCaller = null;

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params)  {

        if (myApiService == null)  {
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    .setRootUrl("http://192.168.2.139:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://udacity-jokes.appspot.com/_ah/api");

            myApiService = builder.build();
        }

        context = params[0].first;

        try  {
            return myApiService.sayHi().execute().getData();

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
