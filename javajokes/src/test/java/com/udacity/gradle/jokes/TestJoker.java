package com.udacity.gradle.jokes;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestJoker {

    /*
    * Ensure that we can get a joke and that it is not null or empty.
    */
    @Test
    public void test1()  {
        String joke = Joker.getJoke();
        assertNotNull(joke);
        assertTrue(joke.length() > 0);
    }
}
