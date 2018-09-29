package problems;

import java.io.*;
import java.util.StringTokenizer;

/**
 * https://www.hackerrank.com/challenges/sherlock-and-planes/problem
 * DONE
 * */
public class SherlockAndPlanes {

    public static class Point3f {
        private final int x, y, z;
        public Point3f(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);
    private static void run() {
        try {
            int cases = Integer.parseInt(reader.readLine());
            while (cases-->0) {
                int matrix [][] = new int[4][3];
                for(int i=0; i<4; i++) {
                    StringTokenizer tk = new StringTokenizer(reader.readLine(), " ");
                    matrix[i][0] = Integer.parseInt(tk.nextToken());
                    matrix[i][1] = Integer.parseInt(tk.nextToken());
                    matrix[i][2] = Integer.parseInt(tk.nextToken());
                }
                /**
                 * Produto misto
                 * [u,v,w,z] = UV, UW, UZ respectivamente
                 * */
                int [][] anotherMatrix = {
                     {matrix[1][0] - matrix[0][0], matrix[1][1] - matrix[0][1], matrix[1][2] - matrix[0][2]}
                    ,{matrix[2][0] - matrix[0][0], matrix[2][1] - matrix[0][1], matrix[2][2] - matrix[0][2]}
                    ,{matrix[3][0] - matrix[0][0], matrix[3][1] - matrix[0][1], matrix[3][2] - matrix[0][2]}
                };

                int det = (anotherMatrix[0][0] * anotherMatrix[1][1] * anotherMatrix[2][2]
                        + anotherMatrix[0][1] * anotherMatrix[1][2] * anotherMatrix[2][0]
                        + anotherMatrix[0][2] * anotherMatrix[1][0] * anotherMatrix[2][1]
                        ) -
                        (
                            anotherMatrix[0][1]*anotherMatrix[1][0]*anotherMatrix[2][2]
                            +anotherMatrix[0][0]*anotherMatrix[1][2]*anotherMatrix[2][1]
                            +anotherMatrix[0][2]*anotherMatrix[1][1]*anotherMatrix[2][0]
                        )
                        ;
                writer.printf("%s\n", det == 0 ? "YES" : "NO");
            }
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        run();
    }
}
