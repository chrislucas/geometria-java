package problems;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * https://www.hackerrank.com/challenges/xrange-and-pizza/problem
 * */

public class Xrange {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    public static double angleBetweenAxes(int nAxes) {
        return 360f / (2f * nAxes);
    }

    public static double rotate(int k, int nAxes) {
        return (360f * k / nAxes) % 360;
    }

    public static void run() {

    }

    /**
     * 1 rotate, 2 flip
     * */

    public static void main(String[] args) {

    }

}
