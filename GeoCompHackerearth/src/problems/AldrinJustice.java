package problems;


import java.io.*;
import java.util.StringTokenizer;

/**
 * https://www.hackerearth.com/practice/basic-programming/implementation/basics-of-implementation/practice-problems/algorithm/aldrin-justice/description/
 * */

public class AldrinJustice {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out)
            , true);
    public static void main(String[] args) {
        try {

            int cases = Integer.parseInt(reader.readLine());
            while (cases-->0) {
                int p,q,r,s;
                StringTokenizer tk = new StringTokenizer(reader.readLine(), " ");
                p = Integer.parseInt(tk.nextToken());
                q = Integer.parseInt(tk.nextToken());
                r = Integer.parseInt(tk.nextToken());
                s = Integer.parseInt(tk.nextToken());
                int u = Math.max(Math.min(p, q),  Math.min(r, s));
                int v = Math.min(Math.max(p, q),  Math.max(r, s));
                if(v>u)
                    writer.printf("Nothing\n");
                else if(v==u)
                    writer.printf("Point\n");
                else
                    writer.printf("Line\n");
            }

        } catch (IOException e) {

        }
    }
}
