package app.test;

import app.entity.Point2f;
import app.group.Group;
import app.group.GroupByDistanceWithLimitSize;

import java.util.*;

public class TestByMinDistanceWithLimitSizeSet {

    private static void test() {
        Point2f [] p = {
              new Point2f(2, 2, true)
            , new Point2f(40, 40, true)
            // ponto residencial entre os pontos (40, 40) e (15, 15)
            , new Point2f(27.5, 27.5, false)
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
            , new Point2f(100, 30, false)
        };

        Point2f [] q = {
              new Point2f(300, 300, true)
            , new Point2f(100, 100, true)
            , new Point2f(200, 200, true)
            // ponto entre p(100, 100) e q(200, 200)
            , new Point2f(150, 150, false)
            // os pontos abaixo deveriam ficar juntos ao ponto(100, 100)
            , new Point2f(50, 50, false)
            , new Point2f(10, 10, false)
            , new Point2f(30, 30, false)
            , new Point2f(35, 35, false)
            , new Point2f(30, 35, false)
            , new Point2f(20, 25, false)
            , new Point2f(40, 40, false)
            , new Point2f(60, 60, false)
            , new Point2f(70, 70, false)
            , new Point2f(85, 85, false)
            , new Point2f(65, 65, false)

            // pontos que estao mais proximos de p(200, 200
            , new Point2f(220, 220, false)
            , new Point2f(220, 240, false)
            , new Point2f(220, 250, false)
            , new Point2f(220, 260, false)

            // pontos mais proximos de p(300, 300)
            , new Point2f(290, 250, false)
            , new Point2f(290, 290, false)
            , new Point2f(320, 290, false)
            , new Point2f(320, 300, false)
            , new Point2f(350, 300, false)
            , new Point2f(350, 350, false)
            , new Point2f(400, 350, false)
            , new Point2f(400, 400, false)
        };
        Point2f[][] matrixTest = {p, q};
        int idxTest = 1;
        ArrayList<Point2f> points = new ArrayList<>(Arrays.asList(matrixTest[idxTest]));
        TestResult.showResult(advancedMethod(points), points);
    }

    private static LinkedHashMap<Point2f, LinkedHashSet<Point2f>> advancedMethod(ArrayList<Point2f> points) {
        Group advanced = new GroupByDistanceWithLimitSize(points);
        return ((GroupByDistanceWithLimitSize) advanced.apply()).getGroup();
    }

    public static void main(String[] args) {
        test();
    }
}
