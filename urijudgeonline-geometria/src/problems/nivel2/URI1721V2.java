package problems.nivel2;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

import static java.lang.Math.*;

/**
 * https://www.urionlinejudge.com.br/judge/en/problems/view/1721
 * Usando dot product. Não sou autor dessa solucao, devo estudar mais sobre
 * como se aplica dot product em distancia entre pontos numa esfera
 * */

public class URI1721V2 {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);
    private static final int RADIUS = 6378;
    private static final class Point2f {
        private double x,y,z;
        public Point2f(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point2f difference(Point2f that) {
            return new Point2f(x - that.x, y - that.y, z - that.z);
        }

        public double length() {
            return sqrt(x*x+y*y+z*z);
        }
    }

    public static double s1(Point2f p, Point2f q, Point2f r) {
        Point2f n = p.difference(q);
        double dotProduct = n.x * r.x + n.y * r.y + n.z * r.z;
        double angle = acos(abs(dotProduct) / n.length() / r.length());
        return (acos(-1) / 2 - angle) * RADIUS;
    }

    public static void run() {
        HashMap<String, Point2f> map = new HashMap<>();
        try {
            String in = "";
            int i = 0;
            while ( ! (in = reader.readLine()).equals("#")) {
                StringTokenizer tk = new StringTokenizer(in, " ");
                String s = tk.nextToken();
                double lat = Double.parseDouble(tk.nextToken()) * PI / 180f;
                double lon = Double.parseDouble(tk.nextToken()) * PI / 180f;
                double x = RADIUS * cos(lat) * cos(lon);
                double y = RADIUS * cos(lat) * sin(lon);
                double z = RADIUS * sin(lat);
                map.put(s, new Point2f(x, y, z));
            }
            while ( ! (in = reader.readLine()).equals("#") ) {
                StringTokenizer tk = new StringTokenizer(in, " ");
                String a =  tk.nextToken(), b = tk.nextToken(), c = tk.nextToken();
                Point2f locA = map.get(a);       // alice's place
                Point2f locB = map.get(b);       // bob's place
                Point2f locC = map.get(c);       // possible place
                boolean existsLocA = locA != null;
                boolean existsLocB = locB != null;
                if(existsLocA & existsLocB) {
                    int answer = (int)round(s1(locA, locB, locC));
                    writer.printf("%s is %d km off %s/%s equidistance.\n", c, answer, a, b);
                }
                else {
                    writer.printf("%s is ? km off %s/%s equidistance.\n", c, a, b);
                }
            }
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        run();
    }
}
