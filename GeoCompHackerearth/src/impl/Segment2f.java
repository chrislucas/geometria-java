package impl;

public class Segment2f {
    
    public static class Point2f {
        double x, y;
        public Point2f(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public Point2f p, q;

    public Segment2f(Segment2f.Point2f p, Segment2f.Point2f q) {
        this.p = p;
        this.q = q;
    }

    // norma no caso de vetores

    public double norm() {
        return Math.sqrt( (q.x-p.x)*(q.x-p.x) + (q.y-p.y)*(q.y-p.y));
    }

    public double distance() {
        return Math.sqrt( (q.x-p.x)*(q.x-p.x) - (q.y-p.y)*(q.y-p.y));
    }

    /**
     * // distance ( (ax, by, c), P(x0,y0) ) =  (ax0 + by0 + c) / sqrt(a*a+b*b)
     * // https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
     * // equacao da reta usando 2 pontos
     * // http://www.coolmath.com/algebra/08-lines/12-finding-equation-two-points-01
     * */
    // (y2 - y1)x - (x2 - x1)y + (x2y1 * y2x1)
    public double distancePointToLine(Segment2f.Point2f r) {
        double diffY2Y1  = q.y-p.y;
        double diffX2X1  = q.x-p.x;
        double crossX2Y1 = q.x*p.y;
        double crossY2X1 = q.y*p.x;
        return Math.abs(diffY2Y1 * r.x - diffX2X1 * r.y + crossX2Y1 - crossY2X1)
                / Math.sqrt(diffY2Y1*diffY2Y1+diffX2X1*diffX2X1);
    }
    // (py - qy)x + (qx - px)y + (pxqy - qxpy)
    public  double distancePointToLine2(Segment2f.Point2f r) {
        double diffY1Y2  = p.y-q.y;
        double diffX2X1  = q.x-p.x;
        double crossX1Y2 = p.x*q.y;
        double crossX2Y1 = q.x*p.y;
        return Math.abs( diffY1Y2 * r.x + diffX2X1 * r.y + crossX1Y2 - crossX2Y1)
                / Math.sqrt(diffY1Y2*diffY1Y2+diffX2X1*diffX2X1);
    }

    public double dotProduct(Segment2f that, double theta) {
        double mag1 = Math.sqrt((p.x - q.x)*(p.x - q.x)+( p.y - q.y)*(p.y - q.y));
        double mag2 = Math.sqrt((that.p.x - that.q.x)*(that.p.x - that.q.x)+(that.p.y - that.q.y)*(that.p.y - that.q.y));
        return mag1 * mag2 * Math.cos(theta*Math.PI/180);
    }

    public double dotProduct(Segment2f that) {
        return (p.x-q.x)*(that.p.x - that.q.x)+(p.y - q.y)*(that.p.y - that.q.y);
    }

    public double crossProduct(Segment2f that, double theta) {
        double mag1 = Math.sqrt((p.x - q.x)*(p.x - q.x)+( p.y - q.y)*(p.y - q.y));
        double mag2 = Math.sqrt((that.p.x - that.q.x)*(that.p.x - that.q.x)+(that.p.y - that.q.y)*(that.p.y - that.q.y));
        return mag1 * mag2 * Math.sin(theta*Math.PI/180);
    }
}
