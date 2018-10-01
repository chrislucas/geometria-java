package app.group;

import app.entity.Point2f;

import java.util.*;


public class SimpleGroupByDistance implements Group {

    private final LinkedHashMap<Point2f, LinkedHashSet<Point2f>> group;

    private static final double MAX = Double.MAX_VALUE;

    private List<Point2f> points;

    public SimpleGroupByDistance(List<Point2f> points) {
        this.points = points;
        this.group = new LinkedHashMap<>();
        for (Point2f point : points) {
            if (point.isReference())
                group.put(point, new LinkedHashSet<>());
        }
    }

    public LinkedHashMap<Point2f, LinkedHashSet<Point2f>> getGroup() {
        return group;
    }

    private void remove(Iterator<Point2f> it) {
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    /**
     * Adiciona os  pontos numa estrutura similar a uma lista de adjacencia.
     * Os 'pontos referencia'
     * */
    private Group join() {
        for (Point2f point2f : points) {
            if (point2f.isReference())
                continue;
            double minDistance = MAX;
            List<Point2f> refs = new ArrayList<>();
            /**
             * Para agrupar os 'pontos residenciais' aos 'pontos de referencia'
             * procuramos qual o ponto referencia mais proximo de um ponto residencial
             * */
            for (Point2f reference : group.keySet()) {
                // distancia entre um ponto residencial para um ponto de referencia
                double distance = reference.distance(point2f);
                if ( distance < minDistance) {
                    minDistance = distance;
                    // Se encontrarmos um ponto referencia que seja mais proximo do ponto residencial
                    // removemos todos os pontos que eram considerados os mais proximos
                    remove(refs.iterator());
                    // adicionamos o ponto referencia mais proximo
                    refs.add(reference);
                }
                /**
                 * Se existir mais de 1 'ponto referencia' com distancia minima
                 * (Tal ponto residencial esta entre 2 ou mais pontos referencia),
                 * adicione-o a uma lista para aplicarmos algum criterio que escolha
                 * quais dos pontos referencia
                 **/
                else if (distance == minDistance && minDistance != MAX) {
                    refs.add(reference);
                }
            }
            if (refs.size() > 1) {
                /**
                 * Caso tenhamos mais de um ponto referencia com distancia minima do
                 * ponto residencial em questao, aplicamos um criterio para escolha
                 * do melhor ponto de refencia.
                 * */
                Point2f ref = chooseOnePoint(refs);
                group.get(ref).add(point2f);
            }
            else {
                group.get(refs.get(0)).add(point2f);
            }
        }
        return this;
    }

    @Override
    public Group apply() {
        return join();
    }

    /**
     * Para esse exemplo vamos escolher o primeiro ponto que tiver
     * o menor valor de Y
     * */

    @Override
    public Point2f chooseOnePoint(List<Point2f> points) {
        Point2f pMinY = points.get(0);
        for (int i = 1; i < points.size() ; i++) {
            if (pMinY.getY() > points.get(i).getY())
                pMinY = points.get(i);
        }
        return pMinY;
    }

    private void showPoints2() {
        for (Point2f p : points)
            System.out.println(p);
    }


    public void showPoints() {
        for (int i=0; i<points.size(); i++) {
            Point2f p =  points.get(i);
            System.out.printf(i == 0 ? "(%f, %f)" : ", (%f, %f)"
                    , p.getX(), p.getX());
        }
    }


}
