package app;

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
        this.limitSizeSet = 0;
        this.group = new LinkedHashMap<>();
        for (Point2f point : points) {
            if (point.isReference())
                group.put(point, new LinkedHashSet<>());
        }
        for (Point2f point2f : points) {

        }
    }

    private void remove(Iterator<Point2f> it) {
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }



    @Override
    public Group apply() {
        return this;
    }

    /**
     * Nessa aplicacao vou escolher o ponto com o menor Y dentro do conjunto. A titulo
     * de teste
     * */

    @Override
    public Point2f chooseOnePoint(List<Point2f> points) {
        return null;
    }


    public LinkedHashMap<Point2f, LinkedHashSet<Point2f>> getGroup() {
        return group;
    }
}
