package solution.test;

import solution.algorithm.Group;
import solution.algorithm.GroupLocationByDistanceAndTime;
import solution.entity.Location;

import java.util.*;

public class TestGroupLocationWithLimitSize {


    private static final Location p = new Location(0, true);
    private static final Location q = new Location(1, true);
    private static final Location u = new Location(2, true);

    private static final Location [][] locations = {
        {
              p
            , q
            , u
                // Melhor ponto 'U'
            , new Location(3
                , new ArrayList<>(Arrays.asList(
                          new Location(p, 100, 1000)
                        , new Location(q, 120, 1500)
                        , new Location(u, 80, 1000)
                    )
                )
            )
                // Melhor ponto 'U'
            , new Location(4
                , new ArrayList<>(Arrays.asList(
                          new Location(p, 120, 1000)
                        , new Location(q, 120, 1500)
                        , new Location(u, 80, 1000)
                    )
                )
            )
                // melhor ponto 'p'
            , new Location(5
                , new ArrayList<>(Arrays.asList(
                          new Location(p, 80, 1000)
                        , new Location(q, 130, 1500)
                        , new Location(u, 180, 1000)
                    )
                )
            )
                // melhor ponto 'p'
            ,
                new Location(6
                    , new ArrayList<>(Arrays.asList(
                          new Location(p, 80, 1200)
                        , new Location(q, 130, 1500)
                        , new Location(u, 180, 1000)
                    )
                )
            )
            // melhor ponto 'p'
            ,
                new Location(7
                    , new ArrayList<>(Arrays.asList(
                          new Location(p, 75, 1200)
                        , new Location(q, 130, 1500)
                        , new Location(u, 180, 1000)
                    )
                )
            )
            // melhor ponto 'p'
            ,
                new Location(8
                    , new ArrayList<>(Arrays.asList(
                        new Location(p, 75, 1200)
                        , new Location(q, 130, 1000)
                        , new Location(u, 120, 800)
                    )
                )
            )
            , // melhor ponto: 'q'
                new Location(9
                    , new ArrayList<>(Arrays.asList(
                          new Location(p, 75, 1200)
                        , new Location(q, 30, 1500)
                        , new Location(u, 180, 1000)
                    )
                )
            )

            , // melhor ponto: 'q'
                new Location(10
                    , new ArrayList<>(Arrays.asList(
                          new Location(p, 75, 1200)
                        , new Location(q, 20, 1000)
                        , new Location(u, 180, 1000)
                    )
                )
            )

            , // melhor ponto: 'q' parecido com o ponto 8, porem mandar o ponto 9 para referencia 'p'  demora menos
                new Location(11
                    , new ArrayList<>(Arrays.asList(
                          new Location(p, 75, 1000)
                        , new Location(q, 20, 1000)
                        , new Location(u, 180, 1000)
                    )
                )
            )

            , // melhor ponto: 'q' parecido com o ponto 9, porem mandar o ponto 9 para referencia 'u'  demora menos
                new Location(12
                    , new ArrayList<>(Arrays.asList(
                          new Location(p, 75, 1000)
                        , new Location(q, 20, 1000)
                        , new Location(u, 180, 800)
                    )
                )
            )
        }
    };

    private static void runTest() {
        ArrayList<Location> locs = new ArrayList<>(Arrays.asList(locations[0]));
        Group group = new GroupLocationByDistanceAndTime(locs);
        group.apply();
        LinkedHashMap<Location, LinkedHashSet<Location>> graph = ((GroupLocationByDistanceAndTime) group).getGraph();
        Debug.showResults(graph, locs);
    }

    public static void main(String[] args) {
        runTest();
    }

}
