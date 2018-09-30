package problems.nivel2;


import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

import static java.lang.Math.*;

/**
 * https://www.udebug.com/URI/8-computational-geometry
 * https://www.urionlinejudge.com.br/judge/pt/problems/view/1721
 * */
public class URI1721 {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);
    private static final int RADIUS = 6378;
    private static final class GeoLocation2f {
        private double lat; double lon;
        public GeoLocation2f(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }

    public static double toRadians2(double val) {
        return val * (PI / 180.0f);
    }
    /*
        a.lat = 47.6788206;
        a.lon = -122.3271205;
        b.lat = 47.6788206;
        b.lon = -122.5271205;
    */
    public static double distance(GeoLocation2f a, GeoLocation2f b) {
        double deltaLatAB = toRadians2(b.lat - a.lat);
        double deltaLonAB = toRadians2(b.lon - a.lon);
        double latAInRadians = toRadians2(a.lat);
        double latBInRadians = toRadians2(b.lat);
        double r = haversinCos(deltaLatAB)
                + cos(latAInRadians) * cos(latBInRadians)
                * haversinCos(deltaLonAB);
        //2 * atan2(sqrt(r), sqrt(1-r)) * RADIUS;
        return 2 * asin(sqrt(r)) * RADIUS;
    }

    public static double distance2(GeoLocation2f a, GeoLocation2f b) {
        double deltaLatAB = toRadians2(b.lat - a.lat);
        double deltaLonAB = toRadians2(b.lon - a.lon);
        double latAInRadians = toRadians2(a.lat);
        double latBInRadians = toRadians2(b.lat);
        //double r = (1f - cos(deltaLatAB)) * .5f + cos(latAInRadians) * cos(latBInRadians) * (1 - cos(deltaLonAB)) *.5f;
        double r = .5f - cos(deltaLatAB) * .5f + cos(latAInRadians) * cos(latBInRadians) * (1 - cos(deltaLonAB)) *.5f;
        return 2 * RADIUS * asin(sqrt(r));
    }

    public static double haversinSin(double value) {
        double s = sin(value/2.0f);
        return s * s;
    }

    public static double haversinCos(double value) {
        return (1-cos(value)) / 2.0f;
    }

    public static void main(String[] args) {
        HashMap<String, GeoLocation2f>  map = new HashMap<>();
        try {
            String in = "";
            int i = 0;
            while ( ! (in = reader.readLine()).equals("#")) {
                StringTokenizer tk = new StringTokenizer(in, " ");
                String s = tk.nextToken();
                double lat = Double.parseDouble(tk.nextToken());
                double lon = Double.parseDouble(tk.nextToken());
                map.put(s, new GeoLocation2f(lat, lon));
            }
            while ( ! (in = reader.readLine()).equals("#") ) {
                StringTokenizer tk = new StringTokenizer(in, " ");
                String a =  tk.nextToken(), b = tk.nextToken(), c = tk.nextToken();
                GeoLocation2f locA = map.get(a);       // alice's place
                GeoLocation2f locB = map.get(b);       // bob's place
                GeoLocation2f locC = map.get(c);       // possible place
                boolean existsLocA = locA != null;
                boolean existsLocB = locB != null;
                if(existsLocA & existsLocB) {
                    GeoLocation2f meanLocation = new GeoLocation2f((locA.lat + locB.lat)  *.5f, (locA.lon + locB.lon)  *.5f);
                    int ans = (int) round(distance2(locC, meanLocation)) ;
                    writer.printf("%s is %d km off %s/%s equidistance.\n", c, ans, a, b);
                }
                else {
                    writer.printf("%s is ? km off %s/%s equidistance.\n", c, a, b);
                }
            }
        } catch (IOException ioex) {}

    }
}
