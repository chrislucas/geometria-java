package problems;

import java.io.*;
import java.util.StringTokenizer;
/**
 * https://www.hackerrank.com/challenges/rectangular-game/problem
 * DONE
 * */
public class RectangularGame {
    static int [][] pairs, matrix;
    public static void fill(int i, int j) {
        for (int k = 1; k <= i; k++) {
            for (int l = 1; l <= j; l++) {
                matrix[k][l] += 1;
            }
        }
    }
    public long s1(int cases, int maxI, int maxJ) {
        long maxCount = 0;
        // dimensao da matriz retangular
        matrix = new int[maxI+1][maxJ+1];
        for(int i=0; i<cases; i++) {
            fill(pairs[i][0], pairs[i][1]);
        }
        int max = matrix[1][1];
        for(int i=1; i<=maxI; i++) {
            for (int j = 1; j <= maxJ ; j++) {
                if(matrix[i][j] == max)
                    maxCount++;
            }
        }
        return maxCount;
    }

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            pairs = new int[cases][2];
            StringTokenizer tk;
            long minI = Long.MAX_VALUE, minJ = Long.MAX_VALUE;
            for(int i=0; i<cases; i++){
                tk = new StringTokenizer(bufferedReader.readLine(), " ");
                pairs[i][0] = Integer.parseInt(tk.nextToken());
                pairs[i][1] = Integer.parseInt(tk.nextToken());
                if(minI > pairs[i][0])
                    minI = pairs[i][0];
                if(minJ > pairs[i][1])
                    minJ = pairs[i][1];
            }
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
            printWriter.printf("%d\n", minI * minJ);
        } catch (IOException e) {}
    }
}
