package app.test;

import app.entity.Point2f;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class TestResult {

    public static  void showResult(LinkedHashMap<Point2f, LinkedHashSet<Point2f>> group
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
}
