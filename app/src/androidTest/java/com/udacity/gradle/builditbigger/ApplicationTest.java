package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Pair;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {
        super(Application.class);
    }

    @Test
    public void testJokesAsyncTask() throws Exception  {

        // This object will receive the result of the AsyncTask
        JokesAsyncTaskRecipient jokeRecipient = new JokesAsyncTaskRecipient();

        /*
        * Create an AsyncTask to get a joke.  When the task completes, it
        * will call jokeRecipient.processFinish().
        */
        JokesAsyncTask asyncTask = new JokesAsyncTask();
        asyncTask.mCaller = jokeRecipient;
        asyncTask.execute(new Pair<>(getContext(), ""));

        /*
        * Get the joke from jokeRecipient.  Note that this will block
        * until the AsyncTask completes.
        */
        String joke = jokeRecipient.getJoke();

        assertTrue((joke != null) && (joke.length() > 0));
        
        // Make sure we didn't get the default "Sorry,  Jokes not available..."
        assertFalse(
                getContext().getResources().getString(R.string.jokes_unavailable).equals(joke));
    }

    private class JokesAsyncTaskRecipient implements JokesAsyncTask.AsyncResponse  {

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