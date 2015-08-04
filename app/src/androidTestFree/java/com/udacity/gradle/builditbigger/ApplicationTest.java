package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.backend.myApi.MyApi;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    /*
    * Need to change this to the IP address of the host.
    * Commented out are various options.  The first is the IP address of my host machine.
    * The second is the host IP address when using the Android Emulator
    * The third is the host IP address when using Genymotion.
    */

    //    private static final String APP_ENGINE_URL = "http://192.168.2.139:8080/_ah/api/";
    //    private static final String APP_ENGINE_URL = "http://10.0.2.2:8080/_ah/api/";
    private static final String APP_ENGINE_URL = "http://10.0.3.2:8080/_ah/api/";

    public ApplicationTest() {
        super(Application.class);
    }

    /*
    * Builder which connects to the Google Cloud Engine running on the localhost.
    */
    private MyApi.Builder mBuilder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl(APP_ENGINE_URL)
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                        throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });

    @Test
    public void testJokesAsyncTask() throws Exception {

        // This object will receive the result of the AsyncTask
        JokesAsyncTaskRecipient jokeRecipient = new JokesAsyncTaskRecipient();

        /*
        * Create an AsyncTask to get a joke.  When the task completes, it
        * will call jokeRecipient.processFinish().
        */
        JokesAsyncTask asyncTask = new JokesAsyncTask();
        asyncTask.setBuilder(mBuilder);
        asyncTask.setCaller(jokeRecipient);
        asyncTask.execute(new Pair<>(getContext(), ""));

        /*
        * Get the joke from jokeRecipient.  Note that this will block
        * until the AsyncTask completes.
        */
        String joke = jokeRecipient.getJoke();

        System.out.println("Joke: " + joke);

        assertTrue((joke != null) && (joke.length() > 0));

        // Make sure we didn't get the default "Sorry,  Jokes not available..."
        assertFalse(
                getContext().getResources().getString(R.string.jokes_unavailable).equals(joke));
    }

    private class JokesAsyncTaskRecipient implements JokesAsyncTask.AsyncResponse {

        /*
        * This CountDownLatch will allow us to wait until processFinish()
        * is called before responding to calls to getJoke().
        */
        CountDownLatch cdl = new CountDownLatch(1);
        String joke;

        @Override
        public void processFinish(String output) {
            joke = output;

            /*
            * Moves the countdown from 1 to 0.  This allows the
            * await() call in getJoke to proceed now that we
            * have a valid joke to return.
            */
            cdl.countDown();
        }

        public String getJoke() throws InterruptedException {

            // Block until processFinish() has been called.
            cdl.await();

            return joke;
        }
    }

}