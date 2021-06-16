

//package edu.princeton.cs.algs4;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;



public class RandomWord {
    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        //System.out.println("Hello, RandomWord");

        String str = "";
        String strTemp = "";

        // 1. reads a sequence of words from standard input


        String champion = null;
        boolean replaceBool = false;
        boolean keepGoing = true;
        double iter = 0.0;


        while(keepGoing) {
            if (!StdIn.isEmpty()) {
                str = StdIn.readString();
                iter = iter + 1.0;
            }
            else {
                keepGoing = false;
            }

            // 2. prints one of those words uniformly at random

            // 3. Do not store the words in an array or list.
            //          Instead, use Knuth’s method: when reading the ith word, select it with
            //          probability 1/i to be the champion, replacing the previous champion.

            replaceBool = StdRandom.bernoulli(1.0 / iter);
            if (replaceBool) {
                champion = str;
            }

            //StdOut.println("str=" + str + ", len=" + str.length() + ", iter=" + iter + ", champion=" + champion);
        }


        // 4. After reading all of the words, print the surviving champion.
        //StdOut.println("champion="+champion);
        StdOut.println(champion);


    }

}
