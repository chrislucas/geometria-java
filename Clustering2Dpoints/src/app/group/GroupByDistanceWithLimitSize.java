package app.group;

import app.entity.Point2f;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * Aplicacao interessante para plotar pontos num plano
 * https://www.desmos.com/calculator/mhq4hsncnh
 * */

public class GroupByDistanceWithLimitSize implements Group {

    private List<Point2f> points;
    private int limitSizeSet;

    private final LinkedHashMap<Point2f, LinkedHashSet<Point2f>> group;

    private static final double MAX = Double.MAX_VALUE;

    public GroupByDistanceWithLimitSize(List<Point2f> points, int limitSizeSet) {
        this.points = points;
        this.limitSizeSet = limitSizeSet;
        this.group = new LinkedHashMap<>();
        for (Point2f point : points) {
            if (point.isReference())
                group.put(point, new LinkedHashSet<>());
        }
    }

    public GroupByDistanceWithLimitSize(List<Point2f> points) {
        this.points = points;
        this.group = new LinkedHashMap<>();
        int referencePoints = 0;
        for (Point2f point : points) {
            if (point.isReference()) {
                group.put(point, new LinkedHashSet<>());
                referencePoints++;
            }
        }
        /**
         * Quantidade maxima de pontos 'locais' que podem ser agrupados num ponto
         * de referencia
         * */
        this.limitSizeSet = (points.size() - referencePoints) / referencePoints
                + ((points.size() - referencePoints) % referencePoints);
    }

    private Point2f [] getRefPoints(boolean [] flag, int q) {
        Point2f [] points = new Point2f[q];
        Point2f [] ref = (Point2f[]) group.keySet().toArray();
        for (int i=0, acc=0; i<ref.length; i++) {
            if (flag[i])
                points[acc++] = ref[i];
        }
        return points;
    }

    /**
     * Recuperar todos os pontos de referencia
     * */
    private Point2f [] getRefPoints() {
        return (Point2f[]) group.keySet().toArray();
    }

    private int getQuantityGroupedPoints(Point2f p) {
        return group.get(points).size();
    }

    private Point2f getPointMinDistance(Point2f [] reference, Point2f origin) {
        double minDistance = MAX;
        Point2f ans = reference[0];
        for (Point2f ref : reference) {
            double distance = ref.distance(origin);
            if (distance < minDistance) {
                minDistance = distance;
                ans = ref;
            }
        }
        return ans;
    }

    private void remove(Iterator<Point2f> it) {
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }


    private void removePointFromRefere(Point2f ref, Point2f point2f) {
        group.get(ref).remove(point2f);
    }

    private void addPointToReference(Point2f ref, Point2f point2f) {
        group.get(ref).add(point2f);
    }

    private void findCandidateToChange(Point2f ref) {
        Point2f [] points = (Point2f[]) group.get(ref).toArray();
    }


    /**
     * Implementacao de metodo para agrupar pontos pela sua proximidade
     * */
    private Group groupPoints() {
        for (Point2f point2f : points) {
            if ( point2f.isReference() )
                continue;
            Point2f reference = getPointMinDistance(getRefPoints(), point2f);
            /**
             *
             * Se o ponto de 'referencia' nao estiver saturado, adicione a ele um ponto
             * de 'localidade'
             *
             * */
            if (getQuantityGroupedPoints(reference) <= limitSizeSet) {
                group.get(reference).add(point2f);
            }
            else {
                /**
                 * Se o ponto de refencia estiver saturado, encontre outro
                 * */
            }
        }
        return this;
    }


    @Override
    public Group apply() {
        return groupPoints();
    }

    /**
     * Nessa aplicacao vou escolher o ponto com o menor Y dentro do conjunto. A titulo
     * de teste
     * */

    @Override
    public Point2f chooseOnePoint(List<Point2f> points) {
        return getPointWithMinY();
    }

    private Point2f getPointWithMinY() {
        Point2f pMinY = points.get(0);
        for (int i = 1; i < points.size() ; i++) {
            if (pMinY.getY() > points.get(i).getY())
                pMinY = points.get(i);
        }
        return pMinY;
    }


    public LinkedHashMap<Point2f, LinkedHashSet<Point2f>> getGroup() {
        return group;
    }
}
