package app.group;

import app.entity.Point2f;

import java.util.*;


/**
 * Aplicacao interessante para plotar pontos num plano
 * https://www.desmos.com/calculator/mhq4hsncnh
 * */

public class GroupByDistanceWithLimitSize implements Group {

    private static class EuclideanDistance implements Comparator<Point2f> {
        private Point2f local;

        public EuclideanDistance(Point2f local) { this.local = local; }

        @Override
        public int compare(Point2f p, Point2f q) {
            double dpl = local.distance(p);
            double dql = local.distance(q);
            debug(p, dpl);
            debug(q, dql);
            return Double.compare(dpl, dql);
        }

        private void debug(Point2f q, double distance) {
            System.out.printf("Distancia entre %s e %s eh %f\n"
                    , local
                    , q
                    , distance);
        }
    }

    private ArrayList<Point2f> points, references;
    private int limitSizeSet;

    private final LinkedHashMap<Point2f, LinkedHashSet<Point2f>> group;

    private static final double MAX = Double.MAX_VALUE;

    public GroupByDistanceWithLimitSize(List<Point2f> points) {
        this.points = new ArrayList<>(points);
        this.group = new LinkedHashMap<>();
        this.references = new ArrayList<>();
        int referencePoints = 0;
        for (Point2f point : points) {
            if (point.isReference()) {
                group.put(point, new LinkedHashSet<>());
                references.add(point);
                referencePoints++;
            }
        }
        /**
         * Quantidade maxima de pontos 'locais' que podem ser agrupados num ponto
         * de referencia
         * */
        int q = points.size();
        this.limitSizeSet = (q - referencePoints) / referencePoints
                + ((q - referencePoints) % referencePoints);
    }

    private boolean isReferencePointSaturated(Point2f p) {
        return group.get(p).size() == limitSizeSet;
    }

    private boolean almostEquals(double p, double q) {
        return Math.abs(p - q) < 10E-7;
    }

