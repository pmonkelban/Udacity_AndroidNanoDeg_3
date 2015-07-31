package com.udacity.gradle.jokes;

import java.util.Random;

public class Joker {

    private static final Random rnd = new Random(System.currentTimeMillis());

    public static String getJoke()  {

        return JOKES[rnd.nextInt(JOKES.length)];
    }

    /*
    * These jokes were "borrowed" from:
    * http://www.jokes4us.com/miscellaneousjokes/cleanjokes.html
    */
    private static final String[] JOKES = {
            "Two peanuts were walking down the street. One was asalted.",
            "I've just opened a new restaurant called Karma. There's no menu, we just give you what you deserve.",
            "I had a dream I was a muffler and I woke up exhausted.",
            "Today I gave my dead batteries away....Free of charge.",
            "Never give up on your dreams, keep sleeping.",
            "If you are running next to me on the treadmill, the answer is YES, we are racing.",
            "Being honest may not get you a lot of FRIENDS but it'll always get you the RIGHT ONES.",
            "I'm going to stand outside. So if anyone asks, I am outstanding.",
            "I am going bananas. That's what i say to my bananas before i leave the house.",
            "I'm so bright my mother calls me son.",
            "My eyelids are so sexy, I can't keep my eyes off them.",
            "The past, present and future walk into a bar. It was tense.",
            "One hat said to the other you stay here I'll go on a head",
            "Silence is golden, Duct tape is silver.",
            "I know some jokes about unemployment but they need some work.",
            "I have never seen a fruit PUNCH or a cereal BOX.",
            "If you think of a better fish pun. Let minnow.",
            "A three legged dog walks in the bar and says - \"I'm lookin' for the guy who shot my paw\"",
            "I tried to catch some fog earlier. I mist.",
            "Change is hard. Have you ever tried to bend a coin?",
            "If money doesn't grow on trees why do banks have branches?",
            "Did you hear about the farmer who fed his cows birdseed and started selling cheep milk.",
            "A butcher goes on a first date and says 'It was nice meating you'.",
            "Man that's Ludacris I can't believe I got fired from the calendar factory. All I did was take a day off.",
            "I wonder if earth makes fun of other planets for having no life.",
            "It's been scientifically proven that too many birthdays can kill you!",
            "Don't tell secrets in a cornfield. There a too many ears.",
            "Why do we cook bacon and bake cookies?",
            "Why do you drive down a parkway but park in a driveway?",
            "fi yuo cna raed tihs whit no porlbem, yuo aer smrat. Shaer ti whit yuor fienrds.",
            "I hated my job as an origami teacher. Too much paperwork.",
            "I love pressing F5. It's so refreshing.",
            "Why is everything delivered by a ship called cargo but if it's delivered by a car it's a shipment?",
            "When I die, I want my tombstone to be a WiFi hotspot......that way people visit more often.",
            "Why do they call it a hot water heater when you don't have to heat hot water?",
            "What happens when you get scared half to death twice?",
            "A police recruit was asked during the exam, \"What would you do if you had to arrest your own mother?\" He said, \"Call for backup.\""
    };

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++)  {
            System.out.println(getJoke());
        }
    }
}
