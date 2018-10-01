package app.test;


import app.entity.Point2f;
import app.group.Group;
import app.group.GroupByDistanceWithLimitSize;
import app.group.SimpleGroupByDistance;

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

public class TestByMinDistance {

    private static void test() {
        Point2f[][] matrixTest = {
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
        TestResult.showResult(simpleMethod(points), points);
    }

    private static LinkedHashMap<Point2f, LinkedHashSet<Point2f>> simpleMethod(ArrayList<Point2f> points) {
        // um metodo que so considera adistancia euclidiana sem levar em consideracao
        // quantos pontos estao em cada grupo;
        Group simpleGroupByDistance = new SimpleGroupByDistance(points);
        return ((SimpleGroupByDistance) simpleGroupByDistance.apply()).getGroup();
    }

    public static void main(String[] args) {
        test();
    }

}