    private Point2f getNearestReferencePoint(ArrayList<Point2f> references, Point2f origin) {
        double minDistance = MAX;
        List<Point2f> refs = new ArrayList<>();
        for (Point2f ref : references) {
            double distance = ref.distance(origin);
            if (distance < minDistance) {
                minDistance = distance;
                deleteEntireCollection(refs.iterator());
                refs.add(ref);
            }
            else if (almostEquals(distance, minDistance)) {
                refs.add(ref);
            }
        }
        return refs.size() > 1 ? chooseOnePoint(refs) : refs.get(0);
    }

/*
    private Point2f getNearestReferencePoint(Point2f origin) {
        return getNearestReferencePoint(references, origin);
    }
*/
    private void deleteEntireCollection(Iterator<Point2f> it) {
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    private void removePointFromReference(Point2f ref, Point2f point2f) {
        group.get(ref).remove(point2f);
    }

    private void addPointToReference(Point2f ref, Point2f point2f) {
        group.get(ref).add(point2f.addAttempts(ref));
    }

    /**
     * Metodo auxiliar que tenta adicionar um ponto 'local'
     * a um ponto referencia
     * */
    private void attemptConnectPoints(Point2f currentReferencePoint, Point2f currentLocalPoint) {
        /**
         * Se o ponto de 'referencia' estiver saturado
         **/
        if (isReferencePointSaturated(currentReferencePoint)) {
            /**
             * Recuperar uma lista de pontos de referencia sem esse ponto saturado ordenados ela distância
             * distancia entre o ponto local e os pontos de referencia
             **/
            // adicionar o ponto de referencia a lista de pontos ja tentados
            currentLocalPoint.addAttempts(currentReferencePoint);
            /**
             * Verificar o que é viável:
             *
             * 1) Adicionar o ponto local 'A' a uma outra referencia mais proxima
             * ou remover 1 ponto local 'B' dessa referencia e tentar adiciona-lo em outra
             * referencia
             *
             * */
            // attemptOtherReferencePoints(localPoint);
            // criar uma copia dos pontos de referencia e c
            ArrayList<Point2f> copyReferencePoints = new ArrayList<>(references);
            // remover da copia os pontos de referencia que ja foram testados
            // para o atual 'ponto local'
            for (Point2f attempt : currentLocalPoint.getAttempts()) {
                copyReferencePoints.removeIf(attempt::equals);
            }
            // se nao sobraram mais pontos para testar, nos restas adicionar
            // o atual 'ponto local' no segundo ponto de referencia mais proximo
            if (copyReferencePoints.isEmpty()) {
                Point2f nearest = getNearestReferencePoint(copyReferencePoints, currentLocalPoint);
                addPointToReference(nearest, currentLocalPoint);
            }
            /**
             * Senao, vamos verificar outros pontos de referencia que pode ser viaveis
             * */
            else {
                /**
                 * Estrategia 1)
                 * Ordenar os pontos de referencia restantes pela distancia deles ao
                 * ponto local corrente
                 *
                 * TODO apagar a chamada do metodo abaixo
                 * copyReferencePoints.sort(new EuclideanDistance(currentLocalPoint));
                 * */

                /**
                 * Estrategia 2)
                 * recuperar proximo ponto de referencia mais proximo do corrente ponto local que queremos
                 * agrupar.
                 * */
                Point2f nextNearestReferencePoint = getNearestReferencePoint(copyReferencePoints, currentLocalPoint);
                /**
                 * Vamos recuperar os pontos locais ja conectados ao ponto de referencia
                 * corrente. Queremos verificar se eh melhor remover um ponto local 'B' que esta
                 * conectado ao ponto de referencia corrente e adiciona-lo
                 **/
                LinkedHashSet<Point2f> pointsAlreadyConnected = group.get(currentReferencePoint);
                // ponto ja conectado mais proximo do 'proximo ponto de referencia'
                Point2f nearestLocalPointAlreadyConnected = null;
                double minDistance = MAX;
                /**
                 * Iterar pelos pontos locais que ja foram adicionados ao ponto de referencia corrente.
                 * Verificar qual o ponto local mais proximo do novo ponto de referencia
                 **/
                for (Point2f pointAlreadyConnected : pointsAlreadyConnected) {
                    // distancia do ponto ja adicionado ao proximo ponto de referencia
                    double distance = pointAlreadyConnected.distance(nextNearestReferencePoint);
                    if (Double.compare(distance, minDistance) < 0) {
                        minDistance = distance;
                        nearestLocalPointAlreadyConnected = pointAlreadyConnected;
                    }
                }

                boolean removed = false;
                if (nearestLocalPointAlreadyConnected != null) {
                    // distancia entre o ponto 'local corrente' e 'proximo ponto de referencia'
                    double fromCurrentLocalPoint = currentLocalPoint.distance(nextNearestReferencePoint);
                    /**
                     * se a distancia entre o ponto escolhido e o 'proximo ponto de referencia'
                     * for menor do que a distancia entre o 'ponto local corrente' e o 'proximo ponto de referencia'
                     **/
                    if (Double.compare(minDistance, fromCurrentLocalPoint) < 0) {
                        // vamos remover a conexao entre o ponto referencia e o ponto escolhido
                        removePointFromReference(currentReferencePoint, nearestLocalPointAlreadyConnected);
                        // vamos adicionar o 'ponto local corrente' ao 'ponto de referencia corrente'
                        addPointToReference(currentReferencePoint, currentLocalPoint);
                        // vamos inserir o ponto de referencia na lista de pontos tentados pelo ponto local
                        currentLocalPoint.addAttempts(currentReferencePoint);
                        /**
                         * tentar conectar o 'ponto local' removido ao 'ponto de referencia' que foi definido
                         * como o i-esimo mais proximo
                         * */
                        attemptConnectPoints(nextNearestReferencePoint, nearestLocalPointAlreadyConnected);
                        removed = true;
                    }
                }
                /**
                 * Se nenhum ponto conectado ao 'ponto de referencia corrente' foi removido
                 * Vamos ter que tentar adicionar o 'ponto local corrente' ao (i+1)-esimo ponto
                 * de referencia mais proximo
                 * */
                if (!removed) {
                    // adiciona o ponto de referencia
                    currentLocalPoint.addAttempts(currentLocalPoint);
                    attemptConnectPoints(nextNearestReferencePoint, currentLocalPoint);
                }
            }
        }
        else {
            // senao, conecte o ponto de localidade ao ponto de referencia
            addPointToReference(currentReferencePoint, currentLocalPoint);
        }
    }


    /**
     * Implementacao de metodo para agrupar pontos pela sua proximidade
     * */
    private Group groupPoints() {
        for (Point2f point2f : points) {
            if ( point2f.isReference() )
                continue;
            // recuperar o ponto de referencia mais proximo desse ponto local
            Point2f currentReferencePoint = getNearestReferencePoint(references, point2f);
            attemptConnectPoints(currentReferencePoint, point2f);
        }
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
        return getPointWithMinY(points);
    }

    private Point2f getPointWithMinY(List<Point2f> points) {
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
