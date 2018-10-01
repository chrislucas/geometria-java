package problems;


import java.io.*;
import java.util.StringTokenizer;

/**
 * https://www.hackerrank.com/challenges/xrange-and-pizza/problem
 * */

public class Xrange {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    private static double angleBetweenAxes(int nAxes) {
        return 360f / (2f * nAxes);
    }

    private static double rotate(int k, int nAxes) {
        return (360f * k / nAxes) % 360;
    }

    public static void run() {

    }

    private static double [] initialAngles, copyAngles;

    /**
     * 1 rotate, 2 flip
     * */

    public static void main(String[] args) {
        try {
            StringTokenizer tk = new StringTokenizer(reader.readLine(), " ");
            int nAxes = Integer.parseInt(tk.nextToken());
            int nLines = Integer.parseInt(tk.nextToken());
            initialAngles = new double[nAxes];
            initialAngles[0] = angleBetweenAxes(nAxes);
            copyAngles[0] = angleBetweenAxes(nAxes);
            for(int i = 0; i < nAxes; i++) {
                initialAngles[i] += initialAngles[i-1];
                copyAngles[i] += copyAngles[i-1];
            }

            for (int i = 0; i < nLines ; i++) {
                tk = new StringTokenizer(reader.readLine(), " ");
                int type = Integer.parseInt(tk.nextToken());
                int axis = Integer.parseInt(tk.nextToken());
                // flip
                if(type == 1) {

                }
                // rotate
                else {
                    copyAngles[axis] = rotate(nAxes, axis);
                }
            }


        } catch (IOException ioex) {}
    }

}
