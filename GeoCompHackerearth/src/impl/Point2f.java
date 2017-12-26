package impl;

public class Point2f {


    private double x, y;
    public Point2f(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double squaredDistance(Point2f p) {
        double diffX = p.x-x;
        double diffY = p.y-y;
        return diffX*diffX+diffY*diffY;
    }
    /**
     * {-1,0,1} // horario, colinear, anti-horario
     * */
    public static int ccw(Point2f a, Point2f b, Point2f c) {
        double diffX1 = b.x - a.x;
        double diffX2 = c.x - b.x;
        double diffY1 = b.y - a.y;
        double diffY2 = c.y - b.y;
        double area = diffY1*diffX2-diffX1*diffY2;
        // anti horario
        if(area < 0)
            return 1;
        // horario;
        else if(area > 0)
            return -1;
        // colinear
        return 0;
    }

    public static double areaOfPolygon(Point2f [] points) {
        double acc =  0;
        int n = points.length;
        for(int i=0; i<n; i++)
            acc += (points[i].x * points[(i+1)%n].y) - (points[i].y * points[(i+1)%n].x);
        return acc * 0.5f; //Math.abs(acc * 0.5f);
    }

    public static double crossProduct(Point2f a, Point2f b, Point2f c) {
        double abx = b.x - a.x;
        double aby = b.y - a.y;
        double acx = c.x - a.x;
        double acy = c.y - a.y;
        return abx * acy + aby * acx;
    }

    @Override
    public String toString() {
        return String.format("%f %f", x, y);
    }

    public static class Segment2f {
        public Point2f p, q;
        public Segment2f(Point2f p, Point2f q) {
            this.p = p;
            this.q = q;
        }
        public double distance() {
            double diffX = q.x - p.x;
            double diffY = q.y - p.y;
            return Math.sqrt(diffX*diffX+diffY+diffY);
        }
    }

    // https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
    // equacao da reta usando 2 pontos
    // http://www.coolmath.com/algebra/08-lines/12-finding-equation-two-points-01
    public static double distancePointToLine(Segment2f segment2f, Point2f p) {
        double diffY2Y1  = segment2f.q.y - segment2f.p.y;
        double diffX2X1  = segment2f.q.x - segment2f.p.x;
        double crossX2Y1 = segment2f.q.x * segment2f.p.y;
        double crossY2X1 = segment2f.q.y * segment2f.p.x;
        double acc = Math.abs(diffY2Y1 * p.x - diffX2X1 * p.y + crossX2Y1 - crossY2X1)
                / Math.sqrt(diffY2Y1*diffY2Y1+diffX2X1*diffX2X1);
        return acc;
    }
}
