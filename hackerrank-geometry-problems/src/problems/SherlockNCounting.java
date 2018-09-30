package problems;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Math.*;

/**
 * https://www.hackerrank.com/challenges/sherlock-and-counting/problem
 * */
public class SherlockNCounting {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);
    /**
     *
     * Dados valores n e k tal que
     *
     * i * (n-i) <= n*k and i < n
     *
     * Exemplo: n = 5, k = 1
     * nk = 5
     *
     *
     * */

    public static long solver1(long n, long k) {
        long b      = n;
        long c      = n*k;
        double d    = b*b-4*c;
        if(d < 0)
            return n-1;
        long rs = (long) floor((n  - ceil(sqrt(d))) / 2) * 2;
        return rs;
    }

    public static void main(String[] args) {
        try {
            int cases = Integer.parseInt(reader.readLine());
            while (cases-->0) {
                StringTokenizer tk = new StringTokenizer(reader.readLine(), " ");
                long n = Long.parseLong(tk.nextToken());
                long k = Long.parseLong(tk.nextToken());
                writer.printf("%d\n", solver1(n, k));
            }
        } catch (IOException e) {}
    }
}
