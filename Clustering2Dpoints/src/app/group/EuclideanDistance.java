package app.group;

import app.entity.Point2f;

import java.util.Comparator;

public class EuclideanDistance implements Comparator<Point2f> {

    private Point2f local;
    public EuclideanDistance(Point2f local) { this.local = local; }

    @Override
    public int compare(Point2f p, Point2f q) {
        double dpl = local.distance(p);
        double dql = local.distance(q);
        debug(p, dpl);
        debug(q, dql);
        return Double.compare(dpl, dql);
    }

    private void debug(Point2f q, double distance) {
        System.out.printf("Distancia entre %s e %s eh %f\n"
                , local
                , q
                , distance);
    }
}
