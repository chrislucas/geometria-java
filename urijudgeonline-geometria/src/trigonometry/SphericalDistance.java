package trigonometry;

public class SphericalDistance {

    public static double distance(double lat, double lon, double lat2, double lon2, double radius) {
        double a = Math.cos(Math.toRadians(lat))
                * Math.cos(Math.toRadians(lat2))
                + Math.sin(Math.toRadians(lat))
                * Math.sin(Math.toRadians(lat2))
                * Math.cos(Math.toRadians(lon2-lon));
        return a * radius;
    }

    // sin^2(theta/2) = (1 - cos(theta)) / 2
    // value deve estar em radianos
    public static double formulaHaversinSin(double value) {
        double a = Math.sin(value / 2.0f);
        return a * a;
    }
    // value deve estar em radianos
    public static double formulaHaversinCos(double value) {
        return (1 - Math.cos(value)) *.5f;
    }

    public static double haversine(double lat, double lon, double lat2, double lon2, double radius) {
        double deltaLatHaversine = formulaHaversinSin(Math.toRadians(lat2 - lat));
        double deltaLonHaversine = formulaHaversinSin(Math.toRadians(lon2 - lon));
        double a = deltaLatHaversine + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(lat2)) + deltaLonHaversine;
        // 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)) * radius
        return 2 * Math.asin(Math.sqrt(a)) * radius;
    }

    public static void main(String[] args) {
        System.out.println(distance(48.700d, 10.500d, 39.883d, -75.250d, 6356.752f));
        System.out.println(haversine(48.700d, 10.500d, 39.883d, -75.250d, 6356.752f));
    }

}
