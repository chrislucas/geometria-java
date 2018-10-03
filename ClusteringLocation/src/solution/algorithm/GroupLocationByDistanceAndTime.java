package solution.algorithm;

import solution.entity.Location;

import java.util.*;

public class GroupLocationByDistanceAndTime implements Group {

    private LinkedHashMap<Location, LinkedHashSet<Location>> graph;
    private int limitSizeSet;

    private ArrayList<Location> allLocations;

    public GroupLocationByDistanceAndTime(List<Location> locations) {
        allLocations = new ArrayList<>(locations);
        graph = new LinkedHashMap<>();
        int qReference = 0;
        for (Location location : locations) {
            if (location.isReference()) {
                qReference++;
                graph.put(location, new LinkedHashSet<>());
            }
        }
        int size = locations.size();
        // Quantidade maxima que uma localizacao de referencia deve receber
        // removemos da conta os pontos que sao definidos como referencia
        this.limitSizeSet = ((size-qReference) / qReference) + ((size-qReference) % qReference);
    }

    public LinkedHashMap<Location, LinkedHashSet<Location>> getGraph() {
        return graph;
    }

    private final Location.CompareByDistanceAndTime compareLocations = new Location.CompareByDistanceAndTime();

    private Location getReference(Location origin, Location reference) {
        /**
         * Um loop atraves da lista de pontos de referencia ligados a origem.
         * Cada ponto de origem tem uma lista de pontos de referencia com a informacao
         * de distancia e tempo de trajeto
         **/
        for (Location ref : origin.getReferences()) {
            // cada origem tem informacao de distancia e tempo para todos os pontos
            // de referencia, mas queremos recuperar os dados de ponto de referencia especifico
            if (reference.getId() == ref.getId())
                return ref;
        }
        return null;
    }
/*
    Metodo necessario caso nao seja implementado equal e hashcode na classe Location
    private Location getReference(Location ref) {
        for (Location l : graph.keySet()) {
            if (l.getId() == ref.getId())
                return l;
        }
        return null;
    }
*/
    /**
     * Location currentBestReferenceLocation, atual ponto de referencia mais proximo e que leva menos
     * tempo de trajeto a partir da localizacao de origem
     *
     * Location currentOrigin: localizacao de origem de um determinado recurso
     * */
    private void join(Location currentBestReferenceLocation, Location currentOrigin) {
        /**
         * Se um ponto de referencia estiver saturado
         * */
        if (graph.get(currentBestReferenceLocation).size() == limitSizeSet) {
            // indicar que tal localizacao de referencia ja foi tentado
            currentBestReferenceLocation.defineVisited();

            // vamos escolher outro ponto de referencia
            PriorityQueue<Location> references = new PriorityQueue<>(currentOrigin.getReferences());
            // remover da lista os pontos de referencia ja visitados
            //references.removeIf(Location::isVisited);

            // o metodo abaixo eh mais eficiente pois a referencia visitada a primeira na fila de prioridade
            references.poll();
            /**
             * se visitamos todos os pontos de referencia vamos conectar o ponto
             * de origem a melhor referencia porem marcar o ponto de origem como
             * um ponto de sobrecarga indicando que tal ponto sobregarrega um localizacao
             * de referencia
             * */
            if (references.isEmpty()) {
                currentOrigin.defineOverloading();
                graph.get(currentBestReferenceLocation).add(currentOrigin);
            }
            /**
             * Senao, facamos a seguinte pergunta: O que é mais viável ? remover
             * uma das conexoes de 'localizacao de origem' da 'localizacao referencial'
             * atual (leia-se corrente durante a execucao do algoritmo) e adicionar a
             * 'localizacao de origem' atual ou tentar conectar
             * */
            else {
                /**
                 * Recuperar a proxima localizacao de referencia mais adequada para
                 * a localizacao de origem corrente
                 **/
                Location nextBestLocationReference = references.peek();

                /**
                 * recuperar a lista de 'localizacoes de origem' ja adicionadas a
                 * 'localizacao de referencia' atual
                 **/
                Location [] originsAdded = (Location[]) graph.get(currentBestReferenceLocation).toArray();

                // Marcar a 'localizacao de origem' na posicao 0 como a melhor escolha para uma possivel remocao
                Location bestOriginLocationChoosed = originsAdded[0];
                // dados do trajeto entre a origem escolhida e a proxima melhor localizacao de referencia
                Location bestReferenceLocationChoosed = getReference(bestOriginLocationChoosed, nextBestLocationReference);

                // Loop sobre todas as posicoes de origem ja adicionadas para descobrir qual
                // esta mais proxima da proxima localizacao de referencia (comparando distancia e tempo de deslocamento)
                for (int i = 1; i < originsAdded.length ; i++) {
                    // dados do trajeto entre a i-esima origem e a proxima melhor localizacao de referencia
                    Location ref = getReference(originsAdded[i], nextBestLocationReference);
                    // teste para ver qual localizacao de origem esta mais proxima do proximo ponto de referencia
                    if (compareLocations.compare(ref, bestReferenceLocationChoosed) < 0) {
                        bestOriginLocationChoosed = originsAdded[i];
                        bestReferenceLocationChoosed = new Location(ref, ref.getDistance(), ref.getTime());
                    }
                }

                //boolean removed = false;
                /**
                 * Se dentre as 'localizacoes de origem' ja adicionadas tivermos uma cuja distancia/tempo
                 * para a proxima 'localizacao de referencia' for menor do que distancia entre a 'origem' atual
                 * e essa mesam localizacao de referencia
                 * */
                if (compareLocations.compare(bestReferenceLocationChoosed, nextBestLocationReference) < 0) {
                    // removemos a conexao entre o ponto de origem anteriormente escolhido
                    graph.get(currentBestReferenceLocation).remove(bestOriginLocationChoosed);
                    // adicionamos a nova conexao
                    graph.get(currentBestReferenceLocation).add(currentOrigin);
                    // vamos tentar adicionar a localizacao de origem removida a localizacao de referencia
                    // que foi definida como a mais adequada (menor distancia/tempo)
                    join(nextBestLocationReference, bestOriginLocationChoosed);
                    //removed = true;
                }
                else {
                    /**
                     * Se a localizacao de origem escolhida para remocao for mais distante do novo ponto
                     * de referencia, vamos tentar adicionar o ponto origem atual ao proximo ponto de referencia
                     * considerado o mais adequado
                     * */
                    join(nextBestLocationReference, currentOrigin);
                }
            }
        }
        else {
            currentBestReferenceLocation.defineVisited();
            graph.get(currentBestReferenceLocation).add(currentOrigin);
        }
    }

    private void join() {
        for (Location location : allLocations) {
            if (!location.isReference()) {
                join(location.getReferences().peek(), location);
            }
        }
    }

    @Override
    public Group apply() {
        join();
        return this;
    }

    @Override
    public Location chooseOneOption(List<Location> locations) {
        return null;
    }
}
