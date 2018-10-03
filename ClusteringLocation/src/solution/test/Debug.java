package solution.test;

import solution.entity.Location;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

class Debug {
    static void showResults(LinkedHashMap<Location, LinkedHashSet<Location>> graph, List<Location> locations) {
        int groupedPoints = 0, referencesPoints = 0;
        for (Map.Entry<Location, LinkedHashSet<Location>> e : graph.entrySet()) {
            LinkedHashSet<Location> origins = e.getValue();
            int s = origins.size();
            System.out.printf("%s - %d pontos conectados\n\n", e.getKey(), s);

            groupedPoints += s;
            referencesPoints++;

            for (Location origin : origins) {
                System.out.printf("Ponto de Origem %s\n", origin);
            }
            System.out.println("\n");
        }

        System.out.printf("%d pontos %s\n%d Pontos agrupados a %d pontos de referência\n"
                , locations.size()
                , locations.size() > 1 ? "definidos" : "definido"
                , groupedPoints
                , referencesPoints);
    }

}
