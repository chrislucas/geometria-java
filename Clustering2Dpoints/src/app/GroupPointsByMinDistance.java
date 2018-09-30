package app;


import java.awt.geom.Point2D;
import java.util.*;


/**
 * Essa classe implementa um algoritmo para agrupar pontos num plano cartesiano
 * baseado na distacia euclidiana entre eles
 *
 * O problema queremos resolver é:
 *
 * Dado N pontos denominados de 'pontos de referencia' e M pontos denominados
 * 'pontos residenciais' como agrupar os residencias aos de referencia tal que
 * a distancia entre tais pontos seja a minima possível
 *
 * */

public class GroupPointsByMinDistance {

    private static void test() {
        Point2f [][] matrixTest = {
            {
                new Point2f(2, 2, true)
                , new Point2f(40, 40, true)
                , new Point2f(27.5, 27.5, false) // ponto residencial entre os pontos (40, 40) e (15, 15)
                , new Point2f(15, 15, true)
                , new Point2f(1, 10, false)
                , new Point2f(10, 10, true)
                , new Point2f(2, 10, false)
                , new Point2f(2, 16, false)
                , new Point2f(10, 16, false)
                , new Point2f(16, 12, false)
                , new Point2f(20, 12, false)
                , new Point2f(30, 16, false)
                , new Point2f(30, 16, false)
                , new Point2f(35, 18, false)
                , new Point2f(48, 32, false)
                , new Point2f(37, 15, false)
                , new Point2f(12, 15, false)
                , new Point2f(20, 15, false)
                , new Point2f(32, 15, false)
                , new Point2f(50, 15, false)
            }
        };
        int idxTest = 0;
        ArrayList<Point2f> points = new ArrayList<>(Arrays.asList(matrixTest[idxTest]));
        showResult(simpleMethod(points), points);
        //showResult(advancedMethod(points), points);
    }

    private static LinkedHashMap<Point2f, LinkedHashSet<Point2f>> simpleMethod(ArrayList<Point2f> points) {
        // um metodo que so considera adistancia euclidiana sem levar em consideracao
        // quantos pontos estao em cada grupo;
        Group simpleGroupByDistance = new SimpleGroupByDistance(points);
        return ((SimpleGroupByDistance) simpleGroupByDistance.apply()).getGroup();
    }


    private static LinkedHashMap<Point2f, LinkedHashSet<Point2f>> advancedMethod(ArrayList<Point2f> points) {
        Group advanced = new GroupByDistanceWithLimitSize(points);
        return ((GroupByDistanceWithLimitSize) advanced).getGroup();
    }


    private static  void showResult(LinkedHashMap<Point2f, LinkedHashSet<Point2f>> group
            , ArrayList<Point2f> points) {
        int groupedPoints = 0, referencesPoints = 0;
        for (Map.Entry<Point2f, LinkedHashSet<Point2f>> e : group.entrySet()) {
            Point2f u = e.getKey();
            System.out.printf("Origem %s\n", e.getKey());
            LinkedHashSet<Point2f> set = e.getValue();
            groupedPoints += set.size();
            referencesPoints++;
            for (Point2f v : set) {
                System.out.printf("Destino %s com distancia %f\n", v, u.distance(v));
            }
            System.out.println("\n");
        }

        System.out.printf("%d pontos %s\n%d Pontos agrupados a %d pontos de referência\n"
                , points.size()
                , points.size() > 1 ? "definidos" : "definido"
                , groupedPoints
                , referencesPoints
        );
    }

    public static void main(String[] args) {
        test();
    }

}
