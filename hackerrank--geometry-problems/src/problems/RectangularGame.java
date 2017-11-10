package problems;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * https://www.hackerrank.com/challenges/rectangular-game/problem
 * */
public class RectangularGame {

    static int pairs [][];

    public static void main(String[] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.int));

        try {
            int cases = Integer.parseInt(bufferedReader.readLine());
            pairs = new int[cases][2];
            StringTokenizer tk;
            int maxI = 0, maxJ = 0;
            for(int i=0. i<cases; i++){
                tk = new StringTokenizer(bufferedReader.readLine(), " ");
                pairs[i][0] = Integer.parseInt(tk.nextToken());
                pairs[i][1] = Integer.parseInt(tk.nextToken());
                if(maxI < pairs[i][0])
                    maxI = pairs[i][0];
                if(maxJ < pairs[i][1])
                    maxJ = pairs[i][1];
            }

            // dimensao da matriz retangular

            for(int i=0; i<cases; i++) {
                //pairs[i][0];
                //pairs[i][1];
            }


        } catch (IOException e) {}


    }
}
