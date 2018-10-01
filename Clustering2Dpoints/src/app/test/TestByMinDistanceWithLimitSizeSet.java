package app.test;

import app.entity.Point2f;
import app.group.Group;
import app.group.GroupByDistanceWithLimitSize;

import java.util.*;

public class TestByMinDistanceWithLimitSizeSet {

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
        TestResult.showResult(advancedMethod(points), points);
    }

    private static LinkedHashMap<Point2f, LinkedHashSet<Point2f>> advancedMethod(ArrayList<Point2f> points) {
        Group advanced = new GroupByDistanceWithLimitSize(points);
        return ((GroupByDistanceWithLimitSize) advanced).getGroup();
    }

    public static void main(String[] args) {
        test();
    }
}
