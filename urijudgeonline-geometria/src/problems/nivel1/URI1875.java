package problems.nivel1;


import java.io.*;
import java.util.StringTokenizer;

/**
 * https://www.urionlinejudge.com.br/judge/pt/problems/view/1875
 * DONE
 * */
public class URI1875 {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);
    public static void main(String[] args) {
        try {
            int cases = Integer.parseInt(reader.readLine());
            while (cases-->0) {
                int goals = Integer.parseInt(reader.readLine());
                int counter [] = {0,0,0};
                while (goals-->0) {
                    StringTokenizer tk = new StringTokenizer(reader.readLine(), " ");
                    String a = tk.nextToken();
                    String b = tk.nextToken();
                    if(a.equals("R")) {
                        counter[0] += b.equals("G") ? 2 : 1;
                    }

                    else if(a.equals("G")) {
                        counter[1] += b.equals("B") ? 2 : 1;
                    }

                    else {
                        counter[2] += b.equals("R") ? 2 : 1;
                    }
                }
                if(counter[0] == counter[1] && counter[1] == counter[2])
                    writer.println("trempate");
                else {
                    if( (counter[0] == counter[1] && counter[0] > counter[2])
                            || (counter[1] == counter[2] && counter[1] > counter[0])
                            || (counter[0] == counter[2] && counter[0] > counter[1]))
                        writer.println("empate");
                    else {
                        int max = counter[0] > counter[1] ? 0 : 1;
                        max = counter[max] > counter[2] ? max : 2;
                        writer.println( max < 1 ? "red" : max == 1 ? "green" : "blue");
                    }
                }
            }
        } catch (IOException ieox) {}
    }
}
