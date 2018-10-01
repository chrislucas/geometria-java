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

    private void remove(Iterator<Point2f> it) {
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    /**
     * Implementacao de metodo para agrupar pontos pela sua proximidade
     * */
    private Group groupPoints() {
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
